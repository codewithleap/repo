package org.leap;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RestClient extends Object {
  private static BufferedReader reader = 
      new BufferedReader(new InputStreamReader(System.in));
  private static String OAUTH_ENDPOINT = "/services/oauth2/token";
  private static String REST_ENDPOINT = "/services/data";
  UserCredentials userCredentials;
  String restUri;
  Header oauthHeader;
  Header prettyPrintHeader = new BasicHeader("X-PrettyPrint", "1");
  Gson gson;
  OAuth2Response oauth2Response;

  public RestClient() {
    gson = new Gson();
  }

  public HttpResponse oauth2Login(UserCredentials userCredentials) {
    HttpResponse response = null;
    this.userCredentials = userCredentials;
    String loginHostUri = "https://" + 
        userCredentials.loginInstanceDomain + OAUTH_ENDPOINT;
    try {
      HttpClient httpClient = new DefaultHttpClient();
      HttpPost httpPost = new HttpPost(loginHostUri);
      StringBuffer requestBodyText = 
          new StringBuffer("grant_type=password");
      requestBodyText.append("&username=");
      requestBodyText.append(userCredentials.userName);
      requestBodyText.append("&password=");
      requestBodyText.append(userCredentials.password);
      requestBodyText.append("&client_id=");
      requestBodyText.append(userCredentials.consumerKey);
      requestBodyText.append("&client_secret=");
      requestBodyText.append(userCredentials.consumerSecret);
      StringEntity requestBody = 
          new StringEntity(requestBodyText.toString());
      requestBody.setContentType("application/x-www-form-urlencoded");
      httpPost.setEntity(requestBody);
      httpPost.addHeader(prettyPrintHeader);
      response = httpClient.execute(httpPost);
      if (  response.getStatusLine().getStatusCode() == 200 ) {
        InputStreamReader inputStream = new InputStreamReader( 
            response.getEntity().getContent() 
        );
        oauth2Response = gson.fromJson( inputStream, 
            OAuth2Response.class );
        restUri = oauth2Response.instance_url + REST_ENDPOINT + 
            "/v" + this.userCredentials.apiVersion +".0";
        System.out.println("\nSuccessfully logged in to instance: " + 
            restUri);
        oauthHeader = new BasicHeader("Authorization", "OAuth " + 
            oauth2Response.access_token);
      } else {
        System.out.println("An error has occured.");
        System.exit(-1);
      }
    } catch (UnsupportedEncodingException uee) {
      uee.printStackTrace();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    } catch (NullPointerException npe) {
      npe.printStackTrace();
    }
    return response;
  }
  
  public void setSessionID(String sessionId){
	  oauthHeader = new BasicHeader("Authorization", "Bearer " + sessionId);
  }
  
  public String restGet(String uri) {
	  String result = "";
	  printBanner("GET", uri);
	  try {
		  HttpClient httpClient = new DefaultHttpClient();
		  HttpGet httpGet = new HttpGet(uri);
		  httpGet.addHeader(oauthHeader);
		  httpGet.addHeader(prettyPrintHeader);
		  HttpResponse response = httpClient.execute(httpGet);
		  result = getBody( response.getEntity().getContent() );
	  } catch (IOException ioe) {
		  ioe.printStackTrace();
	  } catch (NullPointerException npe) {
		  npe.printStackTrace();
	  }
	  return result;
  }

  public String restPatch(String uri, String requestBody) {
	  String result = "";
	  printBanner("PATCH", uri);
	  try {
		  HttpClient httpClient = new DefaultHttpClient();
		  HttpPatch httpPatch = new HttpPatch(uri);
		  httpPatch.addHeader(oauthHeader);
		  httpPatch.addHeader(prettyPrintHeader);
		  StringEntity body = new StringEntity(requestBody);
		  body.setContentType("application/json");
		  httpPatch.setEntity(body);
		  HttpResponse response = httpClient.execute(httpPatch);
		  result = response.getEntity() != null ? 
				  getBody( response.getEntity().getContent() ) : "";
	  } catch (IOException ioe) {
		  ioe.printStackTrace();
	  } catch (NullPointerException npe) {
		  npe.printStackTrace();
	  }
	  return result;
  }

  public String restPatchXml(String uri, String requestBody) {
	  String result = "";
	  printBanner("PATCH", uri);
	  try {
		  HttpClient httpClient = new DefaultHttpClient();
		  HttpPatch httpPatch = new HttpPatch(uri);
		  httpPatch.addHeader(oauthHeader);
		  httpPatch.addHeader(prettyPrintHeader);
		  httpPatch.addHeader( new BasicHeader("Accept", "application/xml") );
		  StringEntity body = new StringEntity(requestBody);
		  body.setContentType("application/xml");
		  httpPatch.setEntity(body);
		  HttpResponse response = httpClient.execute(httpPatch);
		  result = getBody( response.getEntity().getContent() );
	  } catch (IOException ioe) {
		  ioe.printStackTrace();
	  } catch (NullPointerException npe) {
		  npe.printStackTrace();
	  }
	  return result;
  }

  public String restPost(String uri, String requestBody) {
	  String result = null;
	  printBanner("POST", uri);
	  try {
		  HttpClient httpClient = new DefaultHttpClient();
		  HttpPost httpPost = new HttpPost(uri);
		  httpPost.addHeader(oauthHeader);
		  httpPost.addHeader(prettyPrintHeader);
		  StringEntity body = new StringEntity(requestBody);
		  body.setContentType("application/json");
		  httpPost.setEntity(body);
		  HttpResponse response = httpClient.execute(httpPost);
		  result = getBody( response.getEntity().getContent() );
	  } catch (IOException ioe) {
		  ioe.printStackTrace();
	  } catch (NullPointerException npe) {
		  npe.printStackTrace();
	  }
	  return result;
  }

  /**
   * Extend the Apache HttpPost method to implement an HttpPost
   * method.
   */
  public static class HttpPatch extends HttpPost {
	  public HttpPatch(String uri) {
		  super(uri);
	  }

	  public String getMethod() {
		  return "PATCH";
	  }
  }

  static class OAuth2Response {
	  public OAuth2Response() {
	  }
	  String id;
	  String issued_at;
	  String instance_url;
	  String signature;
	  String access_token;
  }
  
  class UserCredentials {
	  String grantType;
	  String userName;
	  String password;
	  String consumerKey;
	  String consumerSecret;
	  String loginInstanceDomain;
	  String apiVersion;
  }

  // private methods
  private String getUserInput(String prompt) {
	  String result = "";
	  try {
		  System.out.print(prompt);
		  result = reader.readLine();
	  } catch (IOException ioe) {
		  ioe.printStackTrace();
	  }
	  return result;
  }

  private void printBanner(String method, String uri) {
	  System.out.println("\n--------------------------------------------------------------------\n");
	  System.out.println("HTTP Method: " + method);
	  System.out.println("REST URI: " + uri);
	  System.out.println("\n--------------------------------------------------------------------\n");
  }

  private String getBody(InputStream inputStream) {
	  String result = "";
	  try {
		  BufferedReader in = new BufferedReader(
				  new InputStreamReader(inputStream)
				  );
		  String inputLine;
		  while ( (inputLine = in.readLine() ) != null ) {
			  result += inputLine;
			  result += "\n";
		  }
		  in.close();
	  } catch (IOException ioe) {
		  ioe.printStackTrace();
	  }
	  return result;
  }

  private UserCredentials getUserCredentials() {
	  UserCredentials userCredentials = new UserCredentials();
	  userCredentials.loginInstanceDomain = 
			  getUserInput("Login Instance Domain: ");
	  userCredentials.apiVersion = getUserInput("API Version: ");
	  userCredentials.userName = getUserInput("UserName: ");
	  userCredentials.password = getUserInput("Password: ");
	  userCredentials.consumerKey = getUserInput("Consumer Key: ");
	  userCredentials.consumerSecret = getUserInput("Consumer Secret: ");
	  userCredentials.grantType = "password";
	  return userCredentials;
  }
}