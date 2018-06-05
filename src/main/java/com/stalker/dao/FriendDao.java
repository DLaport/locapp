package com.stalker.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ws.rs.core.SecurityContext;

import com.stalker.dao.model.Friend;
import com.stalker.dao.model.Invitation;
import com.stalker.dao.model.User;

public class FriendDao
extends Dao {

	public FriendDao() {
	}

	public FriendDao(final SecurityContext securityContext) {
		super(securityContext);
	}

	public Optional<Friend> addFriend(final int invitationId, final int userId) {
		validateUser(userId);
		final Invitation invitation = entityManager.find(Invitation.class, invitationId);
		if (userId != invitation.getUserId().getId()) {
			final Friend friend = new Friend();
			friend.setUserId(invitation.getUserId());
			friend.setFriendId(invitation.getFriendId());
			// Only one row is added, because the "friends" relation is always reciprocal.
			executeInTransaction(() -> entityManager.persist(friend));
			return Optional.of(friend);
		}
		return Optional.empty();
	}

	public List<User> getFriends(final int userId) {
		validateUser(userId);
		final String sqlQuery = "from Friend where userId.id=:userid or friendId.id=:userid";
		return entityManager.createQuery(sqlQuery, Friend.class).setParameter("userid", userId).getResultStream()
			.map(result -> result.getUserId().getId() == userId ? result.getFriendId() : result.getUserId())
			.collect(Collectors.toList());
	}

	public void deleteFriend(final int userId, final int friendId) {
		validateUser(userId);
		final String sqlQuery = "from Friend where (userId.id=:userid and friendId.id=:friendid) or (userId.id=:friendid and friendId.id=:userid)";
		entityManager.createQuery(sqlQuery, Friend.class).setParameter("userid", userId).setParameter("friendid", friendId)
			.getResultStream().findAny().ifPresent(friendRelation -> {
				executeInTransaction(() -> entityManager.remove(friendRelation));
			});
	}
}
