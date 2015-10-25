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
		response = masterCardPaymentManager.createPayment(masterCardRequest);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getCardPaymentId());
		masterCardRequest.setPaymentId(response.getCardPaymentId());
		response = masterCardPaymentManager.findPayment(masterCardRequest);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getCardPaymentId());
	}

	@Test
	public void testCreateTokenAndCustomerAndInvoice() {
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
		masterCardRequest.setTokenId(response.getTokenId());
		masterCardRequest.setCustomerId(response.getCustomerId());
		MasterCardInvoiceRequest masterCardInvoiceRequest = new MasterCardInvoiceRequest();
		masterCardInvoiceRequest.setAmount(masterCardRequest.getAmount());
		masterCardInvoiceRequest.setDescription("Test description");
		masterCardInvoiceRequest.setMemo("Test product");
		masterCardInvoiceRequest.setNote("Test note");
		masterCardInvoiceRequest.setProductId("1");
		masterCardInvoiceRequest.setQuantity(1);
		masterCardInvoiceRequest.setStatus("OPEN");
		masterCardInvoiceRequest.setReference("Test reference");
		masterCardInvoiceRequest.setSuppliedDate(System.currentTimeMillis());

		MasterCardInvoiceResponse masterCardInvoiceResponse = masterCardPaymentManager
				.createInvoice(masterCardRequest, masterCardInvoiceRequest);
		Assert.assertNotNull(masterCardInvoiceResponse);
		Assert.assertNotNull(masterCardInvoiceResponse.getInvoiceId());

	}

	@Test
	public void testCreateTokenAndCustomerAndInvoiceAndApprove() {
		MasterCardRequest masterCardRequest = createMasterCardRequest();
		MasterCardResponse response = masterCardPaymentManager
				.createCardToken(masterCardRequest);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getTokenId());
		Assert.assertNotNull(response.getCustomerId());
		masterCardRequest.setTokenId(response.getTokenId());
		masterCardRequest.setCustomerId(response.getCustomerId());
		masterCardRequest.setAmount(Double.valueOf(100));
		masterCardRequest.setPaymentDescription("Test description approve");
		masterCardRequest.setReferenceNumber("Testrefer approve");
		masterCardRequest.setTokenId(response.getTokenId());
		masterCardRequest.setCustomerId(response.getCustomerId());
		MasterCardInvoiceRequest masterCardInvoiceRequest = new MasterCardInvoiceRequest();
		masterCardInvoiceRequest.setAmount(masterCardRequest.getAmount());
		masterCardInvoiceRequest.setDescription("Test descriptionapprove");
		masterCardInvoiceRequest.setMemo("Test product approve");
		masterCardInvoiceRequest.setNote("Test note approve");
		masterCardInvoiceRequest.setProductId("1");
		masterCardInvoiceRequest.setQuantity(1);

		masterCardInvoiceRequest.setReference("Test reference approve");
		masterCardInvoiceRequest.setSuppliedDate(System.currentTimeMillis());

		MasterCardInvoiceResponse masterCardInvoiceResponse = masterCardPaymentManager
				.createInvoice(masterCardRequest, masterCardInvoiceRequest);
		Assert.assertNotNull(masterCardInvoiceResponse);
		Assert.assertNotNull(masterCardInvoiceResponse.getInvoiceId());
		masterCardInvoiceRequest = new MasterCardInvoiceRequest();
		masterCardInvoiceRequest.setInvoiceId(masterCardInvoiceResponse
				.getInvoiceId());
		masterCardInvoiceRequest.setStatus("OPEN");
		masterCardInvoiceResponse = masterCardPaymentManager
				.approveInvoice(masterCardInvoiceRequest);
		Assert.assertNotNull(masterCardInvoiceResponse);
		Assert.assertNotNull(masterCardInvoiceResponse.getInvoiceId());
		// Assert.assertNotNull(masterCardInvoiceResponse.getPaymentId());
		// Assert.assertNotNull(masterCardInvoiceResponse.getPaymentInvoice());
	}

	@Test
	public void testCreateTokenAndCustomerAndInvoiceAndInvoiceItem() {
		MasterCardRequest masterCardRequest = createMasterCardRequest();
		MasterCardResponse response = masterCardPaymentManager
				.createCardToken(masterCardRequest);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getTokenId());
		Assert.assertNotNull(response.getCustomerId());
		masterCardRequest.setTokenId(response.getTokenId());
		masterCardRequest.setCustomerId(response.getCustomerId());
		masterCardRequest.setAmount(Double.valueOf(100));
		masterCardRequest.setPaymentDescription("Test description approve");
		masterCardRequest.setReferenceNumber("Testrefer approve");
		masterCardRequest.setTokenId(response.getTokenId());
		masterCardRequest.setCustomerId(response.getCustomerId());
		MasterCardInvoiceRequest masterCardInvoiceRequest = new MasterCardInvoiceRequest();
		masterCardInvoiceRequest.setAmount(masterCardRequest.getAmount());
		masterCardInvoiceRequest.setDescription("Test descriptionapprove");
		masterCardInvoiceRequest.setMemo("Test product approve");
		masterCardInvoiceRequest.setNote("Test note approve");
		masterCardInvoiceRequest.setProductId("1");
		masterCardInvoiceRequest.setQuantity(1);

		masterCardInvoiceRequest.setReference("Test reference approve");
		masterCardInvoiceRequest.setSuppliedDate(System.currentTimeMillis());

		MasterCardInvoiceResponse masterCardInvoiceResponse = masterCardPaymentManager
				.createInvoice(masterCardRequest, masterCardInvoiceRequest);
		Assert.assertNotNull(masterCardInvoiceResponse);
		Assert.assertNotNull(masterCardInvoiceResponse.getInvoiceId());

		masterCardInvoiceRequest = new MasterCardInvoiceRequest();
		masterCardInvoiceRequest.setInvoiceId(masterCardInvoiceResponse
				.getInvoiceId());
		masterCardInvoiceRequest.setAmount(Double.valueOf(100));
		masterCardInvoiceRequest.setReference("Test reference approve");

		masterCardInvoiceResponse = masterCardPaymentManager
				.invoiceItem(masterCardInvoiceRequest);
		Assert.assertNotNull(masterCardInvoiceResponse);
		Assert.assertNotNull(masterCardInvoiceResponse.getInvoiceId());
		Assert.assertNotNull(masterCardInvoiceResponse.getPaymentInvoice());
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
