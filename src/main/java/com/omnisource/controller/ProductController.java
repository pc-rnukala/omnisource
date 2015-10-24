package com.omnisource.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.omnisource.model.OmniSourceJsonModel;

@Controller
public class ProductController {
	private static final Logger logger = LoggerFactory
			.getLogger(ProductController.class);

	@RequestMapping(value = APIRequestMappings.SEARCH_PRODUCT, method = RequestMethod.POST)
	public @ResponseBody
	OmniSourceJsonModel searchProducts(
			@RequestParam(value = "fts", required = true) String fts,
			@RequestParam(value = "fl", required = false) String fl,
			@RequestParam(value = "cat", required = false) String category) {
		OmniSourceJsonModel model = new OmniSourceJsonModel();
		
		Map<String, Object> header = new HashMap<String, Object>();
		model.setSpHeader(header);
		header.put("status", false);
		Map<String, Object> data = new HashMap<String, Object>();
		model.setSpData(data);
		List<String> errorList = new ArrayList<String>();

		header.put("errorList", errorList);
		return model;
	}
}
