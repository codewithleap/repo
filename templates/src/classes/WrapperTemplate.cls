public with sharing class SFDC{{formatted_name}} extends LeapWrapperBase {
	public {{object_name}} record   = null;
	public static final String SFIELDS = LeapSFields.ALL_ACCOUNT_FIELDS;
    
	public SFDC{{formatted_name}}(){}
	
	public SFDC{{formatted_name}} withSObject({{object_name}} record){
		this.record = record;
		this.id = record.Id;
		return this;
	}
	
	public SFDC{{formatted_name}} withId(ID recordId){
		id = recordId;
		record = Database.query('SELECT ' + SFIELDS + ' FROM {{object_name}} WHERE Id=:id LIMIT 1');
		return this;
	}
	
	public SFDC{{formatted_name}} validate(){
		this.errors.clear();
		/*
		Validate fields here.
		if not valid 
		this.success = false; 
		this.errors.add('err message');
		*/
		return this;
	}
	
	public SFDC{{formatted_name}} doSomething(){
		return this;
	}
	
	public String toJSON(){
		Map<String, String> r = new Map<String, String>();
		List<String> fieldNames = SFIELDS.split(',');
		for(String fName : fieldNames){
			r.put(fName, String.valueOf( this.record.get(fName) ));
		}
		return JSON.serialize(r);
	}
}