package tests;

import junit.framework.Assert;
import org.junit.Test;
import org.leap.LeapTemplate;

public class LeapTemplateTests {
	private String TEST_TEMPLATE = "public with sharing class TestClass{" +
			"	public TestClass(){}" +
			"	{placeholder}" +
			"}";
	
	private String LEAPLET_TEMPLATE = "public with sharing class TestClass{" +
			"	/*{\"name\":\"testLeaplet\", \"version\":\"1\"}" +
			"	private String {field_name};" +
			"	*/" +
			"	public TestClass(){}" +
			"	{placeholder}" +
			"}";
	
	private String MULTI_LEAPLET_TEMPLATE = "public with sharing class TestClass{" +
			"	/*{\"name\":\"testLeaplet\", \"version\":\"1\"}" +
			"	private String {field_name};" +
			"	*/" +
			"	/*{\"name\":\"multiLeaplet\", \"version\":\"1\"}" +
			"	private String {field_name};" +
			"	*/" +
			"	public TestClass(){}" +
			"	{placeholder}" +
			"}";
	
	@Test
	public void basicTests(){
		LeapTemplate template = new LeapTemplate().withContent(TEST_TEMPLATE);
		Assert.assertEquals(0, template.leapletList.size());
		
		template = new LeapTemplate().withContent(LEAPLET_TEMPLATE);
		Assert.assertEquals(1, template.leapletList.size());
		Assert.assertEquals("testLeaplet", template.leapletList.get(0).name);
		Assert.assertEquals("	private String {field_name};", template.leapletList.get(0).content);
		Assert.assertEquals("1", template.leapletList.get(0).version);
		
		template = new LeapTemplate().withContent(MULTI_LEAPLET_TEMPLATE);
		Assert.assertEquals(2, template.leapletList.size());
		Assert.assertEquals("testLeaplet", template.leapletList.get(0).name);
		Assert.assertEquals("1", template.leapletList.get(0).version);
		Assert.assertEquals("multiLeaplet", template.leapletList.get(1).name);
		Assert.assertEquals("1", template.leapletList.get(1).version);
		Assert.assertEquals("	private String {field_name};", template.leapletList.get(1).content);
	}
}