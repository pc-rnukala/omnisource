package com.omnisource.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.omnisource.data.Order;
import com.omnisource.data.User;

public class OrderDaoImpl extends DaoImpl implements OrderDao {
	/* (non-Javadoc)
	 * @see com.omnisource.dao.OrderDao#saveOrUpdate(com.omnisource.data.Order)
	 */
	@Override
	@Transactional(value = "omniTran", readOnly = false, propagation = Propagation.REQUIRED)
	public Order saveOrUpdate(Order order) {
		if (order == null) {
			return null;
		}
		Order orderLocal = em.merge(order);
		return orderLocal;
	}

	/* (non-Javadoc)
	 * @see com.omnisource.dao.OrderDao#deleteOrder(com.omnisource.data.Order)
	 */
	@Override
	@Transactional(value = "omniTran", readOnly = false, propagation = Propagation.REQUIRED)
	public boolean deleteOrder(Order order) {
		if (order == null) {
			return false;
		}
		try {
			Order orderLocal = em.find(Order.class, order.getId());
			em.remove(orderLocal);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.omnisource.dao.OrderDao#findOrder(java.lang.Long)
	 */
	@Override
	@Transactional(value = "omniTran", readOnly = false, propagation = Propagation.REQUIRED)
	public Order findOrder(Long orderId) {
		if (orderId == null) {
			return null;
		}
		try {
			Order orderLocal = em.find(Order.class, orderId);
			return orderLocal;
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(value = "omniTran", readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Order> getUserOrders(final User user) {
		try {
			final String queryString = "select model from OrderImpl model where model.userId=:userId";
			Query query = em.createQuery(queryString);
			query.setParameter("userId", user.getId());
			List<Order> result = query.getResultList();
			if (result != null && result.size() > 0) {
				return result;
			} else {
				return null;
			}
		} catch (RuntimeException re) {
			logger.error("getUserOrders", re);
			throw re;
		}
	}
}
