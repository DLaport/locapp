package com.stalker.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.security.auth.login.CredentialException;

import com.stalker.dao.model.User;

public class UserDao
extends Dao {

	public User createUser(final User user)
	throws NoSuchAlgorithmException {
		// Hash the password
		final String currentPassword = user.getPassword();
		final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		final String digest = new String(messageDigest.digest(currentPassword.getBytes()));
		user.setPassword(digest);
		executeInTransaction(() -> entityManager.persist(user));
		return user;
	}

	public Optional<User> getUserById(final int id) {
		final User user = entityManager.find(User.class, id);
		return Optional.ofNullable(user);
	}

	public Optional<User> getUserByToken(final String token) {
		final String sqlQuery = "from User where token=:token";
		return entityManager.createQuery(sqlQuery, User.class).setParameter("token", token).getResultStream().findAny();
	}

	public List<User> searchUsers(final String username, final String search) {
		// Search only users that are not already in the friends list.
		final String sqlQuery = "from User where lower(username) like lower(:search)" //
		+ " and username!=:username" // remove the requester
		+ " and id not in (select userId.id from Friend where friendId.username=:username)" // remove friends
		+ " and id not in (select friendId.id from Friend where userId.username=:username)";
		final List<User> users = entityManager.createQuery(sqlQuery, User.class).setParameter("username", username).setParameter("search", "%" + search + "%").getResultList();
		return users;
	}

	public User authenticateUser(final String username, final String password)
	throws CredentialException, NoSuchAlgorithmException {
		// Hash the password
		final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		final String digest = new String(messageDigest.digest(password.getBytes()));

		final String sqlQuery = "from User where username=:username and password=:password";
		return entityManager.createQuery(sqlQuery, User.class)
			.setParameter("username", username)
			.setParameter("password", digest)
			.getResultStream().findAny()
			.map(user -> {
				user.setToken(UUID.randomUUID().toString());
				executeInTransaction(() -> entityManager.merge(user));
				return user;
			}).orElseThrow(CredentialException::new);
	}

	public void disconnectUser(final String token) {
		getUserByToken(token).ifPresent(user -> {
			user.setToken(null);
			executeInTransaction(() -> entityManager.merge(user));
		});
	}
}
