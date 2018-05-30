package com.stalker.dao;

import java.io.Closeable;
import java.util.Optional;

import javax.persistence.EntityManager;

import com.stalker.dao.model.Invitation;
import com.stalker.dao.model.User;

public class InvitationDao
implements Closeable {
	private final EntityManager entityManager;

	public InvitationDao() {
		entityManager = EntityManagerUtil.getEntityManager();
	}

	public Optional<Invitation> createInvitation(final int userId, final int friendId) {
		// Check if the invitation already exists.
		final String sqlQuery = "from Invitation where userid=:userid and friendid=:friendid";
		// TODO: check if the user is already in your friends list
		final boolean exists = entityManager.createQuery(sqlQuery)
			.setParameter("userid", userId).setParameter("friendid", friendId).getFirstResult() > 0;
		if (!exists && (userId != friendId)) {
			final Invitation invitation = new Invitation();
			invitation.setUserId(entityManager.find(User.class, userId));
			invitation.setFriendId(entityManager.find(User.class, friendId));
			EntityManagerUtil.executeInTransaction(entityManager, () -> entityManager.persist(invitation));
			return Optional.of(invitation);
		}
		return Optional.empty();
	}

	public void deleteInvitation(final String invitationId) {
		final Invitation invitation = entityManager.find(Invitation.class, Integer.valueOf(invitationId));
		EntityManagerUtil.executeInTransaction(entityManager, () -> entityManager.remove(invitation));
	}

	@Override
	public void close() {
		entityManager.close();
	}
}
