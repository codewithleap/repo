package org.leap;

import java.util.ArrayList;
import java.util.List;
import org.leap.Leaplet;

import com.google.gson.Gson;

public class LeapTemplate {
	public List<Leaplet> leapletList = new ArrayList<Leaplet>();
	public String content;
	
	public LeapTemplate(){}
	
	public LeapTemplate withContent(String c){
		this.content = c;
		this.parseLeaplets();
		return this;
	}
	
	public boolean hasLeaplets(){
		return this.leapletList.size() > 0;
	}
	
	public Leaplet getLeapletByName(String name){
		for(Leaplet l : this.leapletList){
			if(l.name.equals(name)){
				return l;
			}
		}
		return null;
	}
	
	private LeapTemplate parseLeaplets(){
		String commentStartPattern = "/*{\"name\"";
		int jsonStart = 0;
		int jsonEnd = 0;
		
		while( (jsonStart = this.content.indexOf(commentStartPattern, jsonEnd)) != -1 ){
			jsonStart += 2; //Set pointer to first curly bracket in JSON definition
			jsonEnd = this.content.indexOf("}", jsonStart);
			jsonEnd += 1; // bump pointer one-off to end of definition
			
			String leapletDefinition = this.content.substring(jsonStart, jsonEnd);
			Leaplet l = new Gson().fromJson(leapletDefinition, Leaplet.class);
			
			int contentStart = jsonEnd;
			int contentEnd = this.content.indexOf("*/", contentStart) - 1;
			l.content = this.content.substring(contentStart, contentEnd);
			
			leapletList.add( l );
		}
		return this;
	}
}