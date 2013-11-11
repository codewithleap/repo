package org.leap;

import java.util.ArrayList;
import java.util.List;

/*
 * Generates an Apex wrapper class for a SObject
 */
public class WrapperTask extends LeapTask {
	private String trigger_url = "https://api.github.com/repos/codewithleap/repo/contents/templates/src/triggers/TriggerTemplate.trigger";
	private String class_url = "https://api.github.com/repos/codewithleap/repo/contents/templates/src/classes/TriggerHandlerTemplate.cls";
	private String test_url = "https://api.github.com/repos/codewithleap/repo/contents/templates/src/classes/TriggerHandlerTemplate_Tests.cls";
	List<String> generatedFiles = new ArrayList<String>();
	
	public void execute() {
		for (int i = 0; i < this.sObjects().length; i++) {
			if( objectIsIgnored(sObjects()[i].getName()) ){
				continue;
			}
			
			String sFieldsName = "ALL_" + this.getFormattedObjectName(this.sObjects()[i]) + "_FIELDS";
			/*
			try {
				this.generateTriggerFiles(this.sObjects()[i]);
				this.generateTriggerHandlerFiles(sObjects()[i]);
				this.generateTestFiles(sObjects()[i]);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			*/
		}
		
		if (deployOption) {
			deployGeneratedFiles(generatedFiles);
		}
	}
	
	/*
	private void generateWrapperFiles(DescribeGlobalSObjectResult sobject) throws FileNotFoundException, UnsupportedEncodingException{
		String triggerTemplate = this.getLeapTriggerTemplate().content;
		triggerTemplate	= triggerTemplate.replace("{{object_name}}", sobject.getName())
					.replace("{{class_name}}", formattedName(sobject.getName()));
		
		String triggerFileName = this.getProjectRoot() + "triggers/Leap" + formattedName(sobject.getName()) + "Trigger.trigger";
		if(triggerFileName.length() > this.MAX_FILE_NAME_SIZE){
			triggerFileName = this.truncateFileName(triggerFileName);
		}
		generatedFiles.add(triggerFileName);
		PrintWriter writer = new PrintWriter(triggerFileName, "UTF-8");
		writer.write( triggerTemplate );
		writer.close();
		System.out.println("Created " + triggerFileName);
		
		String triggerMetaFileName = this.getProjectRoot() + "triggers/Leap" + formattedName(sobject.getName()) + "Trigger.trigger-meta.xml";
		if(triggerMetaFileName.length() > this.MAX_FILE_NAME_SIZE){
			triggerMetaFileName = this.truncateFileName(triggerMetaFileName);
		}
		generatedFiles.add(triggerMetaFileName);
		writer = new PrintWriter(triggerMetaFileName, "UTF-8");
		writer.write( this.getLeapMetaTriggerTemplate().content );
		writer.close();
		System.out.println("Created " + triggerMetaFileName);
	}
	*/
	
	private String formattedName(String name){
		return name.replaceAll("__c", "").replaceAll("_", "");
	}
	
	private String[] ignoreList = {"Partner", "CollaborationGroup", "CollaborationGroupMember"};
    private boolean objectIsIgnored(String name){
    	for(String object : ignoreList){
    		if(object.equals(name)){
    			return true;
    		}
    	}
    	return false;
    }
}