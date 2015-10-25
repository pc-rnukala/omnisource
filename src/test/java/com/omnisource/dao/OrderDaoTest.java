package com.omnisource.dao;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.omnisource.data.CardAccount;
import com.omnisource.data.CardAccountImpl;
import com.omnisource.data.Order;
import com.omnisource.data.OrderImpl;
import com.omnisource.data.User;
import com.omnisource.data.UserImpl;
import com.omnisource.utils.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration
@Transactional(value = "omniTran")
public class OrderDaoTest {
	@Autowired
	private UserDao userDao;
	@Autowired
	private CardAccountDao cardAccountDao;

	@Autowired
	private OrderDao orderDao;

	// @Test
	public void testGetOrder() {
		User user = createUser();
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getId());
		CardAccount cardAccount = createCardAccount(user);
		Assert.assertNotNull(cardAccount);
		Assert.assertNotNull(cardAccount.getId());

		for (int i = 0; i < 10; i++) {
			Order order = createOrder(user, cardAccount);
			Assert.assertNotNull(order);
			Assert.assertNotNull(order.getId());
		}
		List<Order> orders = orderDao.getUserOrders(user);
		Assert.assertNotNull(orders);
	}

	public User createUser() {
		User user = new UserImpl();
		user.setCreatedDate(new DateTime().toDate());
		user.setMastercardAccountId("masteraccountid");
		user.setPassword("testPassword");
		user.setPhoneNumber("2348479489");
		user.setUpdatedDate(new DateTime().toDate());
		user.setUserGuid("3432uerwerw");
		user.setUserName("testUserId@personalcapital.com");
		User createdUser = userDao.saveOrUpdate(user);
		return createdUser;
	}

	public CardAccount createCardAccount(User user) {
		CardAccount cardAccount = new CardAccountImpl();
		cardAccount.setCreatedDate(new DateTime().toDate());
		cardAccount.setCardType("master");
		cardAccount.setUserId(user.getId());
		cardAccount.setCustomerId("testid");
		cardAccount.setTokenId("testtokenid");
		cardAccount.setDescription("test description");
		cardAccount = cardAccountDao.saveOrUpdate(cardAccount);
		return cardAccount;
	}

	public Order createOrder(User user, CardAccount cardAccount) {
		Order order = new OrderImpl();
		order.setUserId(user.getId());
		order.setCardId(cardAccount.getId());
		order.setExternalInvoiceId("Test invoice id");
		order.setExternalPaymentId(Utils.generateGuid());
		order.setLocationId("Test location");
		order.setMerchantId("merchant id");
		order.setAddressDetails("Testaddress");
		order = orderDao.saveOrUpdate(order);
		return order;
	}

	@Test
	public void testCreateOrder() {
		User createdUser = createUser();
		Assert.assertNotNull(createdUser);
		Assert.assertNotNull(createdUser.getId());
		CardAccount cardAccount = createCardAccount(createdUser);
		Assert.assertNotNull(cardAccount);
		Assert.assertNotNull(cardAccount.getId());
		Order order = createOrder(createdUser, cardAccount);
		Assert.assertNotNull(order);
		Assert.assertNotNull(order.getId());
	}

}
