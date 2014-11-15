package tests;

import junit.framework.Assert;

import org.junit.Test;
import org.leap.CleanTask;
import org.leap.SalesforceConnection;

import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.SaveResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

public class CleanTaskTests {

	@Test
	public void propertyInitTests(){
		CleanTask task = new CleanTask();
		Assert.assertEquals("", task.getStartsWith());
		Assert.assertEquals("", task.getType() );
		Assert.assertFalse( task.getIsTest() );
	}
	
	@Test
	public void salesforceConnectionTest(){
		CleanTask task = new CleanTask();
		
		task.setUsername(System.getenv("SALESFORCE_USERNAME"));
		task.setPassword(System.getenv("SALESFORCE_PASSWORD"));
		task.setToken(System.getenv("SALESFORCE_TOKEN"));
		task.setServerurl(System.getenv("SALESFORCE_SERVER_URL"));
		//task.setProjectRoot( System.getenv("PROJECT_ROOT"));
		//task.setLimit("10");
		
		Assert.assertTrue(task.salesforceConnection().isValid());
		
		SObject apex = this.createMockApexClass(task.salesforceConnection());
		Assert.assertNotNull(apex);
		Assert.assertNotNull(apex.getId());
		
		//task.salesforceConnection().getMetadataConnection().delete(metadata)		
		
		//Assert.assertNotNull(task.getLeapTemplate());
		//Assert.assertNotNull(task.getLeapTemplate().content);
		//Assert.assertTrue(task.getLeapTemplate().hasLeaplets());
		
		//task.execute();
		
		//File file = new File(task.getProjectRoot() + "classes/SObjectFields.cls");
		//Assert.assertTrue(file.exists());
		
		//file = new File(task.getProjectRoot() + "classes/SObjectFields.cls-meta.xml");
		//Assert.assertTrue(file.exists());
	}
	
	private SObject createMockApexClass(SalesforceConnection conn){
		SObject apex = getMockApexClass(conn);
		if(apex != null){
			return apex;
		}
		apex = new SObject();
		apex.setType("ApexClass");
		apex.setField("Name", "LEAP_MockObject");
		apex.setField("Status", "Active");
		apex.setField("Body", "@IsTest\npublic class LEAP_MockObject{}");
		try {
			SaveResult[] results = conn.getPartnerConnection().create(new SObject[] {apex} );
			for(SaveResult result : results){
				System.out.println("Apex Mock class create() result.isSuccess(): " + result.isSuccess());
				for(com.sforce.soap.partner.Error err : result.getErrors() ){
					System.out.println("\tError: " + err.getMessage() );
				}
			}
			
			apex = this.getMockApexClass(conn);
			System.out.println("Created ApexClass Id: " + apex.getId() );
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
		return apex;
	}
	
	private SObject getMockApexClass(SalesforceConnection conn){
		SObject apexClass = null;
		String soql = "SELECT Id, Name, Status, Body FROM ApexClass WHERE Name='LEAP_MockObject'";		
		QueryResult results;
		try {
			results = conn.getPartnerConnection().query(soql);
			if(results.getSize() > 0){
				apexClass = results.getRecords()[0];
			}
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
		return apexClass;
	}
}