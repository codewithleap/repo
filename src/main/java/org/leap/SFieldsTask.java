package org.leap;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.sforce.soap.partner.DescribeSObjectResult;
import com.sforce.soap.partner.Field;
import com.sforce.ws.ConnectionException;

public class SFieldsTask extends LeapTask {
	private String class_template_url = "https://api.github.com/repos/codewithleap/repo/contents/templates/src/classes/LeapSFieldsTemplate.cls";
	private String ROW_TEMPLATE	= "public static final String {{object_name}} = '{{field_list}}';";
	private String MERGE_TEMPLATE_TAG = "{placeholder}";
    
    Integer limit = -1;
    public void setLimit(String l){
    	limit = Integer.valueOf(l);
    }
    
    boolean customOnly = false;
    public void setCustomOnly(String setting) {
    	customOnly = Boolean.valueOf(setting);
    }
    
	public void execute() {
		this.validateConnectionParams();
		
		Integer recordCount = (this.limit == -1 ? this.sObjects().length : this.limit);
		System.out.println("Generating Apex class file for objects: " + this.objects + "...");
		
		String fieldContent = this.getSFieldEntries();
		String finalOutput = this.getClassTemplate().decodedContent()
			.replace(MERGE_TEMPLATE_TAG, fieldContent);
		
		if(this.targetFileName != null){
			finalOutput = finalOutput.replaceAll("class LeapSFields", "class " + this.targetFileName);
		}
		
		PrintWriter writer = null;
		try {
			String fileName = (this.targetFileName != null ? this.targetFileName : "LeapSFields");
			String classFileName = this.getProjectRoot() + "classes/" + fileName + ".cls";
			writer = new PrintWriter(classFileName, "UTF-8");
			writer.write( finalOutput );
			writer.close();
			System.out.println("Created " + classFileName);
			
			String metaFileName = this.getProjectRoot() + "classes/" + fileName + ".cls-meta.xml";
			writer = new PrintWriter(metaFileName, "UTF-8");
			writer.write( this.getMetaTemplate().decodedContent() );
			writer.close();
			System.out.println("Created " + metaFileName);
			
			if (deployOption) {
				List<String> generatedFiles = new ArrayList<String>();
				generatedFiles.add(classFileName);
				generatedFiles.add(metaFileName);
				deployGeneratedFiles(generatedFiles);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		System.out.println("Done.");
	}
	
	private LeapTemplate m_leapTemplate = null;
    public LeapTemplate getLeapTemplate(){
    	if(m_leapTemplate == null){
    		m_leapTemplate = new LeapTemplate().withContent( this.getClassTemplate().decodedContent() );
    	}
    	return m_leapTemplate;
    }
        
    private GitHubContent m_metaTemplate = null;
    protected GitHubContent getMetaTemplate(){
    	if(m_metaTemplate == null){
    		m_metaTemplate = GitHubContent.get(this.metaClassURL);
    	}
    	return m_metaTemplate;
    }
    
    private GitHubContent m_classTemplate = null;
    protected GitHubContent getClassTemplate(){
    	if(m_classTemplate == null){
    		m_classTemplate = GitHubContent.get(this.class_template_url);
    	}
    	return m_classTemplate;
    }
	
	private String getSFieldEntries(){
		String output = "";
		Integer counter = 0;		
		for (int i = 0; i < this.sObjects().length; i++) {
			if(!this.objects.equals("all") && !this.objectList().contains(sObjects()[i].getName().toLowerCase() )){
				continue;
			}
			if(this.limit != -1 && counter++ > this.limit){ break;}
			
			String object_name = "ALL_" + this.getFormattedObjectName(this.sObjects()[i]) + "_FIELDS";
			String sfieldTemplate = this.ROW_TEMPLATE;
			sfieldTemplate = sfieldTemplate.replace("{{object_name}}", object_name);
			
			String field_list = "";
			
			DescribeSObjectResult describeSObjectResult = null;
			try {
				describeSObjectResult = this.salesforceConnection().getPartnerConnection().describeSObject(this.sObjects()[i].getName());
			} catch (ConnectionException e) {
				e.printStackTrace();
			}
			
			Field[] fields = describeSObjectResult.getFields();
			
			for (int j = 0; j < fields.length; j++) {
				Field field = fields[j];
				if(this.ignoreFieldList().size() > 0 && this.ignoreFieldList().contains(field.getName().toLowerCase() )){
					continue;
				}
				field_list += field.getName() + ",";
			}
			field_list = field_list.substring(0, field_list.length() - 1);
			sfieldTemplate = sfieldTemplate.replace("{{field_list}}", field_list);
			output += "\t" + sfieldTemplate + "\n";
		}
		return output;
	}
}