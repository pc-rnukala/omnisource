package com.omnisource.dao;

import java.util.List;

import com.omnisource.data.CardAccount;
import com.omnisource.data.User;

public interface CardAccountDao {

	/**
	 * This is to create card account
	 */
	public abstract CardAccount saveOrUpdate(CardAccount cardAccount);

	public abstract boolean deleteCardAccount(CardAccount cardAccount);

	public abstract CardAccount findCardAccount(Long cardId);

	public List<CardAccount> getUserCardAccounts(final User user);

}