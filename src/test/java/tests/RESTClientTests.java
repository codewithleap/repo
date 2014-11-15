package tests;

import junit.framework.Assert;

import org.junit.Test;
import org.leap.RestClient;
import org.leap.SalesforceConnection;
import com.sforce.ws.ConnectionException;

public class RESTClientTests {

	@Test
	public void connectionTests(){
		SalesforceConnection conn = this.getSalesforceConnection();
		Assert.assertEquals(true, conn.isValid());
		
		RestClient client = new RestClient(conn);
		Assert.assertNotNull(client.getBaseURL());		
		System.out.println("Base URL: " + client.getBaseURL() );
		
		
		//String response = client.restGet("/sobjects/Account/");
		//Assert.assertNotNull(response);
		//System.out.println(response);
		
		//response = client.restGet("sobjects/Account/");
		//Assert.assertNotNull(response);
		
		//response = client.restGet("/tooling/");
		//Assert.assertNotNull(response);
		//System.out.println(response);
		
		String response = client.restGet("/tooling/sobjects/ApexClass/01pB00000004LimIAE");
		//String response = client.restGet("/tooling/sobjects/ApexCodeCoverageAggregate/01pB00000004LimIAE");		
		Assert.assertNotNull(response);
		System.out.println(response);
	}
	
	private SalesforceConnection m_salesforceConnection = null;
    public SalesforceConnection getSalesforceConnection(){
    	if(m_salesforceConnection == null){
    		try {
				m_salesforceConnection = new SalesforceConnection(System.getenv("SALESFORCE_USERNAME"), 
						System.getenv("SALESFORCE_PASSWORD"), 
						System.getenv("SALESFORCE_TOKEN"), 
						System.getenv("SALESFORCE_SERVER_URL"));
			} catch (ConnectionException e) {
				e.printStackTrace();
			}
    	}
    	return m_salesforceConnection;
    }
}