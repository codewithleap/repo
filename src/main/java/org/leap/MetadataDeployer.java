package org.leap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.sforce.soap.metadata.AsyncRequestState;
import com.sforce.soap.metadata.AsyncResult;
import com.sforce.soap.metadata.CodeCoverageWarning;
import com.sforce.soap.metadata.DeployDetails;
import com.sforce.soap.metadata.DeployMessage;
import com.sforce.soap.metadata.DeployOptions;
import com.sforce.soap.metadata.DeployResult;
import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.soap.metadata.Package;
import com.sforce.soap.metadata.PackageTypeMembers;
import com.sforce.soap.metadata.RunTestFailure;
import com.sforce.soap.metadata.RunTestsResult;
import com.sforce.ws.bind.TypeMapper;
import com.sforce.ws.parser.XmlOutputStream;

public class MetadataDeployer {

	private static final String MANIFEST_FILE = "src/package.xml";

	// one second in milliseconds
	private static final long ONE_SECOND = 1000;

	// maximum number of attempts to deploy the zip file
	private static final int MAX_NUM_POLL_REQUESTS = 50;

	private static String CLASSES = "classes";
	private static String TRIGGERS = "triggers";
	private static String OBJECTS = "objects";
	private static String APEX_CLASS = "ApexClass";
	private static String APEX_TRIGGER = "ApexTrigger";
	private static String CUSTOM_OBJECT = "CustomObject";

	String apiVersion;
	MetadataConnection metadataConnection;

	static Map<String, String> typeNameMap = new HashMap<String, String>();
	{
		typeNameMap.put(CLASSES, APEX_CLASS);
		typeNameMap.put(TRIGGERS, APEX_TRIGGER);
		typeNameMap.put(OBJECTS, CUSTOM_OBJECT);
	}

	public MetadataDeployer(String apiVersion, MetadataConnection metadataConnection) {
		this.apiVersion = apiVersion;
		this.metadataConnection = metadataConnection;

	}

	public void deployMetadataFiles(List<String> metadataFiles) throws Exception {

		// create ZipFile with ZipOutputStream
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(baos);

		String regex = "[/]";
		if (File.separator.equals("\\"))
			regex = "[\\\\]";		// in Windows

		Map<String, List<String>> membersListMap = new HashMap<String, List<String>>();
		
		// for each file, add to package.xml, read file and add it to zip file
		for (String filePath : metadataFiles) {

			int index = filePath.indexOf(File.separator+"src"+File.separator);
			String objectPath = filePath.substring(index+1);
			String[] parts = objectPath.split(regex);
			String type = parts[1];
			String filename = parts[2];
			
			String typeName = typeNameMap.get(type);
			if (typeName != null) {
				// meta.xml files are not included in package
				if (!filename.endsWith("-meta.xml")) {
					List<String> members = getMembersList(typeName, membersListMap);
					// remove extension
					String objectName = filename.split("[.]")[0];
					members.add(objectName);
				}
				File file = new File(filePath);
				byte[] bytes = LeapUtils.readFileInBytes(file);
				addFileToZipFile(objectPath, bytes, zos);
			} else {
				System.out.println("Not supported type: "+type);
			}
		}

		// create Package, and get byte[] stream
		Package pkg = createPackage(membersListMap);
		byte[] pkgBytes = writePackageInBytes(pkg);
		// add Package to ZipFile
		addFileToZipFile(MANIFEST_FILE, pkgBytes, zos);

		zos.close();
		baos.close();
		// get zip file in byte[]
		byte[] zipBytes = baos.toByteArray();

		DeployOptions deployOptions = new DeployOptions();
		deployOptions.setRollbackOnError(true);
		AsyncResult asyncResult = metadataConnection.deploy(zipBytes, deployOptions);

		// check status, display success or error message
		asyncResult = waitForCompletion(asyncResult);
		DeployResult result = metadataConnection.checkDeployStatus(asyncResult.getId(), true);
		
		if (!result.isSuccess()) {
			printErrors(result);
			throw new Exception("Failed to deploy the generated files");
		}
	}

	private List<String> getMembersList(String typeName, Map<String, List<String>> membersListMap) {
		List<String> members = membersListMap.get(typeName);
		if (members == null) {
			members = new ArrayList<String>();
			membersListMap.put(typeName, members);
		}
		return members;
	}

	// read the generated file and return byte[]
//	private byte[] readFileInBytes(String filePath) throws IOException {
//		byte[] result = null;
//		File file = new File(filePath);
//		if (!file.exists() || !file.isFile()) {
//			throw new IOException("Cannot find the zip file for deploy() on path:"+ filePath);
//		}
//
//		FileInputStream inputStream = new FileInputStream(file);
//		try {
//			ByteArrayOutputStream bos = new ByteArrayOutputStream();
//			byte[] buffer = new byte[4096];
//			int bytesRead = 0;
//			while (-1 != (bytesRead = inputStream.read(buffer))) {
//				bos.write(buffer, 0, bytesRead);
//			}
//			result = bos.toByteArray();
//		} finally {
//			inputStream.close();
//		}
//		return result;
//	}

	// add file to the zip file
	private ZipEntry addFileToZipFile(String filename, byte[] input, ZipOutputStream zos) throws IOException {

		ZipEntry entry = new ZipEntry(filename);
		entry.setSize(input.length);
		zos.putNextEntry(entry);
		zos.write(input);
		zos.closeEntry();

		return entry;
	}

	// write the package object to output stream and return byte[]
	public byte[] writePackageInBytes(Package pkg) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		XmlOutputStream xout = new XmlOutputStream(baos, false);

		TypeMapper typeMapper = new TypeMapper();
		javax.xml.namespace.QName package_qname = new javax.xml.namespace.QName("http://soap.sforce.com/2006/04/metadata", "Package");

		pkg.write(package_qname, xout, typeMapper);
		xout.close();
		baos.close();

		byte[] bytes = baos.toByteArray();
		return bytes;
	}

	// create package with type members
	public Package createPackage(Map<String, List<String>> membersListMap) {
		Package pkg = new Package();
		pkg.setVersion(apiVersion);

		PackageTypeMembers[] typeMembers = new PackageTypeMembers[membersListMap.size()];
		Set<String> keys = membersListMap.keySet();
		
		int i = 0;
		for (String key : keys) {
			typeMembers[i] = createPackageTypeMembers(key, membersListMap.get(key));
			i ++;
		}

		pkg.setTypes(typeMembers);
		return pkg;
	}

	private PackageTypeMembers createPackageTypeMembers(String type, List<String> memberList) {
		PackageTypeMembers pkgTypeMembers = new PackageTypeMembers();
		pkgTypeMembers.setName(type);
		String[] members = new String[memberList.size()];
		for (int i=0; i<memberList.size(); i++) {
			members[i] = memberList.get(i);
		}
		pkgTypeMembers.setMembers(members);
		return pkgTypeMembers;
	}

	private AsyncResult waitForCompletion(AsyncResult asyncResult) throws Exception {
		int poll = 0;
		long waitTimeMilliSecs = ONE_SECOND;
		while (!asyncResult.isDone()) {
			Thread.sleep(waitTimeMilliSecs);
			// double the wait time for the next iteration

			waitTimeMilliSecs *= 2;
			if (poll++ > MAX_NUM_POLL_REQUESTS) {
				throw new Exception(
						"Request timed out. If this is a large set of metadata components, " +
						"ensure that MAX_NUM_POLL_REQUESTS is sufficient.");
			}

			asyncResult = metadataConnection.checkStatus(
					new String[]{asyncResult.getId()})[0];
			System.out.println("Deploy status is: " + asyncResult.getState());
		}

		if (asyncResult.getState() != AsyncRequestState.Completed) {
			throw new Exception(asyncResult.getStatusCode() + " msg: " +
					asyncResult.getMessage());
		}
		return asyncResult;
	}

	private void printErrors(DeployResult deployResult) {
		DeployDetails details = deployResult.getDetails();
		
		StringBuilder stringBuilder = new StringBuilder("Failures:\n");
		for (DeployMessage message : details.getComponentFailures()) {
			if (!message.isSuccess()) {
				String loc = "(" + message.getLineNumber() + ", " + message.getColumnNumber();
				if (loc.length() == 0 && !message.getFileName().equals(message.getFullName()))
				{
					loc = "(" + message.getFullName() + ")";
				}
				stringBuilder.append(message.getFileName() + loc + ":" 
						+ message.getProblem()).append('\n');
			}
		}
		RunTestsResult rtr = details.getRunTestResult();
		if (rtr.getFailures() != null) {
			for (RunTestFailure failure : rtr.getFailures()) {
				String n = (failure.getNamespace() == null ? "" :
					(failure.getNamespace() + ".")) + failure.getName();
				stringBuilder.append("Test failure, method: " + n + "." +
						failure.getMethodName() + " -- " + failure.getMessage() + 
						" stack " + failure.getStackTrace() + "\n\n");
			}
		}
		if (rtr.getCodeCoverageWarnings() != null) {
			for (CodeCoverageWarning ccw : rtr.getCodeCoverageWarnings()) {
				stringBuilder.append("Code coverage issue");
				if (ccw.getName() != null) {
					String n = (ccw.getNamespace() == null ? "" :
						(ccw.getNamespace() + ".")) + ccw.getName();
					stringBuilder.append(", class: " + n);
				}
				stringBuilder.append(" -- " + ccw.getMessage() + "\n");
			}
		}
		System.out.println(stringBuilder.toString());
	}
}