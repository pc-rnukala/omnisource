package com.omnisource.manager;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class OmniDbManagerTest {
	@Autowired
	private OmniDbManager omniDbManager;

	@Test
	public void testDbConnection() {
		boolean status = false;
		try {
			Connection connection = omniDbManager.getConnection();
			status = connection != null ? true : false;
			connection.close();
		} catch (Exception e) {
			status = false;
		}
		assertTrue("Db status failed", status);
	}
}
