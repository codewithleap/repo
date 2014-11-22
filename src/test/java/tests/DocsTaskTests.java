package tests;

import org.junit.Test;
import org.leap.DocsTask;

public class DocsTaskTests {

	@Test
	public void executionTests() {
		DocsTask task = new DocsTask();
		task.setUsername(System.getenv("SALESFORCE_USERNAME"));
		task.setPassword(System.getenv("SALESFORCE_PASSWORD"));
		task.setToken(System.getenv("SALESFORCE_TOKEN"));
		task.setServerurl(System.getenv("SALESFORCE_SERVER_URL"));
		task.setProjectRoot(System.getenv("PROJECT_ROOT"));
		task.setMaxFiles(10); // Limit the number of files rendered for tests.

		task.execute();
	}
}