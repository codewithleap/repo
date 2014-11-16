package org.leap;

import java.util.ArrayList;
import java.util.List;

import com.sforce.soap.partner.DeleteResult;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

public class DeleteObjectsTask extends LeapTask {
	private final int MAX_DELETE_RECORDS = 200;
	
	/*
	 * Example: Deleting all unit test classes.
	 * SELECT Id, Name FROM ApexClass WHERE Name LIKE 'PREFIX_%Test'
	 */
	String query = "";
	public void setQuery(String q) { query = q; }
	public String getQuery(){ return query; }
	
    public void execute() {
    	try {
			List<SObject> deleteObjects = this.getQueryObjects();
			this.deleteObjects(deleteObjects);
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
    }
    
    public List<SObject> getQueryObjects() throws ConnectionException{
		List<SObject> sObjects = new ArrayList<SObject>();
		try {
			QueryResult result = this.salesforceConnection().getPartnerConnection().query( this.getQuery() );
			boolean done = false;
			
			if(result.getSize() > 0){
				while(!done){
					SObject[] records = result.getRecords();
					for ( int i = 0; i < records.length; ++i ) {
						sObjects.add(records[i]);
					}
					if (result.isDone()) {
						done = true;
					} else {
						result = this.salesforceConnection().getPartnerConnection().queryMore(result.getQueryLocator());
					}
				}
			}
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
		return sObjects;
	}
    
    public void deleteObjects(List<SObject> sobjectList){
    	// Delete records 200 at a time
    	List<String> objectIDs = new ArrayList<String>();
    	int count = 0;
    	for(SObject obj : sobjectList){
    		if(count++ >= MAX_DELETE_RECORDS){
    			deleteObjectChunks( objectIDs );
    			objectIDs.clear();
    			count = 0;
    		}
    		objectIDs.add( obj.getId() );
    	}
    	deleteObjectChunks( objectIDs );
    }
    
    public int deleteObjectChunks(List<String> objectIDs){
    	try {
			DeleteResult[] results = this.salesforceConnection().getPartnerConnection().delete( objectIDs.toArray( objectIDs.toArray(new String[objectIDs.size()]) ) );
			for(DeleteResult r : results){
				if(!r.isSuccess()){
					System.out.println("Error deleting object: " + r.getErrors()[0].getMessage() );
				}
			}
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
    	return objectIDs.size();
    }
}