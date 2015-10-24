package com.omnisource.shopstyle.delegate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ShopStyleDelegate {

	public static Logger logger = LoggerFactory
			.getLogger(ShopStyleDelegate.class);
	public static final String SHOP_STYLE_API_KEY = "uid6804-31656702-89";
	public static final String BASE_SHOPSTYLE_API = "http://api.shopstyle.com/api/v2/";

	public JsonObject getOffers(OfferRequest offerRequest) {

		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;
		String line = "";
		JsonObject responseData = null;
		StringBuilder responseString = new StringBuilder();
		try {
			// get the http client
			client = HttpClients.createDefault();
			String accessToken = getModoToken();
			// construct modo api
			HttpPost post = new HttpPost(BASE_SHOPSTYLE_API + OFFER_LOOKUP_API);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			nameValuePairs.add(new BasicNameValuePair(CONSUMER_KEY,
					CONSUMER_KEY_VALUE));
			nameValuePairs
					.add(new BasicNameValuePair(ACCESS_TOKEN, accessToken));
			nameValuePairs.add(new BasicNameValuePair(OfferRequest.ACCOUNT_ID,
					offerRequest.getAccountId()));
			nameValuePairs.add(new BasicNameValuePair(OfferRequest.MERCHANT_ID,
					offerRequest.getMerchantId()));
			nameValuePairs.add(new BasicNameValuePair(OfferRequest.LOCATION_ID,
					offerRequest.getLocationId()));
			nameValuePairs.add(new BasicNameValuePair(
					OfferRequest.STATUS_FILTER, offerRequest.getStatusFilter()
							.toString()));
			nameValuePairs.add(new BasicNameValuePair(
					OfferRequest.GIVER_FILTER, offerRequest.getGiverFilter()
							.toString()));
			nameValuePairs.add(new BasicNameValuePair(
					OfferRequest.SHOULD_INCLUDE_GIVEN, String
							.valueOf(offerRequest.getShouldIncludeGiven())));
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
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
			String statusCode = o.get("status_code") != null ? o.get(
					"status_code").getAsString() : null;
			logger.info("Status Code: " + statusCode);
			if (statusCode.equals("0") == false) {
				throw new Exception();
			}
			responseData = o.getAsJsonObject(RESPONSE_DATA);
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
