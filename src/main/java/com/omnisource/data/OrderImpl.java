package com.omnisource.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author jponsekar
 *
 */
@Entity
@Table(name = "ORDER")
public class OrderImpl implements Order {
	private Long id;
	private Date createdDate;
	private Date updatedDate;
	private String externalInvoiceId;
	private Long userId;
	private Long cardId;
	private String merchantId;
	private String locationId;
	private String addressDetails;

	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.data.Order#getId()
	 */
	@Override
	@Id
	@Column(name = "order_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("order_id")
	public Long getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.data.Order#setId(java.lang.Long)
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.data.Order#getUserId()
	 */
	@Override
	@Column(name = "user_id")
	public Long getUserId() {
		return userId;
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.data.Order#setUserId(java.lang.Long)
	 */

	@Override
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.dao.CardAccountNew#getCreatedDate()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.data.Order#getCreatedDate()
	 */
	@Override
	@Column(name = "created_date")
	@JsonIgnore
	public Date getCreatedDate() {
		return createdDate;
	}

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.data.Order#setCreatedDate(java.util.Date)
	 */
	@Override
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.data.Order#getUpdatedDate()
	 */
	@Override
	@Column(name = "updated_date")
	@JsonIgnore
	public Date getUpdatedDate() {
		return updatedDate;
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.data.Order#setUpdatedDate(java.util.Date)
	 */
	@Override
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.data.Order#getExternalInvoiceId()
	 */
	@Override
	@Column(name = "external_invoice_id")
	public String getExternalInvoiceId() {
		return externalInvoiceId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.data.Order#setExternalInvoiceId(java.lang.String)
	 */
	@Override
	public void setExternalInvoiceId(String externalInvoiceId) {
		this.externalInvoiceId = externalInvoiceId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.data.Order#getCardId()
	 */
	@Override
	@Column(name = "card_id")
	public Long getCardId() {
		return cardId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.data.Order#setCardId(java.lang.Long)
	 */
	@Override
	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.data.Order#getMerchantId()
	 */
	@Override
	@Column(name = "merchant_id")
	public String getMerchantId() {
		return merchantId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.data.Order#setMerchantId(java.lang.String)
	 */
	@Override
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.data.Order#getLocationId()
	 */
	@Override
	@Column(name = "location_id")
	public String getLocationId() {
		return locationId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.data.Order#setLocationId(java.lang.String)
	 */
	@Override
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	@Override
	@Column(name = "address_details")
	public String getAddressDetails() {
		return addressDetails;
	}

	public void setAddressDetails(String addressDetails) {
		this.addressDetails = addressDetails;
	}

}
