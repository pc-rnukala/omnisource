package com.omnisource.manager;

import java.util.UUID;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonObject;
import com.omnisource.dao.CardAccountDao;
import com.omnisource.dao.OrderDao;
import com.omnisource.dao.UserDao;
import com.omnisource.data.CardAccount;
import com.omnisource.data.CardAccountImpl;
import com.omnisource.data.Order;
import com.omnisource.data.OrderImpl;
import com.omnisource.data.User;
import com.omnisource.data.UserImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration
@Transactional(value = "omniTran")
public class SampleDataCreationManagerTest {
	@Autowired
	private UserDao userDao;
	@Autowired
	private CardAccountDao cardAccountDao;

	@Autowired
	private MasterCardPaymentManager masterCardPaymentManager;

	@Autowired
	private OrderDao orderDao;

	String userName = "Jebastian Ponsekar";
	String email = "jebastian.ponsekar@personalcapital.com";

	private User createUser() {
		User user = new UserImpl();
		user.setCreatedDate(new DateTime().toDate());
		user.setMastercardAccountId(UUID.randomUUID().toString());
		user.setPassword("password");
		user.setPhoneNumber("2348479489");
		user.setUpdatedDate(new DateTime().toDate());
		user.setUserGuid(email);
		user.setUserName(email);
		User createdUser = userDao.saveOrUpdate(user);
		return createdUser;
	}

	public CardAccount createCardAccount(User user) {
		CardAccount cardAccount = new CardAccountImpl();
		cardAccount.setCreatedDate(new DateTime().toDate());
		cardAccount.setCardType("MASTER");
		cardAccount.setUserId(user.getId());
		return cardAccount;
	}

	public Order createOrder(User user, CardAccount cardAccount) {
		Order order = new OrderImpl();
		order.setUserId(user.getId());
		order.setCardId(cardAccount.getId());
		order.setAddressDetails("2555 homestead rd ,Santa Clara,CA 95051");
		order.setCreatedDate(new DateTime().toDate());
		return order;
	}

	private MasterCardRequest createMasterCardRequest() {
		MasterCardRequest request = new MasterCardRequest();
		request.setName(userName);
		request.setAddressCity("Santa Clara");
		request.setAddressState("CA");
		request.setCardCvc("123");
		request.setCardNumber("5105105105105100");
		request.setCardExpYear(16);
		request.setCardExpMonth(12);
		request.setEmail(email);
		return request;
	}

	private MasterCardRequest createMasterCardRequest2() {

		MasterCardRequest request = new MasterCardRequest();
		request.setName(userName);
		request.setAddressCity("Santa Clara");
		request.setAddressState("CA");
		request.setCardCvc("123");
		request.setCardNumber("5185540810000019");
		request.setCardExpYear(16);
		request.setCardExpMonth(12);
		request.setEmail(email);
		return request;
	}

	// @Test
	// @Rollback(false)
	public void addCardAccount() {
		User user = this.createUser();
		user = userDao.saveOrUpdate(user);

		// creating master card 1
		MasterCardRequest masterCardRequest = this.createMasterCardRequest();
		MasterCardResponse response = masterCardPaymentManager
				.createCardToken(masterCardRequest);
		CardAccount cardAccount = this.createCardAccount(user);
		cardAccount.setName("FreedomCard");
		cardAccount.setDescription(cardAccount.getName());
		cardAccount.setTokenId(response.getTokenId());
		cardAccount.setCustomerId(response.getCustomerId());
		cardAccountDao.saveOrUpdate(cardAccount);

		// creating master card 2
		masterCardRequest = this.createMasterCardRequest2();
		response = masterCardPaymentManager.createCardToken(masterCardRequest);
		CardAccount cardAccount1 = this.createCardAccount(user);
		cardAccount1.setName("TravelCard");
		cardAccount1.setDescription(cardAccount1.getName());
		cardAccount1.setTokenId(response.getTokenId());
		cardAccount1.setCustomerId(response.getCustomerId());
		cardAccountDao.saveOrUpdate(cardAccount1);
	}

	@Test
	@Rollback(false)
	public void addCardAccountAndOrder() {
		User user = this.createUser();
		user = userDao.saveOrUpdate(user);

		// creating master card 1
		MasterCardRequest masterCardRequest = this.createMasterCardRequest();
		MasterCardResponse response = masterCardPaymentManager
				.createCardToken(masterCardRequest);
		CardAccount cardAccount = this.createCardAccount(user);
		cardAccount.setName("FreedomCard");
		cardAccount.setDescription(cardAccount.getName());
		cardAccount.setTokenId(response.getTokenId());
		cardAccount.setCustomerId(response.getCustomerId());
		cardAccount = cardAccountDao.saveOrUpdate(cardAccount);

		// creating order1
		Order order = createOrder(user, cardAccount);
		JsonObject productDetails = new JsonObject();
		productDetails.addProperty("productId", "P1");
		productDetails.addProperty("productName", "Nike");
		productDetails.addProperty("productDescription", "Nike shoes");
		productDetails.addProperty("productType", "Shoes");
		order.setDetails(productDetails.toString());
		order = orderDao.saveOrUpdate(order);
		masterCardRequest.setReferenceNumber(String.valueOf(order.getId()));
		masterCardRequest.setAmount(Double.valueOf(100));
		masterCardRequest.setPaymentDescription("PaymentDescription");
		masterCardRequest.setCustomerId(response.getCustomerId());
		response = masterCardPaymentManager.createPayment(masterCardRequest);
		order.setExternalInvoiceId(response.getInvoiceId());
		order.setExternalPaymentId(response.getCardPaymentId());
		order = orderDao.saveOrUpdate(order);

		// creating master card 2
		masterCardRequest = this.createMasterCardRequest2();
		response = masterCardPaymentManager.createCardToken(masterCardRequest);
		CardAccount cardAccount1 = this.createCardAccount(user);
		cardAccount1.setName("TravelCard");
		cardAccount1.setDescription(cardAccount.getName());
		cardAccount1.setTokenId(response.getTokenId());
		cardAccount1.setCustomerId(response.getCustomerId());
		cardAccount1 = cardAccountDao.saveOrUpdate(cardAccount1);

		// creating order2
		Order order1 = createOrder(user, cardAccount1);
		JsonObject productDetails1 = new JsonObject();
		productDetails1.addProperty("productId", "P2");
		productDetails1.addProperty("productName", "Sketchers");
		productDetails1.addProperty("productDescription", "Sketchers shoes");
		productDetails1.addProperty("productType", "Shoes");
		order1.setDetails(productDetails1.toString());
		order1 = orderDao.saveOrUpdate(order1);
		masterCardRequest.setReferenceNumber(String.valueOf(order1.getId()));
		masterCardRequest.setAmount(Double.valueOf(50));
		masterCardRequest.setPaymentDescription("PaymentDescription");
		masterCardRequest.setCustomerId(response.getCustomerId());
		response = masterCardPaymentManager.createPayment(masterCardRequest);
		order1.setExternalInvoiceId(response.getInvoiceId());
		order1.setExternalPaymentId(response.getCardPaymentId());
		order1 = orderDao.saveOrUpdate(order1);

	}
}
