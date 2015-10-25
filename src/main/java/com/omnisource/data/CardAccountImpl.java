package com.omnisource.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author jponsekar
 * 
 */
@Entity
@Table(name = "CARD_ACCOUNT")
public class CardAccountImpl implements CardAccount {

	private Long id;
	private Long userId;
	private Date createdDate;
	private Date updatedDate;
	private String name;
	private String description;
	private String cardType;
	private String tokenId;
	private String customerId;

	private static final String DEFAULT_MASTER_URL = "masterCard.png";
	private static final String DEFAULT_VISA_URL = "visaCard.png";

	/*
	 * (non-Javadoc)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.dao.CardAccountNew#getId()
	 */
	@Override
	@Id
	@Column(name = "card_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("cardId")
	public Long getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.dao.CardAccountNew#setId(java.lang.Long)
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.dao.CardAccountNew#getUserId()
	 */
	@Override
	@Column(name = "user_id")
	@JsonIgnore
	public Long getUserId() {
		return userId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.dao.CardAccountNew#setUserId(java.lang.Long)
	 */
	@Override
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/*
	 * (non-Javadoc)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.dao.CardAccountNew#getCreatedDate()
	 */
	@Override
	@Column(name = "created_date")
	@JsonIgnore
	public Date getCreatedDate() {
		return createdDate;
	}

	/*
	 * (non-Javadoc)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.dao.CardAccountNew#setCreatedDate(java.util.Date)
	 */

	@Override
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/*
	 * (non-Javadoc)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.dao.CardAccountNew#getUpdatedDate()
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
	 * @see com.omnisource.dao.CardAccountNew#setUpdatedDate(java.util.Date)
	 */
	@Override
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.dao.CardAccountNew#getName()
	 */
	@Override
	@Column(name = "name")
	@JsonProperty("cardName")
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.dao.CardAccountNew#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.dao.CardAccountNew#getDescription()
	 */
	@Override
	@Column(name = "description")
	@JsonProperty("cardDescription")
	public String getDescription() {
		return description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.dao.CardAccountNew#setDescription(java.lang.String)
	 */
	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.dao.CardAccountNew#getCardType()
	 */
	@Override
	@Column(name = "card_type")
	@JsonProperty("cardType")
	public String getCardType() {
		return cardType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.dao.CardAccountNew#setCardType(java.lang.String)
	 */
	@Override
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.dao.CardAccountNew#getTokenId()
	 */
	@Override
	@Column(name = "token_id")
	@JsonProperty("tokenId")
	public String getTokenId() {
		return tokenId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.omnisource.dao.CardAccountNew#setTokenId(java.lang.String)
	 */
	@Override
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	@Override
	@Column(name = "customer_id")
	@JsonIgnore
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Transient
	public String getImageUrl() {
		if (getCardType() != null
				&& getCardType().toLowerCase().contains("master")) {
			return DEFAULT_MASTER_URL;
		} else if (getCardType() != null
				&& getCardType().toLowerCase().contains("visa")) {
			return DEFAULT_VISA_URL;
		}
		return DEFAULT_MASTER_URL;
	}

}
