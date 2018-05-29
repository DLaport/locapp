package com.stalker.dao;

import java.io.Closeable;

import javax.persistence.EntityManager;

import com.stalker.dao.model.Position;
import com.stalker.dao.model.User;

public class PositionDao
implements Closeable {
	private final EntityManager entityManager;

	public PositionDao() {
		entityManager = EntityManagerUtil.getEntityManager();
	}

	public Position updatePosition(final String userId, final Position position) {
		position.setUser(entityManager.find(User.class, Integer.valueOf(userId)));
		return EntityManagerUtil.executeInTransaction(entityManager, () -> entityManager.merge(position));
	}

	@Override
	public void close() {
		entityManager.close();
	}
}
