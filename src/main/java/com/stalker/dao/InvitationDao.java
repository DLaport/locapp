package com.stalker.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import com.stalker.dao.model.Invitation;
import com.stalker.dao.model.User;

public class InvitationDao
extends Dao {

	public Optional<Invitation> createInvitation(final int userId, final int friendId) {
		if ((userId != friendId)) {
			final Invitation invitation = new Invitation();
			invitation.setUserId(entityManager.find(User.class, userId));
			invitation.setFriendId(entityManager.find(User.class, friendId));

			if ((invitation.getUserId() != null) && (invitation.getFriendId() != null)) {
				// Check if they are already friends.
				final String sql1 = "from Friend where (userId.id=:userid and friendId.id=:friendid) or (userId.id=:friendid and friendId.id=:userid)";
				final Query checkFriendsQuery = entityManager.createQuery(sql1).setParameter("userid", userId).setParameter("friendid", friendId);
				// Check if they already invited each other.
				final String sql2 = "from Invitation where (userId.id=:userid and friendId.id=:friendid) or (userId.id=:friendid and friendId.id=:userid)";
				final Query checkInvitationsQuery = entityManager.createQuery(sql2).setParameter("userid", userId).setParameter("friendid", friendId);

				if ((checkFriendsQuery.getResultList().size() == 0) && (checkInvitationsQuery.getResultList().size() == 0)) {
					// Only one row is added, because the "invitation" relation is always reciprocal.
					executeInTransaction(() -> entityManager.persist(invitation));
					return Optional.of(invitation);
				}
			}
		}
		return Optional.empty();
	}

	public List<Invitation> getInvitations(final int userId) {
		final String sqlQuery = "from Invitation where userId.id=:userid or friendId.id=:userid";
		return entityManager.createQuery(sqlQuery, Invitation.class).setParameter("userid", userId).getResultList();
	}

	public void deleteInvitation(final String invitationId) {
		final Invitation invitation = entityManager.find(Invitation.class, Integer.valueOf(invitationId));
		executeInTransaction(() -> entityManager.remove(invitation));
	}
}
