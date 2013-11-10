package org.leap;
import org.apache.tools.ant.BuildException;

import com.sforce.soap.partner.GetServerTimestampResult;
import com.sforce.ws.ConnectionException;

public class HelloWorld extends LeapTask {
	
	String message;
    public void setMessage(String msg) {
        message = msg;
    }
    
	public void execute() {
		if (message==null) {
			throw new BuildException("No message set.");
        }
		log(message);
        
        log("Here is project '" + getProject().getProperty("ant.project.name") + "'.");
        log("This task is used in: " +  getLocation() );
        log("Root source folder: " + rootSourceFolder());
        
        log("username:" + this.username);
        log("password:" + this.password);
        log("token:" + this.token);
        log("serverurl:" + this.serverurl);
        
        try {
			SalesforceConnection conn = this.salesforceConnection();
			GetServerTimestampResult result = conn.getPartnerConnection().getServerTimestamp();
			log("Salesforce server timestamp:" + result.getTimestamp().toString());
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			log(e.toString());
			e.printStackTrace();
		}
	}
}