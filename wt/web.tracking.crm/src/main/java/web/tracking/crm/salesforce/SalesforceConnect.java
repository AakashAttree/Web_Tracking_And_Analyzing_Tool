package web.tracking.crm.salesforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;


public class SalesforceConnect {

  static final String USERNAME = "m06nov-6yed@force.com";
  static final String PASSWORD = "test*123";
  static final String LOGINURL = "https://login.salesforce.com";
  static final String GRANTSERVICE = "/services/oauth2/token?grant_type=password";
  static final String CLIENTID =
      "3MVG9pe2TCoA1Pf4naJsr8pp_2lA2qIYujwglNzWn6iGcp5udlROBk8QrVDGE4eOBNgwJPUpTEeseZ9RWcneD";
  static final String CLIENTSECRET = "6489226665679131840";
  private static String REST_ENDPOINT = "/services/data";
  private static String API_VERSION = "/v32.0";
  private static String baseUri;
  private static Header oauthHeader;
  private static Header prettyPrintHeader = new BasicHeader("X-PrettyPrint", "1");
  private static String leadId;
  private static String leadFirstName;
  private static String leadLastName;
  private static String leadCompany;

  public static void main(String[] args) {

    HttpClient httpclient = HttpClientBuilder.create().build();

    // Assemble the login request URL
    String loginURL = LOGINURL + GRANTSERVICE + "&client_id=" + CLIENTID + "&client_secret="
        + CLIENTSECRET + "&username=" + USERNAME + "&password=" + PASSWORD;

    // Login requests must be POSTs
    HttpPost httpPost = new HttpPost(loginURL);
    HttpResponse response = null;

    try {
      // Execute the login POST request
      response = httpclient.execute(httpPost);
    } catch (ClientProtocolException cpException) {
      cpException.printStackTrace();
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }

    // verify response is HTTP OK
    final int statusCode = response.getStatusLine().getStatusCode();
    if (statusCode != HttpStatus.SC_OK) {
      System.out.println("Error authenticating to Force.com: " + statusCode);
      // Error is in EntityUtils.toString(response.getEntity())
      return;
    }

    String getResult = null;
    try {
      getResult = EntityUtils.toString(response.getEntity());
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }

    JsonObject jsonObject = null;
    String loginAccessToken = null;
    String loginInstanceUrl = null;

    try {
      jsonObject = new Gson().fromJson(getResult, JsonObject.class);
      loginAccessToken = jsonObject.get("access_token").getAsString();
      loginInstanceUrl = jsonObject.get("instance_url").getAsString();
    } catch (JsonIOException jsonException) {
      jsonException.printStackTrace();
    }

    baseUri = loginInstanceUrl + REST_ENDPOINT + API_VERSION;
    oauthHeader = new BasicHeader("Authorization", "OAuth " + loginAccessToken);
    System.out.println("oauthHeader1: " + oauthHeader);
    System.out.println("\n" + response.getStatusLine());
    System.out.println("Successful login");
    System.out.println("instance URL: " + loginInstanceUrl);
    System.out.println("access token/session ID: " + loginAccessToken);
    System.out.println("baseUri: " + baseUri);

    // Run codes to query, isnert, update and delete records in Salesforce using REST API
    queryLeads();
    createLeads();
    updateLeads();
    deleteLeads();

    // release connection
    httpPost.releaseConnection();
  }

  // Query Leads using REST HttpGet
  public static void queryLeads() {
    System.out.println("\n_______________ Lead QUERY _______________");
    try {

      // Set up the HTTP objects needed to make the request.
      HttpClient httpClient = HttpClientBuilder.create().build();

      String uri =
          baseUri + "/query?q=Select+Id+,+FirstName+,+LastName+,+Company+From+Lead+Limit+5";
      System.out.println("Query URL: " + uri);
      HttpGet httpGet = new HttpGet(uri);
      System.out.println("oauthHeader2: " + oauthHeader);
      httpGet.addHeader(oauthHeader);
      httpGet.addHeader(prettyPrintHeader);

      // Make the request.
      HttpResponse response = httpClient.execute(httpGet);

      // Process the result
      int statusCode = response.getStatusLine().getStatusCode();
      if (statusCode == 200) {
        String response_string = EntityUtils.toString(response.getEntity());
        try {
          JsonObject json = new Gson().fromJson(response_string, JsonObject.class);
          System.out.println("JSON result of Query:\n" + response_string);
          JsonArray j = json.getAsJsonArray("records");
          for (int i = 0; i < j.size(); i++) {
            JsonObject asJsonObject = json.getAsJsonArray("records").get(i).getAsJsonObject();
            leadId = asJsonObject.get("Id").getAsString();
            leadFirstName = asJsonObject.get("FirstName").getAsString();
            leadLastName = asJsonObject.get("LastName").getAsString();
            leadCompany = asJsonObject.get("Company").getAsString();
            System.out.println("Lead record is: " + i + ". " + leadId + " " + leadFirstName + " "
                + leadLastName + "(" + leadCompany + ")");
          }
        } catch (Exception je) {
          je.printStackTrace();
        }
      } else {
        System.out.println("Query was unsuccessful. Status code returned is " + statusCode);
        System.out.println(
            "An error has occured. Http status: " + response.getStatusLine().getStatusCode());
        System.out.println(getBody(response.getEntity().getContent()));
        System.exit(-1);
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    } catch (NullPointerException npe) {
      npe.printStackTrace();
    }
  }

  // Create Leads using REST HttpPost
  public static void createLeads() {
    System.out.println("\n_______________ Lead INSERT _______________");

    String uri = baseUri + "/sobjects/Lead/";
    try {

      // create the JSON object containing the new lead details.
      JsonObject lead = new JsonObject();
      lead.addProperty("FirstName", "REST API");
      lead.addProperty("LastName", "Lead");
      lead.addProperty("Company", "asagarwal.com");

      System.out.println("JSON for lead record to be inserted:\n");

      // Construct the objects needed for the request
      HttpClient httpClient = HttpClientBuilder.create().build();

      HttpPost httpPost = new HttpPost(uri);
      httpPost.addHeader(oauthHeader);
      httpPost.addHeader(prettyPrintHeader);
      // The message we are going to post
      StringEntity body = new StringEntity(lead.toString());
      body.setContentType("application/json");
      httpPost.setEntity(body);

      // Make the request
      HttpResponse response = httpClient.execute(httpPost);

      // Process the results
      int statusCode = response.getStatusLine().getStatusCode();
      if (statusCode == 201) {
        String response_string = EntityUtils.toString(response.getEntity());
        JsonObject json = new Gson().fromJson(response_string, JsonObject.class);
        // Store the retrieved lead id to use when we update the lead.
        leadId = json.get("id").getAsString();
        System.out.println("New Lead id from response: " + leadId);
      } else {
        System.out.println("Insertion unsuccessful. Status code returned is " + statusCode);
      }
    } catch (Exception e) {
      System.out.println("Issue creating JSON or processing results");
      e.printStackTrace();
    }
  }

  // Update Leads using REST HttpPatch. We have to create the HTTPPatch, as it does not exist in the
  // standard library
  // Since the PATCH method was only recently standardized and is not yet implemented in Apache
  // HttpClient
  public static void updateLeads() {
    System.out.println("\n_______________ Lead UPDATE _______________");

    // Notice, the id for the record to update is part of the URI, not part of the JSON
    String uri = baseUri + "/sobjects/Lead/" + leadId;
    try {
      // Create the JSON object containing the updated lead last name
      // and the id of the lead we are updating.
      JsonObject lead = new JsonObject();
      lead.addProperty("LastName", "Lead --UPDATED");
      System.out.println("JSON for update of lead record:\n" + lead.toString());

      // Set up the objects necessary to make the request.
      // DefaultHttpClient httpClient = new DefaultHttpClient();
      HttpClient httpClient = HttpClientBuilder.create().build();

      HttpPatch httpPatch = new HttpPatch(uri);
      httpPatch.addHeader(oauthHeader);
      httpPatch.addHeader(prettyPrintHeader);
      StringEntity body = new StringEntity(lead.toString());
      body.setContentType("application/json");
      httpPatch.setEntity(body);

      // Make the request
      HttpResponse response = httpClient.execute(httpPatch);

      // Process the response
      int statusCode = response.getStatusLine().getStatusCode();
      if (statusCode == 204) {
        System.out.println("Updated the lead successfully.");
      } else {
        System.out.println("Lead update NOT successfully. Status code is " + statusCode);
      }
    } catch (Exception e) {
      System.out.println("Issue creating JSON or processing results");
      e.printStackTrace();
    }
  }

  // Extend the Apache HttpPost method to implement an HttpPatch
  private static class HttpPatch extends HttpPost {
    public HttpPatch(String uri) {
      super(uri);
    }

    public String getMethod() {
      return "PATCH";
    }
  }

  // Update Leads using REST HttpDelete (We have to create the HTTPDelete, as it does not exist in
  // the standard library.)
  public static void deleteLeads() {
    System.out.println("\n_______________ Lead DELETE _______________");

    // Notice, the id for the record to update is part of the URI, not part of the JSON
    String uri = baseUri + "/sobjects/Lead/" + leadId;
    try {
      // Set up the objects necessary to make the request.
      HttpClient httpClient = HttpClientBuilder.create().build();

      HttpDelete httpDelete = new HttpDelete(uri);
      httpDelete.addHeader(oauthHeader);
      httpDelete.addHeader(prettyPrintHeader);

      // Make the request
      HttpResponse response = httpClient.execute(httpDelete);

      // Process the response
      int statusCode = response.getStatusLine().getStatusCode();
      if (statusCode == 204) {
        System.out.println("Deleted the lead successfully.");
      } else {
        System.out.println("Lead delete NOT successful. Status code is " + statusCode);
      }
    } catch (Exception e) {
      System.out.println("Issue creating JSON or processing results");
      e.printStackTrace();
    }
  }

  private static String getBody(InputStream inputStream) {
    String result = "";
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
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
