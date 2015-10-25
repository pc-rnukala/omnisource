package com.omnisource.manager;

public class MasterCardInvoiceRequest {
	private String productId;
	private Double amount;
	private String description;
	private int quantity;
	private String reference;
	private String taxId;
	private Long suppliedDate;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public Long getSuppliedDate() {
		return suppliedDate;
	}

	public void setSuppliedDate(Long suppliedDate) {
		this.suppliedDate = suppliedDate;
	}
	
}
