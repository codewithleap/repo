package tests;

import junit.framework.Assert;

import org.junit.Test;
import org.leap.SFieldsTask;
import org.leap.TriggerTask;

public class TriggerTaskTests {

	@Test
	public void basicTests(){
		TriggerTask task = new TriggerTask();
		
		task.setUsername(System.getenv("SALESFORCE_USERNAME"));
		task.setPassword(System.getenv("SALESFORCE_PASSWORD"));
		task.setToken(System.getenv("SALESFORCE_TOKEN"));
		task.setServerurl(System.getenv("SALESFORCE_SERVER_URL"));
		task.setProjectRoot( System.getenv("PROJECT_ROOT"));
		
		Assert.assertTrue(task.salesforceConnection().isValid());
		Assert.assertNotNull(task.getLeapClassTemplate());
		Assert.assertNotNull(task.getLeapClassTemplate().content);
		Assert.assertNotNull(task.getLeapMetaClassTemplate());
		Assert.assertNotNull(task.getLeapMetaClassTemplate().content);
		
		task.execute();
	}
}