package com.omnisource.dao;

import java.util.List;

import com.omnisource.data.User;

public interface UserDao {

	public List<User> getUsers();

	public User saveOrUpdate(User user);

	public boolean deleteUser(User user);

	public User getUser(String userName);

	public User getUserByGuid(String userGuid);

}
