package org.leap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sforce.soap.apex.RunTestFailure;
import com.sforce.soap.apex.RunTestsRequest;
import com.sforce.soap.apex.RunTestsResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

public class RunTestsTask extends LeapTask {

	private String m_classes = "";

	public void setClasses(String classes) {
		m_classes = classes.toLowerCase();
	}

	private List<String> getClassNames() {
		String[] files = m_classes.split(",");
		return Arrays.asList(files);
	}

	private String m_regex = "a^";

	public void setRegex(String regex) {
		m_regex = regex;
	}

	@Override
	public void execute() {
		try {
			this.doApexClassMatch();
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
	}

	private void doApexClassMatch() throws ConnectionException {
		List<String> declaredClassNames = this.getClassNames();
		List<String> classList = new ArrayList<String>();
		System.out.println("Declared classes to test: ");
		for (String f : declaredClassNames) {
			System.out.println("\t" + f);
		}

		List<String> classNames = this.getAllClassNames();

		for (String className : classNames) {
			if (declaredClassNames.contains(className)
					|| className.matches(this.m_regex)) {
				System.out.println("Matched " + className + ". Added.");
				classList.add(className);
			}
		}

		RunTestsRequest runTestsRequest = new RunTestsRequest();
		runTestsRequest.setClasses(classList.toArray(new String[classList
				.size()]));
		runTestsRequest.setAllTests(false);

		System.out.println("Sending runTests request...");
		try {
			RunTestsResult result = this.salesforceConnection()
					.getApexConnection().runTests(runTestsRequest);

			System.out.println("Total Tests Run: " + result.getNumTestsRun());
			System.out
					.println("Total Tests Failed: " + result.getNumFailures());
			System.out.println("Total Time: " + result.getTotalTime());
			for (RunTestFailure t : result.getFailures()) {
				System.out.println(t.getClass() + "." + t.getMethodName());
				System.out.println(t.getMessage());
				System.out.println(t.getStackTrace());
				System.out.println("*****************");
			}

			if (result.getNumFailures() > 0 && this.failOnError()) {
				System.exit(1);
			}
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
	}

	private List<String> getAllClassNames() throws ConnectionException {
		List<String> classNames = new ArrayList<String>();
		List<SObject> apexRecords = this
				.query("SELECT Id, Name FROM ApexClass");

		for (SObject obj : apexRecords) {
			classNames.add(obj.getField("Name").toString().toLowerCase());
		}
		return classNames;
	}
}