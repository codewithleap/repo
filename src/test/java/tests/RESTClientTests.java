package tests;

import org.junit.Test;
import org.leap.SalesforceConnection;

import com.sforce.ws.ConnectionException;


public class RESTClientTests {

	@Test
	public void connectionTests(){
		
	}
	
	private SalesforceConnection m_salesforceConnection = null;
    public SalesforceConnection salesforceConnection(){
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