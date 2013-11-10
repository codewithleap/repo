package org.leap;

import java.net.URL;

import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.soap.partner.GetServerTimestampResult;
import com.sforce.soap.partner.LoginResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class SalesforceConnection {
	public static final String API_VERSION	= "29.0";
	private final String SFDC_USERNAME; 
    private final String SFDC_PASSWORD; 
    private final String SFDC_TOKEN;
    private final String SFDC_URL;
    
    private final String SFDC_TEST_URL = "https://test.salesforce.com/services/Soap/u/" + API_VERSION;
    private final String SFDC_PROD_URL = "https://login.salesforce.com/services/Soap/u/" + API_VERSION;
    
	private final LoginResult loginResult;
	private final PartnerConnection partnerConnection;
	private final MetadataConnection metadataConnection;
	
	public SalesforceConnection(String username, String password, String token, String serverUrl) throws ConnectionException{
		this.SFDC_USERNAME 	= username;
		this.SFDC_PASSWORD 	= password;
		this.SFDC_TOKEN		= token;
		this.SFDC_URL		= serverUrl + "/services/Soap/u/" + API_VERSION;
				
		this.loginResult = this.loginToSalesforce();
		this.partnerConnection = createPartnerConnection(this.loginResult);
		this.metadataConnection = createMetadataConnection(this.loginResult);
	}
	
	public SalesforceConnection(String username, String password, String token, OrgType type) throws ConnectionException{
		this.SFDC_USERNAME 	= username;
		this.SFDC_PASSWORD 	= password;
		this.SFDC_TOKEN		= token;
		this.SFDC_URL		= type == OrgType.PRODUCTION ? this.SFDC_PROD_URL : this.SFDC_TEST_URL;
		
		this.loginResult = this.loginToSalesforce();
		this.partnerConnection = createPartnerConnection(this.loginResult);
		this.metadataConnection = createMetadataConnection(this.loginResult);
	}
	
	public SalesforceConnection(URL url, String sessionId) throws ConnectionException{
		this.SFDC_USERNAME 	= null;
		this.SFDC_PASSWORD 	= null;
		this.SFDC_TOKEN		= null;
		this.SFDC_URL		= null;
		loginResult 		= new LoginResult();
		loginResult.setServerUrl(url.toExternalForm());
		loginResult.setSessionId(sessionId);
		
		final ConnectorConfig config = new ConnectorConfig();
        config.setServiceEndpoint(url.toExternalForm());
        config.setSessionId(sessionId);
        this.partnerConnection = new PartnerConnection(config);
        this.metadataConnection = null;	//TODO fix this
	}
	
	private LoginResult loginToSalesforce() throws ConnectionException {
        final ConnectorConfig config = new ConnectorConfig();
        config.setAuthEndpoint(SFDC_URL);
        config.setServiceEndpoint(SFDC_URL);
        config.setManualLogin(true);
        return (new PartnerConnection(config)).login(SFDC_USERNAME, SFDC_PASSWORD + SFDC_TOKEN);
    }
		
	private PartnerConnection createPartnerConnection(final LoginResult loginResult) throws ConnectionException {
        final ConnectorConfig config = new ConnectorConfig();
        config.setServiceEndpoint(loginResult.getServerUrl());
        config.setSessionId(loginResult.getSessionId());
        return new PartnerConnection(config);
    }
	
	// create metadata connection for deploy
	private MetadataConnection createMetadataConnection(final LoginResult loginResult) throws ConnectionException {
		ConnectorConfig metadataConfig = new ConnectorConfig();
		metadataConfig.setSessionId(loginResult.getSessionId());
		metadataConfig.setServiceEndpoint(loginResult.getMetadataServerUrl());
		return com.sforce.soap.metadata.Connector.newConnection(metadataConfig);
	}
	
	public LoginResult getLoginResult(){
		return this.loginResult;
	}
	
	public PartnerConnection getPartnerConnection(){
		return this.partnerConnection;
	}
	
	public MetadataConnection getMetadataConnection(){
		return this.metadataConnection;
	}
	
	public boolean isValid(){
		try {
			GetServerTimestampResult result = this.getPartnerConnection().getServerTimestamp();
			if(result != null){
				return true;
			} else {
				return false;
			}
		} catch (ConnectionException e) {
			e.printStackTrace();
			return false;
		}
	}
}