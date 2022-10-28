package com.ops.easyrequest.app;

/**
 *
 *	@author Saswata Mukhopadhyay
 *
 */

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.apache.commons.codec.binary.Base64;


public class OperationHandler {

	public static final String POST_REQUEST = "POST";
	public static final String GET_REQUEST = "GET";
	public static final String DELETE_REQUEST = "DELETE";
	public static final String PATCH_REQUEST = "PATCH";
	public static final String OPTIONS_REQUEST = "OPTIONS";

	public static final String CONTENT_TYPE_FORM_ENCODED = "application/x-www-form-urlencoded";
	public static final String CONTENT_TYPE_FORM = "multipart/form-data";
	public static final String CONTENT_TYPE_TEXT_HTML = "text/html";
	public static final String CONTENT_TYPE_TEXT_PLAIN = "text/plain";
	public static final String CONTENT_TYPE_JSON = "application/json";
	public static final String CONTENT_TYPE_IMAGE = "image/jpeg";
	public static final String CONTENT_TYPE_STREAM = "application/xml";
	public static final String CONTENT_TYPE_BYTES = "application/octet-stream";


	//Encrypt String into Base64
	static String encryptStringBase64(String stringToEncrypt) {
		String encryptedString = "";
		encryptedString = new String(Base64.encodeBase64(stringToEncrypt.getBytes(StandardCharsets.UTF_8)),StandardCharsets.UTF_8);
		return encryptedString;
	}

	//Decrypt String into Base64
	static String decryptStringBase64(String stringTodecrypt) {
		String decryptedString = "";
		decryptedString = new String(Base64.decodeBase64(stringTodecrypt.getBytes(StandardCharsets.UTF_8)),StandardCharsets.UTF_8);
		return decryptedString;
	}

	//Encrypt JSON into Base64 String
	static String encryptJSONObjectBase64(JSONObject jsonToEncrypt) {
		String jsonString = jsonToEncrypt.toString();
		String encryptedString = "";
		encryptedString = new String(Base64.encodeBase64(jsonString.getBytes(StandardCharsets.UTF_8)),StandardCharsets.UTF_8);
		return encryptedString;
	}

	//Decrypt JSON into Base64 String
	JSONObject decryptJSONObjectBase64(String jsonTodecrypt) {
		JSONObject decryptedJSON = null;
		String decryptedString = "";
		decryptedString = new String(Base64.decodeBase64(jsonTodecrypt.getBytes(StandardCharsets.UTF_8)),StandardCharsets.UTF_8);
		return decryptedJSON;
	}

	//To make POST request with form-data in body
	static Map<String,String> postRequest(String endpoint, Map<Object, Object> requestData, Map<Object, Object> headerData, String requestCategory, JSONObject jsonData, String xmlData, String textData) { //requestData can be query params or even body
		Map<String,String> mapToReturn = new HashMap<>();
		if(endpoint == null || endpoint.isEmpty()) {
			mapToReturn.put("Error", "No endpoint provided");
			return mapToReturn;
		}

		try {
			final HttpClient client = HttpClient.newBuilder().build(); //newHttpClient();
			if(requestCategory.equalsIgnoreCase("query")) {
				HttpRequest request = HttpRequest.newBuilder()
						.POST(buildFormDataFromMap(requestData))
						.uri(URI.create(endpoint))
						.setHeader("User-Agent", "Saswata Testing")
		                .header("Content-Type", "application/x-www-form-urlencoded")
		                .header("Cache-Control", "no-cache")
						.build();
				HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
				mapToReturn.put("https_status_code", Integer.toString(response.statusCode()));
				mapToReturn.put("Response", response.body());
			}
			else if(requestCategory.equalsIgnoreCase("json")) {

			}
			else if(requestCategory.equalsIgnoreCase("xml")) {

			}
			else if(requestCategory.equalsIgnoreCase("text")) {

			}
			else if(requestCategory.equalsIgnoreCase("form")) {

			}
			else if(requestCategory.equalsIgnoreCase("formencode")) {

			}

		}
		catch (Exception e) {
			System.out.println(e);
		}

		return mapToReturn;
	}

	//To make GET request with form-data in body
	static Map<String,String> getRequest(String endpoint) {
		Map<String,String> mapToReturn = new HashMap<>();
		if(endpoint == null || endpoint.isEmpty()) {
			mapToReturn.put("Error", "No endpoint provided");
			return mapToReturn;
		}

		try {
			final HttpClient client = HttpClient.newBuilder().build(); //newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(endpoint))
					.build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			mapToReturn.put("https_status_code", Integer.toString(response.statusCode()));
			mapToReturn.put("Response", response.body());
			mapToReturn.put("Headers", response.headers().toString());

//			System.out.println("HTTPS Status Code: "+response.statusCode());
//			System.out.println("Response: "+response.body());
//			System.out.println("Size: to be calculated");
		}
		catch (Exception e) {
			System.out.println(e);
		}

		return mapToReturn;
	}

	//To make DELETE request.
	static Map<String,String> deleteRequest(String endpoint) {
		Map<String,String> mapToReturn = new HashMap<>();
		if(endpoint == null || endpoint.isEmpty()) {
			mapToReturn.put("Error", "No endpoint provided");
			return mapToReturn;
		}

		try {
			final HttpClient client = HttpClient.newBuilder().build(); //newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.DELETE()
					.uri(URI.create(endpoint))
					.build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			mapToReturn.put("https_status_code", Integer.toString(response.statusCode()));
			mapToReturn.put("Response", response.body());
			mapToReturn.put("Headers", response.headers().toString());
		}
		catch (Exception e) {
			System.out.println(e);
		}

		return mapToReturn;
	}

	//To make PUT request with form-data in body
	static Map<String,String> putRequest(String endpoint, Map<Object, Object> requestData, Map<Object, Object> headerData, String requestCategory, JSONObject jsonData, String xmlData, String textData) { //requestData can be query params or even body
		Map<String,String> mapToReturn = new HashMap<>();
		if(endpoint == null || endpoint.isEmpty()) {
			mapToReturn.put("Error", "No endpoint provided");
			return mapToReturn;
		}

		try {
			final HttpClient client = HttpClient.newBuilder().build(); //newHttpClient();
			if(requestCategory.equalsIgnoreCase("query")) {
				HttpRequest request = HttpRequest.newBuilder()
						.PUT(buildFormDataFromMap(requestData))
						.uri(URI.create(endpoint))
						.setHeader("User-Agent", "Saswata Testing")
		                .header("Content-Type", "application/x-www-form-urlencoded")
		                .header("Cache-Control", "no-cache")
						.build();
				HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
				mapToReturn.put("https_status_code", Integer.toString(response.statusCode()));
				mapToReturn.put("Response", response.body());
			}
			else if(requestCategory.equalsIgnoreCase("json")) {

			}
			else if(requestCategory.equalsIgnoreCase("xml")) {

			}
			else if(requestCategory.equalsIgnoreCase("text")) {

			}
			else if(requestCategory.equalsIgnoreCase("form")) {

			}
			else if(requestCategory.equalsIgnoreCase("formencode")) {

			}

		}
		catch (Exception e) {
			System.out.println(e);
		}

		return mapToReturn;
	}

	//To make OPTIONS request.
	static Map<String,String> optionsRequest(String endpoint) {
		Map<String,String> mapToReturn = new HashMap<>();
		if(endpoint == null || endpoint.isEmpty()) {
			mapToReturn.put("Error", "No endpoint provided");
			return mapToReturn;
		}

		try {
			final HttpClient client = HttpClient.newBuilder().build(); //newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.OPTIONS()
					.uri(URI.create(endpoint))
					.build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			mapToReturn.put("https_status_code", Integer.toString(response.statusCode()));
			mapToReturn.put("Response", response.body());
			mapToReturn.put("Headers", response.headers().toString());
		}
		catch (Exception e) {
			System.out.println(e);
		}

		return mapToReturn;
	}

//	static boolean requestOperation(String path, int userId, int orgId, String contentType, String requestType, String endpoint) throws IOException{
//		if (path == null || path.isEmpty()) {
//			return false;
//		}
////		String endpoint = "https://qa.tcsion.com/HandsONExecutor/Execute";
//		String jsonString ="";
//		JSONObject jsonObject = null;
//		String transactionId = "";
//		ObjectMapper mapper = new ObjectMapper();
//		Map<String,String>map = new HashMap<>();
//
//		try {
//			if(requestType.equalsIgnoreCase(POST_REQUEST)) {
//				map = mapper.readValue(Paths.get(path).toFile(), Map.class);
//				jsonObject = new JSONObject(map);
//				transactionId = jsonObject.getString("transactionId");
//				String jsonStringEncoded = encryptJSONObjectBase64(jsonObject);
//				System.out.println(jsonStringEncoded);
//			}
//
//			//Creating form body data
//			Map<Object, Object> requestBodyData = new HashMap<>();
//			if (contentType.equalsIgnoreCase("application/x-www-form-urlencoded")) {
////				requestBodyData.put("requestJSON", jsonStringEncoded);
//				requestBodyData.put("orgId", orgId);
//				requestBodyData.put("userId", userId);
//				requestBodyData.put("transactionId", transactionId);
//			}
//
//			//Directing to method based on requestType
//			if(requestType.equalsIgnoreCase(POST_REQUEST))
//				sendRequest(endpoint,requestBodyData);
//			else if(requestType.equalsIgnoreCase(GET_REQUEST))
//				getRequest(endpoint);
//			else if(requestType.equalsIgnoreCase(DELETE_REQUEST))
//				sendRequest(endpoint,requestBodyData);
//			else if(requestType.equalsIgnoreCase(GET_REQUEST))
//				sendRequest(endpoint,requestBodyData);
//
//		}
//		catch (Exception e) {
//			System.out.println(e);
//		}
//		return true;
//	}

	//To build form data from a map. Useful to send data as form body and not as params
	static HttpRequest.BodyPublisher buildFormDataFromMap(Map<Object, Object> data) {
        var builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }

	static void test() {
        System.out.println("working");
    }

}
