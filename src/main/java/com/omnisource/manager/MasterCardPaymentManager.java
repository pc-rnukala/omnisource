package com.omnisource.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.simplify.payments.PaymentsApi;
import com.simplify.payments.PaymentsMap;
import com.simplify.payments.domain.CardToken;
import com.simplify.payments.domain.Customer;
import com.simplify.payments.domain.Invoice;
import com.simplify.payments.domain.InvoiceItem;
import com.simplify.payments.domain.Payment;

/**
 * Master card payment manager for master card api
 * 
 * @author jponsekar
 *
 */
public class MasterCardPaymentManager {

	private static final Logger logger = LoggerFactory
			.getLogger(MasterCardPaymentManager.class);
	private String privateKey;
	private String publicKey;

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	private Customer createCustomer(final MasterCardRequest masterCardRequest) {
		try {
			PaymentsApi.PUBLIC_KEY = this.getPublicKey();
			PaymentsApi.PRIVATE_KEY = this.getPrivateKey();
			Customer customer = Customer.create(new PaymentsMap()
					.set("token", masterCardRequest.getTokenId())
					.set("email", masterCardRequest.getEmail())
					.set("name", masterCardRequest.getName()));
			// customer.
			logger.info("Customer :", customer.get("id"));
			return customer;
		} catch (Exception e) {
			logger.error("Exception occured", e);
		}
		return null;
	}

	public MasterCardResponse findCustomer(
			final MasterCardRequest masterCardRequest) {
		try {
			PaymentsApi.PUBLIC_KEY = this.getPublicKey();
			PaymentsApi.PRIVATE_KEY = this.getPrivateKey();
			Customer customer = Customer
					.find(masterCardRequest.getCustomerId());
			// customer.
			logger.info("Customer :", customer.get("id"));
			String customerId = (String) customer.get("id");
			MasterCardResponse response = new MasterCardResponse();
			response.setCustomerId(customerId);
			return response;
		} catch (Exception e) {
			logger.error("Exception occured", e);
		}
		return null;
	}

	public MasterCardResponse findCardToken(
			final MasterCardRequest masterCardRequest) {
		try {
			PaymentsApi.PUBLIC_KEY = this.getPublicKey();
			PaymentsApi.PRIVATE_KEY = this.getPrivateKey();
			CardToken cardToken = CardToken
					.find(masterCardRequest.getTokenId());
			// customer.
			logger.info("cardToken :", cardToken.get("id"));
			String tokenId = (String) cardToken.get("id");
			MasterCardResponse response = new MasterCardResponse();
			response.setTokenId(tokenId);
			return response;
		} catch (Exception e) {
			logger.error("Exception occured", e);
		}
		return null;
	}

	public MasterCardResponse createCardToken(
			final MasterCardRequest masterCardRequest) {
		PaymentsApi.PUBLIC_KEY = this.getPublicKey();
		PaymentsApi.PRIVATE_KEY = this.getPrivateKey();

		CardToken cardToken;
		try {
			cardToken = CardToken
					.create(new PaymentsMap()
							.set("card.name", masterCardRequest.getName())
							.set("card.addressCity",
									masterCardRequest.getAddressCity())
							.set("card.addressState",
									masterCardRequest.getAddressState())
							.set("card.cvc", masterCardRequest.getCardCvc())
							.set("card.expMonth",
									masterCardRequest.getCardExpMonth())
							.set("card.expYear",
									masterCardRequest.getCardExpYear())
							.set("card.number",
									masterCardRequest.getCardNumber()));
			String tokenId = (String) cardToken.get("id");
			logger.info("CardToken :", cardToken);
			logger.info("tokenId :", tokenId);
			masterCardRequest.setTokenId(tokenId);
			Customer customer = this.createCustomer(masterCardRequest);
			String customerId = (String) customer.get("id");
			MasterCardResponse response = new MasterCardResponse();
			response.setTokenId(tokenId);
			response.setCustomerId(customerId);
			return response;
		} catch (Exception e) {
			logger.error("Exception occured", e);
		}
		return null;
	}

	public MasterCardResponse createPayment(
			final MasterCardRequest masterCardRequest) {
		PaymentsApi.PUBLIC_KEY = this.getPublicKey();
		PaymentsApi.PRIVATE_KEY = this.getPrivateKey();
		Payment payment;
		try {
			payment = Payment.create(new PaymentsMap()
					.set("amount", masterCardRequest.getAmount())
					.set("currency", "USD")
					.set("description",
							masterCardRequest.getPaymentDescription())
					.set("reference", masterCardRequest.getReferenceNumber())
					.set("customer", masterCardRequest.getCustomerId()));
			if ("APPROVED".equals(payment.get("paymentStatus"))) {
				logger.info("Payment approved :");
			}
			// customer.
			logger.info("Payment :", payment);
			String paymentId = (String) payment.get("id");
			// String cardId = (String) payment.get("card.id");
			String customerId = (String) payment.get("customer.id");
			MasterCardResponse response = new MasterCardResponse();
			response.setTokenId(masterCardRequest.getTokenId());
			response.setCustomerId(customerId);
			response.setCardPaymentId(paymentId);
			return response;
		} catch (Exception e) {
			logger.error("Exception occured", e);
		}
		return null;
	}

	public MasterCardResponse findPayment(
			final MasterCardRequest masterCardRequest) {
		PaymentsApi.PUBLIC_KEY = this.getPublicKey();
		PaymentsApi.PRIVATE_KEY = this.getPrivateKey();
		Payment payment;
		try {
			payment = Payment.find(masterCardRequest.getPaymentId());
			if ("APPROVED".equals(payment.get("paymentStatus"))) {
				logger.info("Payment approved :");
			}
			// customer.
			logger.info("Payment :", payment);
			String paymentId = (String) payment.get("id");
			// String cardId = (String) payment.get("card.id");
			String customerId = (String) payment.get("customer.id");
			MasterCardResponse response = new MasterCardResponse();
			response.setTokenId(masterCardRequest.getTokenId());
			response.setCustomerId(customerId);
			response.setCardPaymentId(paymentId);
			return response;
		} catch (Exception e) {
			logger.error("Exception occured", e);
		}
		return null;
	}

	public MasterCardInvoiceResponse createInvoice(
			final MasterCardRequest masterCardRequest,
			final MasterCardInvoiceRequest masterCardInvoiceRequest) {
		PaymentsApi.PUBLIC_KEY = this.getPublicKey();
		PaymentsApi.PRIVATE_KEY = this.getPrivateKey();
		try {
			Invoice invoice = Invoice.create(new PaymentsMap()
					.set("currency", "USD")
					.set("email", masterCardRequest.getEmail())
					.set("items[0].amount",
							masterCardInvoiceRequest.getAmount())
					.set("items[0].quantity",
							masterCardInvoiceRequest.getQuantity())
					// .set("items[0].tax", masterCardInvoiceRequest.getTaxId())
					.set("items[0].description",
							masterCardInvoiceRequest.getDescription())
					.set("items[0].product",
							masterCardInvoiceRequest.getProductId())
					.set("memo", masterCardInvoiceRequest.getMemo())
					.set("customer", masterCardRequest.getCustomerId())
					.set("status", masterCardInvoiceRequest.getStatus())
					.set("note", masterCardInvoiceRequest.getNote())
					.set("reference", masterCardInvoiceRequest.getReference())
					.set("suppliedDate",
							masterCardInvoiceRequest.getSuppliedDate()));
			// customer.
			logger.info("invoice :", invoice);
			String invoiceId = (String) invoice.get("id");
			String invoiceUUID = (String) invoice.get("uuid");
			String customerId = (String) invoice.get("customer.id");
			// String payment = (String) invoice.get("payment");
			// String paymentId = (String) invoice.get("payment.id");
			MasterCardInvoiceResponse response = new MasterCardInvoiceResponse();
			response.setCustomerId(customerId);
			response.setInvoiceUUID(invoiceUUID);
			response.setInvoiceId(invoiceId);
			// response.setPaymentInvoice(payment);
			// response.setPaymentId(paymentId);
			return response;

		} catch (Exception e) {
			logger.error("Exception occured", e);
		}
		return null;
	}

	public MasterCardInvoiceResponse approveInvoice(
			final MasterCardInvoiceRequest masterCardInvoiceRequest) {
		PaymentsApi.PUBLIC_KEY = this.getPublicKey();
		PaymentsApi.PRIVATE_KEY = this.getPrivateKey();
		try {
			Invoice invoice = Invoice.find(masterCardInvoiceRequest
					.getInvoiceId());
			invoice.set("status", masterCardInvoiceRequest.getStatus());
			invoice.update();
			logger.info("invoice :", invoice);
			String invoiceId = (String) invoice.get("id");
			String invoiceUUID = (String) invoice.get("uuid");
			String customerId = (String) invoice.get("customer.id");
			MasterCardInvoiceResponse response = new MasterCardInvoiceResponse();
			response.setCustomerId(customerId);
			response.setInvoiceUUID(invoiceUUID);
			response.setInvoiceId(invoiceId);
			return response;
		} catch (Exception e) {
			logger.error("Exception occured", e);
		}
		return null;
	}

	public MasterCardInvoiceResponse invoiceItem(
			final MasterCardInvoiceRequest masterCardInvoiceRequest) {
		PaymentsApi.PUBLIC_KEY = this.getPublicKey();
		PaymentsApi.PRIVATE_KEY = this.getPrivateKey();
		try {
			InvoiceItem invoiceItem = InvoiceItem.create(new PaymentsMap()
					.set("amount", masterCardInvoiceRequest.getAmount())
					.set("description", "Invoice Item1")
					.set("invoice", masterCardInvoiceRequest.getInvoiceId())
					.set("reference", masterCardInvoiceRequest.getReference()));
			logger.info("invoiceItem :", invoiceItem);
			String invoiceId = (String) invoiceItem.get("id");
			Long totalAmount = (Long) invoiceItem.get("totalAmount");
			MasterCardInvoiceResponse response = new MasterCardInvoiceResponse();
			response.setInvoiceId(invoiceId);
			response.setPaymentInvoice(totalAmount);
			return response;
		} catch (Exception e) {
			logger.error("Exception occured", e);
		}
		return null;
	}
}
