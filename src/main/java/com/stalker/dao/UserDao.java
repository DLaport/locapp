package com.stalker.dao;

import java.util.List;

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
	
	public List<User> getUsers(final String search) {
		final EntityManager entityManager = EntityManagerUtil.getEntityManager();
		final String sqlQuery = "from User where lower(username) like lower(:search)";
		final List<User> users = entityManager.createQuery(sqlQuery, User.class).setParameter("search", "%" + search + "%").getResultList();
		entityManager.close();
		return users;
	}
}
