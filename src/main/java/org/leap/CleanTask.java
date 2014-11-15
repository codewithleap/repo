package org.leap;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.sforce.soap.metadata.DescribeMetadataObject;
import com.sforce.soap.metadata.DescribeMetadataResult;
import com.sforce.soap.metadata.FileProperties;
import com.sforce.soap.metadata.ListMetadataQuery;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

public class CleanTask extends LeapTask {
	
	String metadataType = "";
	public void setType(String t) { metadataType = t; }
	public String getType(){ return metadataType; }
	
	String strStartsWith = "";
    public void setStartsWith(String str) { strStartsWith = str; }
    public String getStartsWith(){ return strStartsWith; }
	
    Boolean isTest = false;
    public void setIsTest(Boolean setting){ isTest = setting; }
    public Boolean getIsTest(){ return isTest; }
    
    /*
     * Task goals.
     * Remove metadata prior to deploy.
     * Remove all custom or by type.
     * Remove only unit test files (@IsTest)
     * Support for piping file names to be deleted using ls.
     * 
     * Example use cases:
     * Remove all unit tests.
     * Remove all metadata with a matching namespace prefix.
     * Remove all Apex classes.
     * Remove all Visualforce Pages.
     * 
     * FailOnError = true | false
     * 
     * Dependency checks. Must be declared in order of dependency.
     * 
     * Chunking. API supports 10 records at a time. Provide abstraction to delete all in one operation.
     * Local vs API. Declaration applies to cloud definition of files? or local files?
     * Make this configurable?
     * 
     * Local could support grep of files. 
     * <leap:clean type="remote" metadatatype="ApexClass".
     * Task uses metadata API to determine scope of files to delete.
     * Useful in Git as system of record use cases.
     * 
     * <leap:clean type="ApexClass" source="local" grep="@IsTest src/classes/ADM* -l" />
     * <leap:clean type="ApexClass" namespace="ADM" IsTest="True" />
     * 
     * Maybe this should be a separate deleteFiles task to clearly distinguish from goals of 'clean'. 
     * 
     * Task uses the local file system for determining scope of metadata to remove. 
     * 
     * Use case: clean an org of all metadata of certain type, which may not be replicated locally.
     * 
     */
	public void execute() {
		//this.doQuery();
		//this.doApexClassQuery();
		//this.doToolingAPISandbox();
	}
	
	private void doQuery(){
		ListMetadataQuery query = new ListMetadataQuery();
		//query.setFolder("classes");
		query.setType("ApexClass");
		
		try {
			FileProperties[] lmr = this.salesforceConnection().getMetadataConnection().listMetadata( 
					new ListMetadataQuery[] {query}, 30.0);
			if (lmr != null) {
				for (FileProperties n : lmr) {
					if( n.getFullName().toLowerCase().startsWith("adm") ){
						System.out.println("FullName: " + n.getFullName());
						System.out.println("Filename: " + n.getFileName() );
						System.out.println("Type: " + n.getType());
						System.out.println("Namespace Prefix: " + n.getNamespacePrefix() );
						System.out.println("Id: " + n.getId() );
						System.out.println("Created By Name: " + n.getCreatedByName() );
						//System.out.println("Namespace Prefix: " + n.get );
					}
				}
			}
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
	}
	
	private void doApexClassQuery(){
		String soql = "SELECT Id, Name, Body FROM ApexClass WHERE Status='Active'";
		try {
			QueryResult result = this.salesforceConnection().getPartnerConnection().query(soql);
			for(SObject obj : result.getRecords()){
				if( !obj.getField("Name").toString().toLowerCase().startsWith("adm") ){
					continue;
				}
				System.out.println("------------------------------------------------");
				System.out.println("ApexClass: " + obj.getField("Name") );
				System.out.println("Id: " + obj.getId() );
				System.out.println("Body: " + obj.getField("Body") );
			}
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
	}
	
	private void doDescribe(){
		try {
			DescribeMetadataResult result = this.salesforceConnection().getMetadataConnection().describeMetadata(30.0);
			for(DescribeMetadataObject obj : result.getMetadataObjects()){
				System.out.println("---------------------------------------" );
				System.out.println("obj.getDirectoryName(): " + obj.getDirectoryName());
				System.out.println("obj.getSuffix(): " + obj.getSuffix() );
				System.out.println("obj.getXmlName(): " + obj.getXmlName() );
				for(String fName : obj.getChildXmlNames()){
					System.out.println("\tname: " + fName);
				}
			}
		} catch (ConnectionException e) {			
			e.printStackTrace();
		}
	}
	
	private void doToolingAPISandbox(){
		/*
		HttpClient httpClient = new DefaultHttpClient();
		String sessionid = this.salesforceConnection().getPartnerConnection().getSessionHeader().getSessionId();
		HttpGet client = new HttpGet();
		*/
		
		/*
		System.out.println("Server URL: " + this.salesforceConnection().getLoginResult().getServerUrl() );
		String restBaseURL = "https://";
		try {
			URI serverUri = new URI( this.salesforceConnection().getLoginResult().getServerUrl() );
			System.out.println("getHost() " + serverUri.getHost() );
			restBaseURL += serverUri.getHost();
			//System.out.println("getHost() " + serverUri. );
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		*/
		
		//String sessionId = this.salesforceConnection().getPartnerConnection().getSessionHeader().getSessionId();		
		//sessionId = sessionId.split("!")[1];
		//System.out.println("SessionId: " + sessionId);
		
		//restBaseURL += "/services/data/v29.0/sobjects/Account/";
		//String uri = this.serverurl + "/services/data/v29.0/sobjects/Account/";
		RestClient client = new RestClient( this.salesforceConnection() );
		
		/*
		String response = client.restGet(sobjects/Account/);
		System.out.println("Response from URL " + restBaseURL);
		System.out.println(response);
		*/
		
		/*
		 curl https://cs14.salesforce.com/services/data/v29.0/ -H 'Authorization: Bearer AQoAQKCCgWc8ixvHbrG3kq.2fgP._x5TD8V903Zq8rOdLeLJe3F0DV9b8z4g31xOsHB5WvCLRWE_GkAQZr.dPZARTFEDk8yY'
		 
		 curl https://gus--SMAuto.cs14.my.salesforce.com/services/data/v29.0/ -H 'Authorization: Bearer AQoAQKCCgWc8ixvHbrG3kq.2fgP._x5TD8V903Zq8rOdLeLJe3F0DV9b8z4g31xOsHB5WvCLRWE_GkAQZr.dPZARTFEDk8yY'
		 
		 */		 
	}
}