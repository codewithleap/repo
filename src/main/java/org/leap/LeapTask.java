package org.leap;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.soap.partner.DescribeGlobalResult;
import com.sforce.soap.partner.DescribeGlobalSObjectResult;
import com.sforce.ws.ConnectionException;

public class LeapTask extends Task {
	protected final int MAX_FILE_NAME_SIZE = 255;
	protected String metaClassURL 	= "https://api.github.com/repos/codewithleap/repo/contents/templates/src/classes/ApexClassTemplate.cls-meta.xml";
	protected String metaTriggerURL = "https://api.github.com/repos/codewithleap/repo/contents/templates/src/triggers/TriggerTemplate.trigger-meta.xml";
	
	String projectRootFolder = null;
	public void setProjectRoot(String root){
		this.projectRootFolder = root;
		if(!this.projectRootFolder.endsWith("/")){
			this.projectRootFolder += "/";
		}
	}
	
	public String getProjectRoot(){
		if(projectRootFolder == null){
			projectRootFolder = rootSourceFolder();
		}
		return this.projectRootFolder;
	}
	
	protected String rootSourceFolder(){
		String[] path = this.getLocation().getFileName().split("/");
		String result = "";
		for(String p : path){
			if(p.contains(".")){
				continue;
			}
			result += p + "/";
		}
		result += "src/";
		return result;
	}
	
	String username;
    public void setUsername(String uname) {
    	username = uname;
    }
    
    String password;
    public void setPassword(String pw) {
    	password = pw;
    }
    
    String token;
    public void setToken(String t) {
    	token = t;
    }
    
    String serverurl;
    public void setServerurl(String url) {
    	serverurl = url;
    }
    
	String srcUsername;
    public void setSrcUsername(String uname) {
    	srcUsername = uname;
    }
    
    String srcPassword;
    public void setSrcPassword(String pw) {
    	srcPassword = pw;
    }
    
    String srcToken;
    public void setSrcToken(String t) {
    	srcToken = t;
    }
    
    String srcServerurl;
    public void setSrcServerurl(String url) {
    	srcServerurl = url;
    }
    
    String api = SalesforceConnection.API_VERSION;
    public void setApi(String apiVersion){
    	api = apiVersion;
    }
    
    String namespace = "";
    public void setNamespace(String ns){
    	namespace = ns;
    }
    
    String objects = "all";
    public void setObjects(String objConfig) {
    	objects = objConfig;
    }
    
    private List<String> m_objectList = null;
    public List<String> objectList(){
    	if(m_objectList == null){
    		m_objectList = new ArrayList<String>();
    		if(this.objects == null || objects.equals("all")){
    			m_objectList.add("all");
    		} else {
	    		for(String obj : objects.split(",")){
	    			obj = obj.replaceAll(" ", "");
	    			m_objectList.add(obj);
	    		}
    		}
    	}
    	return m_objectList;
    }
    
    String className;
    public void setClass(String cName) {
    	className = cName;
    }
    
    // By default the generated files are deployed.
    // set it to false if don't want to deploy
    protected Boolean deployOption = true;
    public void setDeploy(Boolean deploy) {
    	deployOption = deploy;
    }
    
    private SalesforceConnection m_salesforceConnection = null;
    public SalesforceConnection salesforceConnection(){
    	if(m_salesforceConnection == null){
    		try {
				m_salesforceConnection = new SalesforceConnection(username, password, token, serverurl);
			} catch (ConnectionException e) {
				e.printStackTrace();
			}
    	}
    	return m_salesforceConnection;
    }
    
    public void validateConnectionParams(){
    	if(this.username == null){
    		throw new BuildException("Missing Salesforce username param");
    	}
    	if(this.password == null){
    		throw new BuildException("Missing Salesforce password param");
    	}
    	if(this.token == null){
    		throw new BuildException("Missing Salesforce token param");
    	}
    	if(this.serverurl == null){
    		throw new BuildException("Missing Salesforce serverurl param");
    	}
    }
    
    private DescribeGlobalSObjectResult[] m_sobjectResults;
	public DescribeGlobalSObjectResult[] sObjects(){
		if(m_sobjectResults == null){
			DescribeGlobalResult describeGlobalResult = null;
			try {
				describeGlobalResult = salesforceConnection().getPartnerConnection().describeGlobal();
			} catch (ConnectionException e) {
				e.printStackTrace();
				//errors.add(e.getMessage());
			}
			m_sobjectResults = describeGlobalResult.getSobjects();
		}
		return m_sobjectResults;
	}
	
	// deploy the generated files with Metadata connection
	protected void deployGeneratedFiles(List<String> metadataFiles) {
		if(metadataFiles == null || metadataFiles.size() == 0){
			return;
		}
		MetadataConnection metadataConnection = salesforceConnection().getMetadataConnection();
		
		try {
			MetadataDeployer metadataDeployer = new MetadataDeployer(api, metadataConnection);
			metadataDeployer.deployMetadataFiles(metadataFiles);
		} catch(Exception e) {
			System.out.println("Deploying files failed with "+ e.getMessage() +", deploy is aborted");
			//e.printStackTrace();
		}    
	}
	
	protected String truncateFileName(String fName){
		return fName.substring(0, this.MAX_FILE_NAME_SIZE);
	}
	
	protected String getFormattedObjectName(DescribeGlobalSObjectResult sobject){
		String objectName = "";
		String[] objectTokens = sobject.getName().split("__");		
		if(objectTokens.length == 1){ 		// Example "Account"
			objectName = sobject.getName();
		}
		else if(objectTokens.length == 2){	// Example "Order__c"
			objectName = objectTokens[0];
		}
		else if(objectTokens.length == 3){	// Example "ISV__Order__c"
			objectName = objectTokens[2];
		}
		return objectName.toUpperCase();
	}
}
