package tests;

import junit.framework.Assert;

import org.apache.tools.ant.BuildException;
import org.junit.Test;
import org.leap.LeapTask;

public class LeapTaskTests {
	
	@Test
	public void basicTests(){
		LeapTask task = new LeapTask();
		try{
			task.validateConnectionParams();
			Assert.fail("Expected validation to throw BuildException. Missing Salesforce connection params.");
		} catch(BuildException ex){
			// Expected
		}
	}
}