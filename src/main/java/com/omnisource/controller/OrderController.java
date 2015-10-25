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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.omnisource.dao.OrderDao;
import com.omnisource.dao.UserDao;
import com.omnisource.data.Order;
import com.omnisource.data.User;
import com.omnisource.model.OmniSourceJsonModel;

@Controller
public class OrderController {
	private static final Logger logger = LoggerFactory
			.getLogger(OrderController.class);

	@Autowired
	UserDao userDao;

	@Autowired
	OrderDao orderDao;

	@RequestMapping(value = APIRequestMappings.GET_ORDER, method = {
			RequestMethod.POST, RequestMethod.GET }, produces = "application/json")
	public @ResponseBody
	OmniSourceJsonModel getCards(
			@RequestParam(value = "customerGuid", required = true) String customerGuid) {
		OmniSourceJsonModel model = new OmniSourceJsonModel();
		User user = userDao.getUserByGuid(customerGuid);
		Map<String, Object> header = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		model.setSpData(data);
		model.setSpHeader(header);
		boolean status = false;
		try {
			List<String> errorList = new ArrayList<String>();
			if (user != null) {
				List<Order> orders = orderDao.getUserOrders(user);
				status = true;
				data.put("orders", orders != null ? jsonizeOrders(orders)
						: new ArrayList<>());
				logger.info("Orders Size is: " + orders);
			} else {
				errorList.add("User doesn't exist for customerGuid: "
						+ customerGuid);
				status = false;
				data.put("orders", new ArrayList<>());
			}
			header.put("status", status);
			header.put("errorList", errorList);
		} catch (Exception e) {
			logger.error(
					"Exception while fetching the Cards: " + e.getMessage(), e);
		}
		return model;
	}

	private StringResponse jsonizeOrders(List<Order> orders) {
		JsonArray orderArrayJson = new JsonArray();
		if (orders == null || orders.isEmpty()) {
			return new StringResponse(orderArrayJson.toString());
		}
		for (Order order : orders) {
			JsonObject orderJson = new JsonObject();
			orderJson.addProperty("orderId", order.getId());
			orderJson.addProperty("cardId", order.getCardId());
			orderJson.addProperty("paymentId", order.getExternalPaymentId());
			String details = order.getDetails();
			JsonParser parser = new JsonParser();
			JsonObject o = (JsonObject) parser.parse(details);
			orderJson.addProperty("productId", o.get("productId") != null ? o
					.get("productId").getAsNumber().toString() : "");
			orderJson.addProperty("productName",
					o.get("productName") != null ? o.get("productName")
							.getAsString() : "");
			orderJson.addProperty(
					"productDescription",
					o.get("productDescription") != null ? o.get(
							"productDescription").getAsString() : "");
			orderJson.addProperty("productType",
					o.get("productType") != null ? o.get("productType")
							.getAsString() : "");
			orderJson.addProperty("amount",
					o.get("amount") != null ? o.get("amount").getAsString()
							: "");
			orderArrayJson.add(orderJson);
		}
		return new StringResponse(orderArrayJson.toString());
	}
}
