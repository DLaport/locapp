package com.stalker.dao;

import java.io.Closeable;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.security.auth.login.CredentialException;

import com.stalker.dao.model.User;

public class UserDao
implements Closeable {
	private final EntityManager entityManager;

	public UserDao() {
		entityManager = EntityManagerUtil.getEntityManager();
	}

	public void createUser(final User user) {
		entityManager.getTransaction().begin();
		entityManager.persist(user);
		entityManager.getTransaction().commit();
	}

	public Optional<User> getUserById(final int id) {
		final User user = entityManager.find(User.class, id);
		return Optional.ofNullable(user);
	}

	public Optional<User> getUserByToken(final String token) {
		final String sqlQuery = "from User where token=:token";
		return entityManager.createQuery(sqlQuery, User.class).setParameter("token", token).getResultStream().findAny();
	}

	public List<User> searchUsers(final String search) {
		final String sqlQuery = "from User where lower(username) like lower(:search)";
		final List<User> users = entityManager.createQuery(sqlQuery, User.class).setParameter("search", "%" + search + "%").getResultList();
		return users;
	}

	public void authenticateUser(final String username, final String password)
	throws CredentialException {
		final String sqlQuery = "from User where username=:username and password=:password";
		final boolean credentialsValid = entityManager.createQuery(sqlQuery, User.class)
			.setParameter("username", username)
			.setParameter("password", password)
			.getResultList().size() > 0;
		if (!credentialsValid) {
			throw new CredentialException();
		}
	}

	@Override
	public void close() {
		entityManager.close();
	}
}
