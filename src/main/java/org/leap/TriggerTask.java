package org.leap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.sforce.soap.partner.DescribeGlobalSObjectResult;

public class TriggerTask extends LeapTask {	
	private String trigger_url = "https://api.github.com/repos/codewithleap/repo/contents/templates/src/triggers/TriggerTemplate.trigger";
	private String class_url = "https://api.github.com/repos/codewithleap/repo/contents/templates/src/classes/TriggerHandlerTemplate.cls";
	private String test_url = "https://api.github.com/repos/codewithleap/repo/contents/templates/src/classes/TriggerHandlerTemplate_Tests.cls";
	List<String> generatedFiles = new ArrayList<String>();
	
	public void execute() {
		for (int i = 0; i < this.sObjects().length; i++) {
			if( !sObjects()[i].getTriggerable() || this.objectIsIgnored(sObjects()[i].getName()) ){
				continue;
			}
			
			try {
				this.generateTriggerFiles(this.sObjects()[i]);
				this.generateTriggerHandlerFiles(sObjects()[i]);
				this.generateTestFiles(sObjects()[i]);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		if (deployOption) {
			deployGeneratedFiles(generatedFiles);
		}
	}
	
	private void generateTriggerFiles(DescribeGlobalSObjectResult sobject) throws FileNotFoundException, UnsupportedEncodingException{
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
	
	private void generateTriggerHandlerFiles(DescribeGlobalSObjectResult sobject) throws FileNotFoundException, UnsupportedEncodingException{
		String classTemplate = this.getLeapClassTemplate().content;
		classTemplate	= classTemplate.replace("{{object_name}}", sobject.getName())
					.replace("{{class_name}}", formattedName(sobject.getName()));
		
		String handlerFileName = this.getProjectRoot() + "classes/" + formattedName(sobject.getName()) + "TriggerHandler.cls";
		if(handlerFileName.length() > this.MAX_FILE_NAME_SIZE){
			handlerFileName = this.truncateFileName(handlerFileName);
		}
		
		File f = new File(handlerFileName);
		if(f.exists()){ return; } // Do not overwrite if already generated
		
		generatedFiles.add(handlerFileName);
		PrintWriter writer = new PrintWriter(handlerFileName, "UTF-8");
		writer.write( classTemplate );
		writer.close();
		System.out.println("Created " + handlerFileName);
		
		String handlerMetaFileName = this.getProjectRoot() + "classes/" + formattedName(sobject.getName()) + "TriggerHandler.cls-meta.xml";
		if(handlerMetaFileName.length() > this.MAX_FILE_NAME_SIZE){
			handlerMetaFileName = this.truncateFileName(handlerMetaFileName);
		}
		generatedFiles.add(handlerMetaFileName);
		writer = new PrintWriter(handlerMetaFileName, "UTF-8");
		writer.write( this.getLeapMetaClassTemplate().content );
		writer.close();
		System.out.println("Created " + handlerMetaFileName);
	}
	
	private void generateTestFiles(DescribeGlobalSObjectResult sobject) throws FileNotFoundException, UnsupportedEncodingException{
		String testTemplate = this.getLeapTestTemplate().content;
		testTemplate = testTemplate.replace("{{object_name}}", sobject.getName())
					.replace("{{class_name}}", formattedName(sobject.getName()));
		
		String testFileName = this.getProjectRoot() + "classes/" + formattedName(sobject.getName()) + "TriggerHandler_Tests.cls";
		if(testFileName.length() > this.MAX_FILE_NAME_SIZE){
			testFileName = this.truncateFileName(testFileName);
		}
		
		File f = new File(testFileName);
		if(f.exists()){ return; } // Do not overwrite if already generated
		
		generatedFiles.add(testFileName);
		PrintWriter writer = new PrintWriter(testFileName, "UTF-8");
		writer.write( testTemplate );
		writer.close();
		System.out.println("Created " + testFileName);
		
		String testMetaFileName = this.getProjectRoot() + "classes/" + formattedName(sobject.getName()) + "TriggerHandler_Tests.cls-meta.xml";
		if(testMetaFileName.length() > this.MAX_FILE_NAME_SIZE){
			testMetaFileName = this.truncateFileName(testMetaFileName);
		}
		generatedFiles.add(testMetaFileName);
		writer = new PrintWriter(testMetaFileName, "UTF-8");
		writer.write( this.getLeapMetaClassTemplate().content );
		writer.close();
		System.out.println("Created " + testMetaFileName);
	}
	
	private String formattedName(String name){
		return name.replaceAll("__c", "").replaceAll("_", "");
	}
	
	private String truncateFileName(String fName){
		return fName.substring(0, this.MAX_FILE_NAME_SIZE);
	}
	
	private LeapTemplate m_leapClassTemplate = null;
    public LeapTemplate getLeapClassTemplate(){
    	if(m_leapClassTemplate == null){
    		m_leapClassTemplate = new LeapTemplate().withContent( this.getClassTemplate().decodedContent() );
    	}
    	return m_leapClassTemplate;
    }
    
    private LeapTemplate m_leapMetaClassTemplate = null;
    public LeapTemplate getLeapMetaClassTemplate(){
    	if(m_leapMetaClassTemplate == null){
    		m_leapMetaClassTemplate = new LeapTemplate().withContent( this.getClassMetaTemplate().decodedContent() );
    	}
    	return m_leapMetaClassTemplate;
    }
    
    private LeapTemplate m_leapTestTemplate = null;
    public LeapTemplate getLeapTestTemplate(){
    	if(m_leapTestTemplate == null){
    		m_leapTestTemplate = new LeapTemplate().withContent( this.getTestTemplate().decodedContent() );
    	}
    	return m_leapTestTemplate;
    }
	
	private LeapTemplate m_leapTriggerTemplate = null;
    public LeapTemplate getLeapTriggerTemplate(){
    	if(m_leapTriggerTemplate == null){
    		m_leapTriggerTemplate = new LeapTemplate().withContent( this.getTriggerTemplate().decodedContent() );
    	}
    	return m_leapTriggerTemplate;
    }
    
    private LeapTemplate m_leapMetaTriggerTemplate = null;
    public LeapTemplate getLeapMetaTriggerTemplate(){
    	if(m_leapMetaTriggerTemplate == null){
    		m_leapMetaTriggerTemplate = new LeapTemplate().withContent( this.getTriggerMetaTemplate().decodedContent() );
    	}
    	return m_leapMetaTriggerTemplate;
    }
    
    private GitHubContent m_classTemplate = null;
    protected GitHubContent getClassTemplate(){
    	if(m_classTemplate == null){
    		m_classTemplate = GitHubContent.get(this.class_url);
    	}
    	return m_classTemplate;
    }
       
    private GitHubContent m_classMetaTemplate = null;
    protected GitHubContent getClassMetaTemplate(){
    	if(m_classMetaTemplate == null){
    		m_classMetaTemplate = GitHubContent.get(this.metaClassURL);
    	}
    	return m_classMetaTemplate;
    }
    
    private GitHubContent m_testTemplate = null;
    protected GitHubContent getTestTemplate(){
    	if(m_testTemplate == null){
    		m_testTemplate = GitHubContent.get(this.test_url);
    	}
    	return m_testTemplate;
    }
    
    private GitHubContent m_triggerTemplate = null;
    protected GitHubContent getTriggerTemplate(){
    	if(m_triggerTemplate == null){
    		m_triggerTemplate = GitHubContent.get(this.trigger_url);
    	}
    	return m_triggerTemplate;
    }
        
    private GitHubContent m_triggerMetaTemplate = null;
    protected GitHubContent getTriggerMetaTemplate(){
    	if(m_triggerMetaTemplate == null){
    		m_triggerMetaTemplate = GitHubContent.get(this.metaTriggerURL);
    	}
    	return m_triggerMetaTemplate;
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