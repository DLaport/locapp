package com.stalker.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;
import javax.ws.rs.core.SecurityContext;

import com.stalker.dao.model.Invitation;
import com.stalker.dao.model.User;

public class InvitationDao
extends Dao {

	public InvitationDao() {
	}

	public InvitationDao(final SecurityContext securityContext) {
		super(securityContext);
	}

	/**
	 * Creates a new invitation (from the current user, to another one).
	 *
	 * @param userId Current user.
	 * @param friendId Friend identifier.
	 * @return the friend request, if both ids are different.
	 */
	public Optional<Invitation> createInvitation(final int userId, final int friendId) {
		validateUser(userId);
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

	/**
	 * Retrieves the pending invitations sent to the current user.
	 *
	 * @param userId Current user.
	 * @return pending invitations.
	 */
	public List<Invitation> getInvitations(final int userId) {
		validateUser(userId);
		final String sqlQuery = "from Invitation where userId.id=:userid or friendId.id=:userid";
		return entityManager.createQuery(sqlQuery, Invitation.class).setParameter("userid", userId).getResultList();
	}

	/**
	 * Deletes the given invitation.
	 *
	 * @param userId Current user.
	 * @param invitationId Invitation identifier.
	 */
	public void deleteInvitation(final int userId, final String invitationId) {
		validateUser(userId);
		final Invitation invitation = entityManager.find(Invitation.class, Integer.valueOf(invitationId));
		executeInTransaction(() -> entityManager.remove(invitation));
	}
}
