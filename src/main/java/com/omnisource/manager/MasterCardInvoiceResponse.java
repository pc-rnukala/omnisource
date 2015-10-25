package com.omnisource.manager;

public class MasterCardInvoiceResponse {
	private String invoiceId;
	private String invoiceUUID;
	private String customerId;
	private String paymentId;
	private Long paymentInvoice;
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getInvoiceUUID() {
		return invoiceUUID;
	}
	public void setInvoiceUUID(String invoiceUUID) {
		this.invoiceUUID = invoiceUUID;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public Long getPaymentInvoice() {
		return paymentInvoice;
	}
	public void setPaymentInvoice(Long paymentInvoice) {
		this.paymentInvoice = paymentInvoice;
	}
	
}
