package com.stalker.dao;

import java.io.Closeable;
import java.util.Optional;

import javax.persistence.EntityManager;

import com.stalker.dao.model.Friend;
import com.stalker.dao.model.Invitation;

public class FriendDao
implements Closeable {
	private final EntityManager entityManager;

	public FriendDao() {
		entityManager = EntityManagerUtil.getEntityManager();
	}

	public Optional<Friend> addFriend(final int invitationId, final int requesterId) {
		final Invitation invitation = entityManager.find(Invitation.class, invitationId);
		if (requesterId != invitation.getUserId().getId()) {
			final Friend friend = new Friend();
			friend.setUserId(invitation.getUserId());
			friend.setFriendId(invitation.getFriendId());
			// Only one row is added, because the "friends" relation is always reciprocal.
			EntityManagerUtil.executeInTransaction(entityManager, () -> entityManager.persist(friend));
			return Optional.of(friend);
		}
		return Optional.empty();
	}

	@Override
	public void close() {
		entityManager.close();
	}
}
