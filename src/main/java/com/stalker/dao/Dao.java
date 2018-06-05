package com.stalker.dao;

import java.io.Closeable;
import java.util.function.Supplier;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class Dao
implements Closeable {
	private static final String PERSISTENCE_UNIT_NAME = "postgres";
	private static final EntityManagerFactory entityManagerFactory;
	final EntityManager entityManager;

	static {
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Dao() {
		try {
			entityManager = Dao.entityManagerFactory.createEntityManager();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	void executeInTransaction(final Runnable function) {
		entityManager.getTransaction().begin();
		function.run();
		entityManager.getTransaction().commit();
	}

	<T> T executeInTransaction(final Supplier<T> function) {
		entityManager.getTransaction().begin();
		final T result = function.get();
		entityManager.getTransaction().commit();
		return result;
	}

	@Override
	public void close() {
		entityManager.close();
	}
}
