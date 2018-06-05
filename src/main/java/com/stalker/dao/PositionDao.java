package com.stalker.dao;

import java.util.List;

import javax.ws.rs.core.SecurityContext;

import com.stalker.dao.model.Position;
import com.stalker.dao.model.User;

public class PositionDao
extends Dao {

	public PositionDao() {
	}

	public PositionDao(final SecurityContext securityContext) {
		super(securityContext);
	}

	public Position updatePosition(final int userId, final Position position) {
		validateUser(userId);
		position.setUser(entityManager.find(User.class, userId));
		return executeInTransaction(() -> entityManager.merge(position));
	}

	public List<Position> getFriendsPositions(final int userId) {
		validateUser(userId);
		final String sqlQuery = "from Position where user.id=(select friendId.id from Friend where userId.id=:userid)" //
		+ " or user.id=(select userId.id from Friend where friendId=:userid)";
		return entityManager.createQuery(sqlQuery, Position.class).setParameter("userid", userId).getResultList();
	}
}
