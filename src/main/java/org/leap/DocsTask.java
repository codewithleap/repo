package org.leap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.leap.tooling.Method;
import org.leap.tooling.SymbolTableResponse;

import com.google.gson.Gson;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

public class DocsTask extends LeapTask {

	private String outputFolder = "/docs";

	public void setOutputFolder(String folder) {
		this.outputFolder = folder;
	}

	public String getOutputFolder() {
		return this.outputFolder;
	}

	public String getOutputPath() {
		return (this.getProjectRoot() + this.getOutputFolder());
	}

	@Override
	public void execute() {
		// RestClient rest = new RestClient( this.salesforceConnection() );
		String outputPath = this.getProjectRoot() + this.getOutputFolder();
		File outputDirectory = new File(outputPath);

		if (outputDirectory.exists()) {
			outputDirectory.delete();
		}
		outputDirectory.mkdir();

		this.createIndex();
		this.createHome();
		this.createNavigation();
		this.createContent();
	}

	/*
	 * TODO: Move all HTML to GitHub hosted templates (same pattern as SFields
	 * and Trigger generation tasks)
	 */
	private void createIndex() {
		StringBuilder builder = new StringBuilder();
		builder.append("<html>");
		builder.append("<body>");
		builder.append("<frameset cols=\"27%,80%\">");
		builder.append("<iframe src=\"navigation.html\" name=\"nav\" frameborder=\"0\" style=\"overflow:hidden;height:100%;width:27%\" height=\"100%\" width=\"27%\"></iframe>");
		builder.append("<iframe src=\"home.html\" name=\"content\" frameborder=\"0\" style=\"overflow:hidden;height:100%;width:73%\" height=\"100%\" width=\"73%\"></iframe>");
		builder.append("</frameset>");
		builder.append("</body>");
		builder.append("</html>");

		String fileName = this.getProjectRoot() + this.getOutputFolder()
				+ "/index.html";
		fileName = fileName.replace("//", "/");

		try {
			PrintWriter writer = new PrintWriter(fileName, "UTF-8");
			writer.write(builder.toString());
			writer.close();
			System.out.println("Created " + fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private void createHome() {
		StringBuilder builder = new StringBuilder();
		builder.append("<html>");
		builder.append("<body>");
		builder.append("<h1>Welcome</h1> Click on a class on the left to view Apex class details.");
		builder.append("</body>");
		builder.append("</html>");

		String fileName = this.getProjectRoot() + this.getOutputFolder()
				+ "/home.html";
		fileName = fileName.replace("//", "/");

		try {
			PrintWriter writer = new PrintWriter(fileName, "UTF-8");
			writer.write(builder.toString());
			writer.close();
			System.out.println("Created " + fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public void createNavigation() {
		StringBuilder builder = new StringBuilder();
		for (SObject obj : this.getAllApexClassDefinitions()) {
			String className = (String) obj.getField("Name");
			builder.append("<a href=\"" + className
					+ ".html\" target=\"content\">" + className + "</a><br/>");
		}

		String fileName = this.getProjectRoot() + this.getOutputFolder()
				+ "/navigation.html";
		fileName = fileName.replace("//", "/");

		try {
			PrintWriter writer = new PrintWriter(fileName, "UTF-8");
			writer.write(builder.toString());
			writer.close();
			System.out.println("Created " + fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public void createContent() {
		for (SObject obj : this.getAllApexClassDefinitions()) {
			String fileName = this.getProjectRoot() + this.getOutputFolder()
					+ "/" + (String) obj.getField("Name") + ".html";
			fileName = fileName.replace("//", "/");

			try {
				System.out.println("Creating " + fileName + "...");
				PrintWriter writer = new PrintWriter(fileName, "UTF-8");
				writer.write(this.getApexClassContent(obj.getId()));
				writer.close();
				System.out.println("Created " + fileName);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * TODO: Move all HTML to GitHub templates
	 */
	private String getApexClassContent(String id) {
		RestClient client = new RestClient(this.salesforceConnection());
		String url = "/tooling/sobjects/ApexClass/" + id;
		String jsonResponse = client.restGet(url);
		Gson gson = new Gson();
		StringBuilder builder = new StringBuilder();

		try {
			SymbolTableResponse response = gson.fromJson(jsonResponse,
					SymbolTableResponse.class);
			builder.append("<b>Methods</b><br/>");
			builder.append("<ul>");
			for (Method meth : response.getSymbolTable().getMethods()) {
				builder.append("<li>" + meth.getVisibility() + " "
						+ meth.getReturnType() + " " + meth.getName());
				builder.append("(");
				for (Object param : meth.getParameters()) {
					builder.append(param.toString() + " ");
				}
				builder.append(")");
				builder.append("</li>");
			}
			builder.append("</ul>");
		} catch (Exception ex) {
			builder.append(ex.getMessage());
			builder.append(jsonResponse);
		}
		return builder.toString();
	}

	private List<SObject> apexDefinitions = null;

	public List<SObject> getAllApexClassDefinitions() {
		if (apexDefinitions == null) {
			apexDefinitions = new ArrayList<SObject>();
			try {
				apexDefinitions = this
						.query("SELECT Id, Name FROM ApexClass ORDER BY Name");
			} catch (ConnectionException e) {
				e.printStackTrace();
			}
		}
		return apexDefinitions;
	}
}