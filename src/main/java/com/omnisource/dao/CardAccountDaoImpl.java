package com.omnisource.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.omnisource.data.CardAccount;
import com.omnisource.data.User;

public class CardAccountDaoImpl extends DaoImpl implements CardAccountDao {

	/* (non-Javadoc)
	 * @see com.omnisource.dao.CardAccount#saveOrUpdate(com.omnisource.data.CardAccount)
	 */

	@Transactional(value = "omniTran", readOnly = false, propagation = Propagation.REQUIRED)
	public CardAccount saveOrUpdate(CardAccount cardAccount) {
		if (cardAccount == null) {
			return null;
		}
		CardAccount cardAccountLocal = em.merge(cardAccount);
		return cardAccountLocal;
	}

	/* (non-Javadoc)
	 * @see com.omnisource.dao.CardAccount#deleteCardAccount(com.omnisource.data.CardAccount)
	 */
	@Transactional(value = "omniTran", readOnly = false, propagation = Propagation.REQUIRED)
	public boolean deleteCardAccount(CardAccount cardAccount) {
		if (cardAccount == null) {
			return false;
		}
		try {
			CardAccount cardAccountLocal = em.find(CardAccount.class,
					cardAccount.getId());
			em.remove(cardAccountLocal);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.omnisource.dao.CardAccount#findOrder(java.lang.Long)
	 */

	@Transactional(value = "omniTran", readOnly = false, propagation = Propagation.REQUIRED)
	public CardAccount findCardAccount(Long cardId) {
		if (cardId == null) {
			return null;
		}
		try {
			CardAccount cardAccountLocal = em.find(CardAccount.class, cardId);
			return cardAccountLocal;
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(value = "omniTran", readOnly = true, propagation = Propagation.SUPPORTS)
	public List<CardAccount> getUserCardAccounts(final User user) {
		try {
			final String queryString = "select model from CardAccountImpl model where model.userId=:userId";
			Query query = em.createQuery(queryString);
			query.setParameter("userId", user.getId());
			List<CardAccount> result = query.getResultList();
			if (result != null && result.size() > 0) {
				return result;
			} else {
				return null;
			}
		} catch (RuntimeException re) {
			logger.error("getUserCardAccounts", re);
			throw re;
		}
	}

}
