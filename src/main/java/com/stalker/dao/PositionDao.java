package com.stalker.dao;

import com.stalker.dao.model.Position;
import com.stalker.dao.model.User;

public class PositionDao
extends Dao {

	public Position updatePosition(final String userId, final Position position) {
		position.setUser(entityManager.find(User.class, Integer.valueOf(userId)));
		return executeInTransaction(() -> entityManager.merge(position));
	}
}
