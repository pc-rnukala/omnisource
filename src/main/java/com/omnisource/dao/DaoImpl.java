package com.omnisource.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaoImpl implements Dao {

	protected static Logger logger = LoggerFactory.getLogger(DaoImpl.class);
	@PersistenceContext(unitName = "omnidb")
	protected EntityManager em;
}
