package com.stalker.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

	/**
	 * Updates the position of the user.
	 *
	 * @param userId Current user.
	 * @param position New position.
	 * @return the saved position.
	 */
	public Position updatePosition(final int userId, final Position position) {
		validateUser(userId);
		position.setUser(entityManager.find(User.class, userId));
		position.setLastUpdate(new Date());
		final Optional<Position> somePosition = entityManager.createQuery("from Position where user.id=:userid", Position.class).setParameter("userid", userId).getResultStream().findFirst();
		if (somePosition.isPresent()) { // Update
			position.setId(somePosition.get().getId());
			executeInTransaction(() -> entityManager.merge(position));
		} else { //Create
			executeInTransaction(() -> entityManager.persist(position));
		}
		return position;
	}

	/**
	 * Retrieves the last known positions of all user's friends.
	 *
	 * @param userId Current user.
	 * @return friends positions.
	 */
	public List<Position> getFriendsPositions(final int userId) {
		validateUser(userId);
		final String sqlQuery = "from Position where user.id=(select friendId.id from Friend where userId.id=:userid)" //
		+ " or user.id=(select userId.id from Friend where friendId=:userid)";
		return entityManager.createQuery(sqlQuery, Position.class).setParameter("userid", userId).getResultList();
	}
}
