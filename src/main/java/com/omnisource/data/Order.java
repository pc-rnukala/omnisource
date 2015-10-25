package com.omnisource.data;

import java.util.Date;

public interface Order {

	/*
	 * (non-Javadoc)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.dao.CardAccountNew#getId()
	 */
	public abstract Long getId();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.dao.CardAccountNew#setId(java.lang.Long)
	 */
	public abstract void setId(Long id);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.dao.CardAccountNew#getUserId()
	 */
	public abstract Long getUserId();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.dao.CardAccountNew#setUserId(java.lang.Long)
	 */
	public abstract void setUserId(Long userId);

	/*
	 * (non-Javadoc)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.dao.CardAccountNew#getCreatedDate()
	 */
	public abstract Date getCreatedDate();

	public abstract void setCreatedDate(Date createdDate);

	/*
	 * (non-Javadoc)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.dao.CardAccountNew#getUpdatedDate()
	 */
	public abstract Date getUpdatedDate();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.dao.CardAccountNew#setUpdatedDate(java.util.Date)
	 */
	public abstract void setUpdatedDate(Date updatedDate);

	public abstract String getExternalInvoiceId();

	public abstract void setExternalInvoiceId(String externalInvoiceId);

	public abstract Long getCardId();

	public abstract void setCardId(Long cardId);

	public abstract String getMerchantId();

	public abstract void setMerchantId(String merchantId);

	public abstract String getLocationId();

	public abstract void setLocationId(String locationId);

	public String getAddressDetails();

	public void setAddressDetails(String addressDetails);

}