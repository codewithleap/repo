package org.leap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.sforce.soap.partner.DescribeGlobalSObjectResult;

/*
 * Generates an Apex wrapper class for a SObject
 */
public class WrapperTask extends LeapTask {
	private String base_class_url 	= "https://api.github.com/repos/codewithleap/repo/contents/templates/src/classes/LeapWrapperBase.cls";
	private String class_url 		= "https://api.github.com/repos/codewithleap/repo/contents/templates/src/classes/WrapperTemplate.cls";
	private String test_url 		= "https://api.github.com/repos/codewithleap/repo/contents/templates/src/classes/WrapperTemplate_Tests.cls";
	List<String> generatedFiles 	= new ArrayList<String>();
	
	public void execute() {
		try {
			this.generateWrapperBaseClass();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}
		
		System.out.println("Objects: " + this.objects);
		for (int i = 0; i < this.sObjects().length; i++) {
			if( objectIsIgnored(sObjects()[i].getName()) ){
				continue;
			}
			
			try {
				this.generateWrapperClass(this.sObjects()[i]);				
				this.generateTestFile(sObjects()[i]);
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
	
	private void generateWrapperBaseClass() throws FileNotFoundException, UnsupportedEncodingException{
		String contentTemplate = this.getLeapBaseClassTemplate().content;
		
		String fileName = this.getProjectRoot() + "classes/LeapWrapperBase.cls";
		generatedFiles.add(fileName);
		PrintWriter writer = new PrintWriter(fileName, "UTF-8");
		writer.write( contentTemplate );
		writer.close();
		System.out.println("Created " + fileName);
		
		String metaFileName = this.getProjectRoot() + "classes/LeapWrapperBase.cls-meta.xml";
		generatedFiles.add(metaFileName);
		writer = new PrintWriter(metaFileName, "UTF-8");
		writer.write( this.getLeapMetaClassTemplate().content );
		writer.close();
		System.out.println("Created " + metaFileName);
	}
	
	private void generateWrapperClass(DescribeGlobalSObjectResult sobject) throws FileNotFoundException, UnsupportedEncodingException{
		String contentTemplate = this.getLeapClassTemplate().content;
		contentTemplate	= contentTemplate.replace("{{object_name}}", sobject.getName())
					.replace("{{formatted_name}}", formattedName(sobject.getName()));
		
		String fileName = this.getProjectRoot() + "classes/SFDC" + formattedName(sobject.getName()) + ".cls";
		if(fileName.length() > this.MAX_FILE_NAME_SIZE){
			fileName = this.truncateFileName(fileName);
		}
		
		File f = new File(fileName);
		if(f.exists()){ return; } // Do not overwrite if already generated
		
		generatedFiles.add(fileName);
		PrintWriter writer = new PrintWriter(fileName, "UTF-8");
		writer.write( contentTemplate );
		writer.close();
		System.out.println("Created " + fileName);
		
		String metaFileName = this.getProjectRoot() + "classes/SFDC" + formattedName(sobject.getName()) + ".cls-meta.xml";
		if(metaFileName.length() > this.MAX_FILE_NAME_SIZE){
			metaFileName = this.truncateFileName(metaFileName);
		}
		generatedFiles.add(metaFileName);
		writer = new PrintWriter(metaFileName, "UTF-8");
		writer.write( this.getLeapMetaClassTemplate().content );
		writer.close();
		System.out.println("Created " + metaFileName);
	}
	
	private void generateTestFile(DescribeGlobalSObjectResult sobject) throws FileNotFoundException, UnsupportedEncodingException{
		String contentTemplate = this.getLeapTestTemplate().content;
		contentTemplate	= contentTemplate.replace("{{object_name}}", sobject.getName())
					.replace("{{formatted_name}}", formattedName(sobject.getName()));
		
		String fileName = this.getProjectRoot() + "classes/SFDC" + formattedName(sobject.getName()) + "_Tests.cls";
		if(fileName.length() > this.MAX_FILE_NAME_SIZE){
			fileName = this.truncateFileName(fileName);
		}
		
		File f = new File(fileName);
		if(f.exists()){ return; } // Do not overwrite if already generated
		
		generatedFiles.add(fileName);
		PrintWriter writer = new PrintWriter(fileName, "UTF-8");
		writer.write( contentTemplate );
		writer.close();
		System.out.println("Created " + fileName);
		
		String metaFileName = this.getProjectRoot() + "classes/SFDC" + formattedName(sobject.getName()) + "_Tests.cls-meta.xml";
		if(metaFileName.length() > this.MAX_FILE_NAME_SIZE){
			metaFileName = this.truncateFileName(metaFileName);
		}
		generatedFiles.add(metaFileName);
		writer = new PrintWriter(metaFileName, "UTF-8");
		writer.write( this.getLeapMetaClassTemplate().content );
		writer.close();
		System.out.println("Created " + metaFileName);
	}
	
	private LeapTemplate m_leapBaseClassTemplate = null;
	public LeapTemplate getLeapBaseClassTemplate(){
    	if(m_leapBaseClassTemplate == null){
    		m_leapBaseClassTemplate = new LeapTemplate().withContent( this.getBaseClassTemplate().decodedContent() );
    	}
    	return m_leapBaseClassTemplate;
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
    
    private GitHubContent m_baseTemplate = null;
    protected GitHubContent getBaseClassTemplate(){
    	if(m_baseTemplate == null){
    		m_baseTemplate = GitHubContent.get(this.base_class_url);
    	}
    	return m_baseTemplate;
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
	
	private String formattedName(String name){
		return name.replaceAll("__c", "").replaceAll("_", "");
	}
	
	private String[] ignoreList = {"Partner", "CollaborationGroup", "CollaborationGroupMember", "CollaborationGroupMemberRequest"};
    private boolean objectIsIgnored(String name){
    	for(String object : ignoreList){
    		if(object.equals(name)){
    			return true;
    		}
    	}
    	return false;
    }
}