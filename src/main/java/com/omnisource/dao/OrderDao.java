package com.omnisource.dao;

import java.util.List;

import com.omnisource.data.Order;
import com.omnisource.data.User;

public interface OrderDao {

	/**
	 * This is to create order
	 */
	public abstract Order saveOrUpdate(Order order);

	public abstract boolean deleteOrder(Order order);

	public abstract Order findOrder(Long orderId);

	public List<Order> getUserOrders(final User user);
}