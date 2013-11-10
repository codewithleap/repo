package tests;

import java.io.File;
import java.net.URL;

import junit.framework.Assert;

import org.junit.Test;
import org.leap.SFieldsTask;

public class SFieldsTaskTests {
	
	@Test
	public void fetchGitHubTemplateTest(){
		SFieldsTask task = new SFieldsTask();
		
		task.setUsername(System.getenv("SALESFORCE_USERNAME"));
		task.setPassword(System.getenv("SALESFORCE_PASSWORD"));
		task.setToken(System.getenv("SALESFORCE_TOKEN"));
		task.setServerurl(System.getenv("SALESFORCE_SERVER_URL"));
		task.setProjectRoot( System.getenv("PROJECT_ROOT"));
		task.setLimit("10");
		
		Assert.assertTrue(task.salesforceConnection().isValid());
		Assert.assertNotNull(task.getLeapTemplate());
		Assert.assertNotNull(task.getLeapTemplate().content);
		Assert.assertTrue(task.getLeapTemplate().hasLeaplets());
		
		task.execute();
		
		File file = new File(task.getProjectRoot() + "classes/SObjectFields.cls");
		Assert.assertTrue(file.exists());
		
		file = new File(task.getProjectRoot() + "classes/SObjectFields.cls-meta.xml");
		Assert.assertTrue(file.exists());
	}
}