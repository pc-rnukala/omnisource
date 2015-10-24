package com.omnisource.shopstyle.delegate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.omnisource.shopstyle.request.SearchProductRequest;

public class ShopStyleDelegate {

	public static Logger logger = LoggerFactory
			.getLogger(ShopStyleDelegate.class);
	public static final String SHOP_STYLE_API_KEY = "uid6804-31656702-89";
	public static final String BASE_SHOPSTYLE_API = "http://api.shopstyle.com/api/v2/";

	public JsonObject searchProducts(SearchProductRequest searchProductRequest) {

		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;
		String line = "";
		JsonObject responseData = null;
		StringBuilder responseString = new StringBuilder();
		try {
			// get the http client
			client = HttpClients.createDefault();
			// construct modo api
			String apiRequest = BASE_SHOPSTYLE_API + "products?pid="
					+ SHOP_STYLE_API_KEY;
			HttpGet post = new HttpGet(apiRequest);
			// Execute the modo post
			response = client.execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			/*
			 * Read the line by line response from modo
			 */
			while ((line = rd.readLine()) != null) {
				responseString.append(line);
			}
			/*
			 * Parse the api response to fetch the access token
			 */
			JsonParser parser = new JsonParser();
			JsonObject o = (JsonObject) parser.parse(responseString.toString());
			responseData = o;
		} catch (IOException e) {
			logger.error(
					"Exception while fetching the getToken: " + e.getMessage(),
					e);
		} catch (Exception e) {
			logger.error("Exception while processing addCard Request: "
					+ e.getMessage());
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (Exception e) {
					logger.error(
							"Exception while closing the response: "
									+ e.getMessage(), e);
				}
			}

			if (client != null) {
				try {
					client.close();
				} catch (Exception e) {
					logger.error("Exception while closing the http Client: "
							+ e.getMessage(), e);
				}
			}

		}
		return responseData;
	}
}
