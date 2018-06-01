package com.stalker.dao;

import java.io.Closeable;

import javax.persistence.EntityManager;

public abstract class Dao
implements Closeable {
	final EntityManager entityManager;

	public Dao() {
		entityManager = EntityManagerUtil.getEntityManager();
	}

	@Override
	public void close() {
		entityManager.close();
	}
}
