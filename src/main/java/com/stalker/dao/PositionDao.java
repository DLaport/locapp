package com.stalker.dao;

import java.util.List;

import com.stalker.dao.model.Position;
import com.stalker.dao.model.User;

public class PositionDao
extends Dao {

	public Position updatePosition(final String userId, final Position position) {
		position.setUser(entityManager.find(User.class, Integer.valueOf(userId)));
		return executeInTransaction(() -> entityManager.merge(position));
	}

	public List<Position> getFriendsPositions(final int userId) {
		final String sqlQuery = "from Position where user.id=(select friendId.id from Friend where userId.id=:userid)" //
		+ " or user.id=(select userId.id from Friend where friendId=:userid)";
		return entityManager.createQuery(sqlQuery, Position.class).setParameter("userid", userId).getResultList();
	}
}
