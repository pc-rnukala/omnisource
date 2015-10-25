package com.omnisource.dao;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.omnisource.data.CardAccount;
import com.omnisource.data.CardAccountImpl;
import com.omnisource.data.User;
import com.omnisource.data.UserImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration(defaultRollback = false)
@Transactional(value = "omniTran")
public class CardAccountDaoTest {
	@Autowired
	private UserDao userDao;
	@Autowired
	private CardAccountDao cardAccountDao;

	@Test
	public void testGetCardAccount() {
		User user = createUser();
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getId());
		for (int i = 0; i < 10; i++) {
			CardAccount cardAccount = createCardAccount(user);
			Assert.assertNotNull(cardAccount);
			Assert.assertNotNull(cardAccount.getId());
		}
		List<CardAccount> cardAccounts = cardAccountDao
				.getUserCardAccounts(user);
		Assert.assertNotNull(cardAccounts);

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

	@Test
	public void testCreateCardAccount() {
		User createdUser = createUser();
		Assert.assertNotNull(createdUser);
		Assert.assertNotNull(createdUser.getId());
		CardAccount cardAccount = createCardAccount(createdUser);
		Assert.assertNotNull(cardAccount);
		Assert.assertNotNull(cardAccount.getId());
	}

}
