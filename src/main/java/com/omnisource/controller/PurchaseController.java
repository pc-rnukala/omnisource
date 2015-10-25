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

import com.google.gson.JsonObject;
import com.omnisource.dao.CardAccountDao;
import com.omnisource.dao.OrderDao;
import com.omnisource.dao.UserDao;
import com.omnisource.data.CardAccount;
import com.omnisource.data.Order;
import com.omnisource.data.OrderImpl;
import com.omnisource.data.User;
import com.omnisource.manager.MasterCardPaymentManager;
import com.omnisource.manager.MasterCardRequest;
import com.omnisource.manager.MasterCardResponse;
import com.omnisource.model.OmniSourceJsonModel;
import com.omnisource.utils.Utils;

@Controller
public class PurchaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(PurchaseController.class);

	@Autowired
	CardAccountDao cardAccountDao;

	@Autowired
	UserDao userDao;

	@Autowired
	OrderDao orderDao;

	@Autowired
	MasterCardPaymentManager masterCardPaymentManager;

	@RequestMapping(value = APIRequestMappings.PURCHASE_PRODUCT, method = {
			RequestMethod.POST, RequestMethod.GET }, produces = "application/json")
	public @ResponseBody
	OmniSourceJsonModel purchaseProduct(
			@RequestParam(value = "customerGuid", required = true) String customerGuid,
			@RequestParam(value = "cardId", required = true) String cardId,
			@RequestParam(value = "productId", required = true) String productId,
			@RequestParam(value = "productName", required = true) String productName,
			@RequestParam(value = "productDescription", required = true) String productDescription,
			@RequestParam(value = "productType", required = true) String productType,
			@RequestParam(value = "amount", required = true) String amount) {
		OmniSourceJsonModel model = new OmniSourceJsonModel();
		User user = userDao.getUserByGuid(customerGuid);
		Map<String, Object> header = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		model.setSpData(data);
		model.setSpHeader(header);
		boolean status = false;
		try {
			List<String> errorList = new ArrayList<String>();
			// Check If the user Is valid
			if (user != null) {
				CardAccount cardAccount = cardAccountDao.getUserCardAccount(
						user, cardId);
				if (cardAccount != null) {
					MasterCardRequest masterCardRequest = new MasterCardRequest();
					masterCardRequest.setPaymentDescription(productDescription);
					masterCardRequest.setAmount(Double.valueOf(amount) * 100);
					masterCardRequest.setReferenceNumber(Utils.generateGuid());
					masterCardRequest
							.setCustomerId(cardAccount.getCustomerId());
					/*
					 * Create Payment
					 */
					MasterCardResponse masterCardResponse = masterCardPaymentManager
							.createPayment(masterCardRequest);
					// If the masterCardResponse is successfull proceed further
					if (masterCardResponse != null) {
						Order order = new OrderImpl();
						order.setUserId(user.getId());
						order.setCardId(Long.valueOf(cardId));
						order.setExternalPaymentId(masterCardResponse
								.getCardPaymentId());
						JsonObject productDetails = new JsonObject();
						productDetails.addProperty("productId", productId);
						productDetails.addProperty("productName", productName);
						productDetails.addProperty("productDescription",
								productDescription);
						productDetails.addProperty("productType", productType);
						productDetails.addProperty("amount",
								masterCardRequest.getAmount());
						order.setDetails(productDetails.toString());
						Order createdOrder = orderDao.saveOrUpdate(order);
						if (createdOrder != null) {
							status = true;
							data.put("confirmationId",
									masterCardResponse.getCardPaymentId());
							data.put("orderId", createdOrder.getId());
						} else {
							errorList
									.add("Order Creation failed for the cardId: "
											+ cardId
											+ " and customerGuid: "
											+ customerGuid);
							status = false;
							data.put("confirmationId", "");
							data.put("orderId", "");

						}
					} else {
						errorList
								.add("Payment Response Returned is null for the cardId: "
										+ cardId
										+ " and customerGuid: "
										+ customerGuid);
						status = false;
						data.put("confirmationId", "");
						data.put("orderId", "");
					}

				} else {
					// Invalid Card On File
					errorList
							.add("CardId and CustomerGuid combination is invalid: for the cardId: "
									+ cardId
									+ " and customerGuid: "
									+ customerGuid);
					status = false;
					data.put("confirmationId", "");
					data.put("orderId", "");
				}
			} else {
				errorList.add("User doesn't exist for customerGuid: "
						+ customerGuid);
				status = false;
				data.put("confirmationId", "");
				data.put("orderId", "");
			}
			header.put("customerGuid", customerGuid);
			header.put("status", status);
			header.put("errorList", errorList);
		} catch (Exception e) {
			logger.error("Exception while purchaseProduct: " + e.getMessage(),
					e);
		}
		return model;
	}
}
