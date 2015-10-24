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
 * @author Jebastian Ponsekar
 * 
 */
@Entity
@Table(name = "USER")
public class UserImpl implements User {

	private Long id;
	private Date createdDate;
	private Date updatedDate;
	private String password;
	private String phoneNumber;
	private String userGuid;
	private String userName;
	private String mastercardAccountId;

	/*
	 * (non-Javadoc)
	 */
	@Override
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("userId")
	public Long getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
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
	@Override
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	@Column(name = "updated_date")
	@JsonIgnore
	public Date getUpdatedDate() {
		return updatedDate;
	}

	@Override
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Override
	@Column(name = "password")
	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	@Column(name = "phone_number")
	@JsonIgnore
	public String getPhoneNumber() {
		return phoneNumber;
	}

	@Override
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	@Column(name = "user_guid")
	public String getUserGuid() {
		return userGuid;
	}

	@Override
	public void setUserGuid(String userGuid) {
		this.userGuid = userGuid;
	}

	@Override
	@Column(name = "user_name")
	public String getUserName() {
		return userName;
	}

	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	@Column(name = "mastercard_account_id")
	@JsonIgnore
	public String getMastercardAccountId() {
		return mastercardAccountId;
	}

	public void setMastercardAccountId(String mastercardAccountId) {
		this.mastercardAccountId = mastercardAccountId;
	}

}
