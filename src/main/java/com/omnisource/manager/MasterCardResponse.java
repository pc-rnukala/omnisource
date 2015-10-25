package com.omnisource.manager;

public class MasterCardResponse {
	private String customerId;
	private String cardPaymentId;
	private String tokenId;
	private String invoiceId;
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCardPaymentId() {
		return cardPaymentId;
	}
	public void setCardPaymentId(String cardPaymentId) {
		this.cardPaymentId = cardPaymentId;
	}
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
}
