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

import com.omnisource.dao.CardAccountDao;
import com.omnisource.dao.UserDao;
import com.omnisource.data.CardAccount;
import com.omnisource.data.User;
import com.omnisource.model.OmniSourceJsonModel;

@Controller
public class CardController {
	private static final Logger logger = LoggerFactory
			.getLogger(CardController.class);

	@Autowired
	CardAccountDao cardAccountDao;

	@Autowired
	UserDao userDao;

	@RequestMapping(value = APIRequestMappings.GET_CARDS, method = {
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
				List<CardAccount> cardAccounts = cardAccountDao
						.getUserCardAccounts(user);
				status = true;
				data.put("cards", cardAccounts != null ? cardAccounts
						: new ArrayList<>());
				logger.info("Card Size is: " + cardAccounts);
			} else {
				errorList.add("User doesn't exist for customerGuid: "
						+ customerGuid);
				status = false;
				data.put("cards", new ArrayList<>());
			}
			header.put("status", status);
			header.put("errorList", errorList);
		} catch (Exception e) {
			logger.error(
					"Exception while fetching the Cards: " + e.getMessage(), e);
		}
		return model;
	}
}
