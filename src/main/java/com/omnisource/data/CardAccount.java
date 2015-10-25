package com.omnisource.data;

import java.util.Date;

public interface CardAccount {

	/*
	 * (non-Javadoc)
	 */
	public abstract Long getId();

	public abstract void setId(Long id);

	public abstract Long getUserId();

	public abstract void setUserId(Long userId);

	/*
	 * (non-Javadoc)
	 */
	public abstract Date getCreatedDate();

	/*
	 * (non-Javadoc)
	 */
	public abstract void setCreatedDate(Date createdDate);

	/*
	 * (non-Javadoc)
	 */
	public abstract Date getUpdatedDate();

	public abstract void setUpdatedDate(Date updatedDate);

	public abstract String getName();

	public abstract void setName(String name);

	public abstract String getDescription();

	public abstract void setDescription(String description);

	public abstract String getCardType();

	public abstract void setCardType(String cardType);

	public abstract String getTokenId();

	public abstract void setTokenId(String tokenId);

	public String getCustomerId();

	public void setCustomerId(String customerId);

}