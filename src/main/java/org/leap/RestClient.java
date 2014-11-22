package org.leap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;

import com.google.gson.Gson;

@SuppressWarnings("deprecation")
public class RestClient extends Object {
	private static BufferedReader reader = new BufferedReader(
			new InputStreamReader(System.in));
	private final SalesforceConnection connection;
	private final String API_VERSION = "v30.0";
	// String restUri;
	String sessionId;
	Header oauthHeader;
	Header prettyPrintHeader = new BasicHeader("X-PrettyPrint", "1");
	Gson gson = new Gson();

	public RestClient(SalesforceConnection conn) {
		connection = conn;

		sessionId = conn.getPartnerConnection().getSessionHeader()
				.getSessionId();
		sessionId = sessionId.split("!")[1];

		oauthHeader = new BasicHeader("Authorization", "Bearer " + sessionId);
	}

	public String getBaseURL() {
		String restBaseURL = "https://";
		try {
			URI serverUri = new URI(connection.getLoginResult().getServerUrl());
			restBaseURL += serverUri.getHost();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		restBaseURL += "/services/data/" + API_VERSION + "/";
		return restBaseURL;
	}

	public String restGet(String uriOffset) {
		String result = "";
		if (uriOffset.startsWith("/")) {
			uriOffset = uriOffset.substring(1);
		}
		String url = this.getBaseURL() + uriOffset;
		// printBanner("GET", url);
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			httpGet.addHeader(oauthHeader);
			httpGet.addHeader(prettyPrintHeader);

			HttpResponse response = httpClient.execute(httpGet);
			result = getBody(response.getEntity().getContent());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (NullPointerException npe) {
			npe.printStackTrace();
		}
		return result;
	}

	public String restPatch(String uri, String requestBody) {
		String result = "";
		// printBanner("PATCH", uri);
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPatch httpPatch = new HttpPatch(uri);
			httpPatch.addHeader(oauthHeader);
			httpPatch.addHeader(prettyPrintHeader);
			StringEntity body = new StringEntity(requestBody);
			body.setContentType("application/json");
			httpPatch.setEntity(body);
			HttpResponse response = httpClient.execute(httpPatch);
			result = response.getEntity() != null ? getBody(response
					.getEntity().getContent()) : "";
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (NullPointerException npe) {
			npe.printStackTrace();
		}
		return result;
	}

	public String restPatchXml(String uri, String requestBody) {
		String result = "";
		// printBanner("PATCH", uri);
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPatch httpPatch = new HttpPatch(uri);
			httpPatch.addHeader(oauthHeader);
			httpPatch.addHeader(prettyPrintHeader);
			httpPatch.addHeader(new BasicHeader("Accept", "application/xml"));
			StringEntity body = new StringEntity(requestBody);
			body.setContentType("application/xml");
			httpPatch.setEntity(body);
			HttpResponse response = httpClient.execute(httpPatch);
			result = getBody(response.getEntity().getContent());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (NullPointerException npe) {
			npe.printStackTrace();
		}
		return result;
	}

	public String restPost(String uri, String requestBody) {
		String result = null;
		// printBanner("POST", uri);
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(uri);
			httpPost.addHeader(oauthHeader);
			httpPost.addHeader(prettyPrintHeader);
			StringEntity body = new StringEntity(requestBody);
			body.setContentType("application/json");
			httpPost.setEntity(body);
			HttpResponse response = httpClient.execute(httpPost);
			result = getBody(response.getEntity().getContent());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (NullPointerException npe) {
			npe.printStackTrace();
		}
		return result;
	}

	/**
	 * Extend the Apache HttpPost method to implement an HttpPost method.
	 */
	public static class HttpPatch extends HttpPost {
		public HttpPatch(String uri) {
			super(uri);
		}

		@Override
		public String getMethod() {
			return "PATCH";
		}
	}

	private void printBanner(String method, String uri) {
		System.out
				.println("\n--------------------------------------------------------------------\n");
		System.out.println("HTTP Method: " + method);
		System.out.println("REST URI: " + uri);
		System.out
				.println("\n--------------------------------------------------------------------\n");
	}

	private String getBody(InputStream inputStream) {
		String result = "";
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					inputStream));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				result += inputLine;
				result += "\n";
			}
			in.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return result;
	}
}