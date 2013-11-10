package tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.leap.MetadataDeployer;

import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.soap.metadata.Package;
import com.sforce.ws.ConnectionException;

public class MetadataDeployerTest {
	
	@Test
	public void createPackageTest() throws IOException, ConnectionException {

		MetadataConnection metadataConnection = null;
		
		MetadataDeployer metadataDeployer = new MetadataDeployer("29.0", metadataConnection);
		
		Map<String, List<String>> membersListMap = new HashMap<String, List<String>>();
		List<String> members = new ArrayList<String>();
		members.add("SObjectFields");
		membersListMap.put("ApexClass", members);
		
		Package pkg = metadataDeployer.createPackage(membersListMap);
		byte[] pkgBytes = metadataDeployer.writePackageInBytes(pkg);
		String pkgStr = new String(pkgBytes, "UTF-8");
		System.out.println(pkgStr);
		
		Assert.assertTrue(pkgStr.contains("<n1:version>29.0</n1:version>"));
		Assert.assertTrue(pkgStr.contains("<n1:types><n1:members>SObjectFields</n1:members><n1:name>ApexClass</n1:name></n1:types>"));
		
	}
	
	
}
