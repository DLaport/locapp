package com.stalker.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.security.auth.login.CredentialException;

import com.stalker.dao.model.User;

public class UserDao
extends Dao {

	public void createUser(final User user) {
		EntityManagerUtil.executeInTransaction(entityManager, () -> entityManager.persist(user));
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
		// TODO: search only users that are not already in the friends list
		final String sqlQuery = "from User where lower(username) like lower(:search)";
		final List<User> users = entityManager.createQuery(sqlQuery, User.class).setParameter("search", "%" + search + "%").getResultList();
		return users;
	}

	public String authenticateUser(final String username, final String password)
	throws CredentialException {
		final String sqlQuery = "from User where username=:username and password=:password";
		return entityManager.createQuery(sqlQuery, User.class)
			.setParameter("username", username)
			.setParameter("password", password)
			.getResultStream().findAny()
			.map(user -> {
				user.setToken(UUID.randomUUID().toString());
				EntityManagerUtil.executeInTransaction(entityManager, () -> entityManager.merge(user));
				return user.getToken();
			}).orElseThrow(CredentialException::new);
	}

	public void disconnectUser(final String token) {
		getUserByToken(token).ifPresent(user -> {
			user.setToken(null);
			EntityManagerUtil.executeInTransaction(entityManager, () -> entityManager.merge(user));
		});
	}
}
