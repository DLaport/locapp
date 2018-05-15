package com.stalker.dao;

import javax.persistence.EntityManager;

public class UserDao {
	
	public void createUser(final User user) {
		final EntityManager entityManager = EntityManagerUtil.getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(user);
		entityManager.getTransaction().commit();
		entityManager.close();
	}
	
	public User getUser(final int id) {
		final EntityManager entityManager = EntityManagerUtil.getEntityManager();
		final User user = entityManager.find(User.class, id);
		entityManager.close();
		return user;
	}
}
