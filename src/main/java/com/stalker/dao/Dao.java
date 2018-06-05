package com.stalker.dao;

import java.io.Closeable;
import java.util.function.Supplier;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.core.SecurityContext;

import com.stalker.dao.model.User;

public abstract class Dao
implements Closeable {
	private static final String PERSISTENCE_UNIT_NAME = "postgres";
	private static final EntityManagerFactory entityManagerFactory;
	private String username; // from session
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

	public Dao(final SecurityContext securityContext) {
		this();
		username = securityContext.getUserPrincipal().getName();
	}

	void validateUser(final int userId)
	throws IllegalArgumentException {
		// Try to get the user specified in the request url...
		final User user = entityManager.find(User.class, userId);
		// ... and check that the one who made the request is the same person.
		if ((user == null) || !user.getUsername().equals(username)) {
			throw new IllegalArgumentException("unauthorized");
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
