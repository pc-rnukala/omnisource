package com.omnisource.manager;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration
@Transactional(value = "omniTran")
public class MasterCardPaymentManagerTest {

	@Autowired
	private MasterCardPaymentManager masterCardPaymentManager;

	@Test
	public void testCreateTokenAndCustomer() {
		MasterCardRequest masterCardRequest = createMasterCardRequest();
		MasterCardResponse response = masterCardPaymentManager
				.createCardToken(masterCardRequest);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getTokenId());
		Assert.assertNotNull(response.getCustomerId());
		String tokenId = response.getTokenId();
		String customerId = response.getCustomerId();
		masterCardRequest.setTokenId(tokenId);
		masterCardRequest.setCustomerId(customerId);
		response = masterCardPaymentManager.findCardToken(masterCardRequest);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getTokenId());
		Assert.assertTrue("Token id not same",
				response.getTokenId().equals(tokenId));
		response = masterCardPaymentManager.findCustomer(masterCardRequest);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getCustomerId());
		Assert.assertTrue("Customer id not same", response.getCustomerId()
				.equals(customerId));

	}

	@Test
	public void testCreateTokenAndCustomerAndPayment() {
		MasterCardRequest masterCardRequest = createMasterCardRequest();
		MasterCardResponse response = masterCardPaymentManager
				.createCardToken(masterCardRequest);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getTokenId());
		Assert.assertNotNull(response.getCustomerId());
		masterCardRequest.setTokenId(response.getTokenId());
		masterCardRequest.setCustomerId(response.getCustomerId());
		masterCardRequest.setAmount(Double.valueOf(100));
		masterCardRequest.setPaymentDescription("Test description");
		masterCardRequest.setReferenceNumber("Testrefer");
		response = masterCardPaymentManager
				.createPayment(masterCardRequest);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getCardPaymentId());
		masterCardRequest.setPaymentId(response.getCardPaymentId());
		response = masterCardPaymentManager
				.findPayment(masterCardRequest);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getCardPaymentId());
	}

	@Test
	@Ignore
	public void testCreateTokenAndCustomerAmericanExpress() {
		MasterCardRequest masterCardRequest = createAmericanCardRequest();
		MasterCardResponse response = masterCardPaymentManager
				.createCardToken(masterCardRequest);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getTokenId());
		Assert.assertNotNull(response.getCustomerId());
		String tokenId = response.getTokenId();
		String customerId = response.getCustomerId();
		masterCardRequest.setTokenId(tokenId);
		masterCardRequest.setCustomerId(customerId);
		response = masterCardPaymentManager.findCardToken(masterCardRequest);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getTokenId());
		Assert.assertTrue("Token id not same",
				response.getTokenId().equals(tokenId));
		response = masterCardPaymentManager.findCustomer(masterCardRequest);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getCustomerId());
		Assert.assertTrue("Customer id not same", response.getCustomerId()
				.equals(customerId));

	}

	@Test
	public void testCreateTokenAndCustomerVisa() {
		MasterCardRequest masterCardRequest = createVisaCardRequest();
		MasterCardResponse response = masterCardPaymentManager
				.createCardToken(masterCardRequest);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getTokenId());
		Assert.assertNotNull(response.getCustomerId());
		String tokenId = response.getTokenId();
		String customerId = response.getCustomerId();
		masterCardRequest.setTokenId(tokenId);
		masterCardRequest.setCustomerId(customerId);
		response = masterCardPaymentManager.findCardToken(masterCardRequest);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getTokenId());
		Assert.assertTrue("Token id not same",
				response.getTokenId().equals(tokenId));
		response = masterCardPaymentManager.findCustomer(masterCardRequest);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getCustomerId());
		Assert.assertTrue("Customer id not same", response.getCustomerId()
				.equals(customerId));

	}

	private MasterCardRequest createMasterCardRequest() {
		MasterCardRequest request = new MasterCardRequest();
		request.setName("Jebastian Ponsekar");
		request.setAddressCity("Santa Clara");
		request.setAddressState("CA");
		request.setCardCvc("123");
		request.setCardNumber("5105105105105100");
		request.setCardExpYear(16);
		request.setCardExpMonth(12);
		request.setEmail("jebastian.ponsekar@personalcapital.com");
		return request;
	}

	private MasterCardRequest createVisaCardRequest() {
		MasterCardRequest request = new MasterCardRequest();
		request.setName("Jebastian Ponsekar");
		request.setAddressCity("Santa Clara");
		request.setAddressState("CA");
		request.setCardCvc("123");
		request.setCardNumber("4012888888881881");
		request.setCardExpYear(16);
		request.setCardExpMonth(12);
		request.setEmail("jebastian.ponsekar@personalcapital.com");
		return request;
	}

	private MasterCardRequest createAmericanCardRequest() {
		MasterCardRequest request = new MasterCardRequest();
		request.setName("Jebastian Ponsekar");
		request.setAddressCity("Santa Clara");
		request.setAddressState("CA");
		request.setCardCvc("123");
		request.setCardNumber("371449635398431");
		request.setCardExpYear(16);
		request.setCardExpMonth(12);
		request.setEmail("jebastian.ponsekar@personalcapital.com");
		return request;
	}
}
