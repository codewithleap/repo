package org.leap;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class TriggerTask extends LeapTask {
	private String trigger_url = "https://api.github.com/repos/codewithleap/repo/contents/templates/src/triggers/TriggerTemplate.trigger";
	private String class_url = "https://api.github.com/repos/codewithleap/repo/contents/templates/src/classes/TriggerHandlerTemplate.cls";
	
	public void execute() {
		List<String> generatedFiles = new ArrayList<String>();
		
		for (int i = 0; i < this.sObjects().length; i++) {
			if( !sObjects()[i].getTriggerable() || objectIsIgnored(sObjects()[i].getName())){
				continue;
			}
			
			String triggerTemplate = this.getLeapTriggerTemplate().content;
			triggerTemplate	= triggerTemplate.replace("{{object_name}}", this.sObjects()[i].getName())
						.replace("{{class_name}}", formattedName(sObjects()[i].getName()));
			
			String classTemplate = this.getLeapClassTemplate().content;
			classTemplate	= classTemplate.replace("{{object_name}}", sObjects()[i].getName())
						.replace("{{class_name}}", formattedName(sObjects()[i].getName()));
			
			PrintWriter writer = null;
			try {
				//TODO: Check if these will overwrite
				String triggerFileName = this.getProjectRoot() + "triggers/" + formattedName(this.sObjects()[i].getName()) + "Trigger.trigger";
				generatedFiles.add(triggerFileName);
				writer = new PrintWriter(triggerFileName, "UTF-8");
				writer.write( triggerTemplate );
				writer.close();
				System.out.println("Created " + triggerFileName);
				
				String triggerMetaFileName = this.getProjectRoot() + "triggers/" + formattedName(this.sObjects()[i].getName()) + "Trigger.trigger-meta.xml";
				generatedFiles.add(triggerMetaFileName);
				writer = new PrintWriter(triggerMetaFileName, "UTF-8");
				writer.write( this.getLeapMetaTriggerTemplate().content );
				writer.close();
				System.out.println("Created " + triggerMetaFileName);
				
				String handlerFileName = this.getProjectRoot() + "classes/" + formattedName(this.sObjects()[i].getName()) + "TriggerHandler.cls";
				generatedFiles.add(handlerFileName);
				writer = new PrintWriter(handlerFileName, "UTF-8");
				writer.write( classTemplate );
				writer.close();
				System.out.println("Created " + handlerFileName);
				
				String handlerMetaFileName = this.getProjectRoot() + "classes/" + formattedName(this.sObjects()[i].getName()) + "TriggerHandler.cls-meta.xml";
				generatedFiles.add(handlerMetaFileName);
				writer = new PrintWriter(handlerMetaFileName, "UTF-8");
				writer.write( this.getLeapMetaClassTemplate().content );
				writer.close();
				System.out.println("Created " + handlerMetaFileName);				

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
	
	private String formattedName(String name){
		return name.replaceAll("__c", "").replaceAll("_", "");
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
    
    private String[] ignoreList = {"Partner"};
    private boolean objectIsIgnored(String name){
    	for(String object : ignoreList){
    		if(object.equals(name)){
    			return true;
    		}
    	}
    	return false;
    }
}