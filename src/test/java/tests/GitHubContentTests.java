package tests;

import junit.framework.Assert;
import org.junit.Test;
import org.leap.GitHubContent;

public class GitHubContentTests {

	@Test
	public void fetchContentTests(){
		String url_str = "https://api.github.com/repos/cubiccompass/leap/contents/src/main/resources/org/leap/antlib.xml";
		
		GitHubContent gitObj = GitHubContent.get(url_str);
		Assert.assertEquals(gitObj.name, "antlib.xml");
        Assert.assertEquals(gitObj.path, "src/main/resources/org/leap/antlib.xml");
        Assert.assertEquals(gitObj.url, "https://api.github.com/repos/cubiccompass/leap/contents/src/main/resources/org/leap/antlib.xml?ref=master");
        Assert.assertEquals(gitObj.type, "file");
        Assert.assertEquals(gitObj.encoding, "base64");
        Assert.assertEquals(gitObj._links.html, "https://github.com/cubiccompass/leap/blob/master/src/main/resources/org/leap/antlib.xml");
        
        Assert.assertNotNull(gitObj.decodedContent());
        Assert.assertTrue(gitObj.decodedContent().startsWith("<antlib>"));
        Assert.assertTrue(gitObj.decodedContent().endsWith("</antlib>"));
	}
}