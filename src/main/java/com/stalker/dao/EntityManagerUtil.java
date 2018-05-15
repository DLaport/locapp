package com.stalker.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {
	private static final String PERSISTENCE_UNIT_NAME = "postgres";
	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	
	public static EntityManager getEntityManager() {
		return entityManagerFactory.createEntityManager();
	}
}
