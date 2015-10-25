package com.omnisource.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.omnisource.model.OmniSourceJsonModel;
import com.omnisource.shopstyle.delegate.ShopStyleDelegate;
import com.omnisource.shopstyle.request.SearchProductRequest;

@Controller
public class ProductController {
	private static final Logger logger = LoggerFactory
			.getLogger(ProductController.class);

	@Autowired
	ShopStyleDelegate shopStyleDelegate;

	@RequestMapping(value = APIRequestMappings.SEARCH_PRODUCT, method = {
			RequestMethod.POST, RequestMethod.GET }, produces = "application/json")
	public @ResponseBody
	OmniSourceJsonModel searchProducts(
			@RequestParam(value = "fts", required = true) String fts,
			@RequestParam(value = "fl", required = false) String[] fl) {
		OmniSourceJsonModel model = new OmniSourceJsonModel();
		SearchProductRequest searchProductRequest = new SearchProductRequest();
		searchProductRequest.setFts(fts);
		searchProductRequest.setFilters(fl);

		JsonArray searchResults = shopStyleDelegate
				.searchProducts(searchProductRequest);
		String productResults = searchResults != null ? searchResults
				.toString() : "";
		Map<String, Object> header = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		model.setSpData(data);
		model.setSpHeader(header);
		header.put("status", true);
		data.put("products", new StringResponse(productResults));
		List<String> errorList = new ArrayList<String>();
		header.put("errorList", errorList);
		return model;
	}
}
