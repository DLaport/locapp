package com.stalker.dao;

import java.util.function.Supplier;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {
	private static final String PERSISTENCE_UNIT_NAME = "postgres";
	private static EntityManagerFactory entityManagerFactory;

	static {
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static EntityManager getEntityManager() {
		return entityManagerFactory.createEntityManager();
	}

	public static void executeInTransaction(final EntityManager entityManager, final Runnable function) {
		entityManager.getTransaction().begin();
		function.run();
		entityManager.getTransaction().commit();
	}

	public static <T> T executeInTransaction(final EntityManager entityManager, final Supplier<T> function) {
		entityManager.getTransaction().begin();
		final T result = function.get();
		entityManager.getTransaction().commit();
		return result;
	}
}
