package tests;

import org.junit.Test;
import org.leap.DocsTask;

public class DocsTaskTests {

	/*
	 * @Test public void initializationTests() { DocsTask task = new DocsTask();
	 * task.setUsername(System.getenv("SALESFORCE_USERNAME"));
	 * task.setPassword(System.getenv("SALESFORCE_PASSWORD"));
	 * task.setToken(System.getenv("SALESFORCE_TOKEN"));
	 * task.setServerurl(System.getenv("SALESFORCE_SERVER_URL"));
	 * Assert.assertTrue(task.salesforceConnection().isValid());
	 * 
	 * List<SObject> records = task.getAllApexClassDefinitions(); for (SObject
	 * obj : records) { System.out.println("Id: " + obj.getId() + " " +
	 * obj.getField("Name")); } System.out.println(records.size() +
	 * " ApexClass records returned.");
	 * 
	 * RestClient client = new RestClient(task.salesforceConnection()); String
	 * url = "/tooling/sobjects/ApexClass/" + records.get(0).getId(); String
	 * jsonResponse = client.restGet(url); Assert.assertNotNull(jsonResponse);
	 * System.out.println("REST GET response:");
	 * System.out.println(jsonResponse);
	 * 
	 * Gson gson = new Gson(); SymbolTableResponse response =
	 * gson.fromJson(jsonResponse, SymbolTableResponse.class);
	 * Assert.assertNotNull(response); System.out.println("API Version: " +
	 * response.getMetadata().getApiVersion());
	 * Assert.assertTrue(response.getMetadata().getApiVersion() > 15.0);
	 * 
	 * System.out.println("Total Methods in class: " +
	 * response.getSymbolTable().getMethods().size());
	 * Assert.assertNotNull(response.getSymbolTable());
	 * Assert.assertTrue(response.getSymbolTable().getMethods().size() >= 0);
	 * 
	 * for (Method meth : response.getSymbolTable().getMethods()) {
	 * System.out.println("\t" + meth.getName()); }
	 * 
	 * client = new RestClient(task.salesforceConnection()); url =
	 * "/tooling/sobjects/ApexClass/" + records.get(0).getId(); jsonResponse =
	 * client.restGet(url); Assert.assertNotNull(jsonResponse);
	 * System.out.println("ApexClassMember REST GET response:");
	 * System.out.println(jsonResponse);
	 */

	/*
	 * Gson gson = new Gson(); SymbolTableResponse response =
	 * gson.fromJson(jsonResponse, SymbolTableResponse.class);
	 */
	// }

	@Test
	public void executionTests() {
		DocsTask task = new DocsTask();
		task.setUsername(System.getenv("SALESFORCE_USERNAME"));
		task.setPassword(System.getenv("SALESFORCE_PASSWORD"));
		task.setToken(System.getenv("SALESFORCE_TOKEN"));
		task.setServerurl(System.getenv("SALESFORCE_SERVER_URL"));
		task.setProjectRoot(System.getenv("PROJECT_ROOT"));

		task.execute();
	}

	/*
	 * public List<SObject> getAllApexClasses(){ return records; }
	 */
}