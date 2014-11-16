package tests;

import junit.framework.Assert;

import org.junit.Test;
import org.leap.DeleteObjectsTask;
import org.leap.SalesforceConnection;

import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.SaveResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

public class DeleteObjectsTests {
	private String TEST_SOQL = "SELECT Id FROM ApexClass WHERE Name='LEAP_MockObject'"; 
	
	@Test
	public void taskInitializationTests(){
		DeleteObjectsTask task = new DeleteObjectsTask();
		Assert.assertEquals("", task.getQuery() );
		task.setQuery(TEST_SOQL);
		Assert.assertEquals(TEST_SOQL, task.getQuery() );
	}
	
	@Test
	public void taskExecutionTests(){
		DeleteObjectsTask task = new DeleteObjectsTask();
		task.setUsername(System.getenv("SALESFORCE_USERNAME"));
		task.setPassword(System.getenv("SALESFORCE_PASSWORD"));
		task.setToken(System.getenv("SALESFORCE_TOKEN"));
		task.setServerurl(System.getenv("SALESFORCE_SERVER_URL"));
		Assert.assertTrue(task.salesforceConnection().isValid());
		
		SObject apex = this.createMockApexClass(task.salesforceConnection());
		Assert.assertNotNull(apex);
		Assert.assertNotNull(apex.getId());
		
		task.setQuery(TEST_SOQL);
		Assert.assertEquals(TEST_SOQL, task.getQuery() );
		task.execute();
		
		apex = this.getMockApexClass(task.salesforceConnection());
		Assert.assertNull(apex);
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