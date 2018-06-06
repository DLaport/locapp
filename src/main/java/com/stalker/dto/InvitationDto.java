package com.stalker.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.stalker.dao.model.Invitation;
import com.stalker.dao.model.User;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InvitationDto {
	private String id;
	private String userId;
	private String friendId;

	public InvitationDto() {
	}

	public InvitationDto(final int id, final int userId, final int friendId) {
		this.id = Integer.toString(id);
		this.userId = Integer.toString(userId);
		this.friendId = Integer.toString(friendId);
	}

	public Invitation toDao() {
		final Invitation invitationDao = new Invitation();
		final User user = new User();
		if (userId != null) {
			user.setId(Integer.valueOf(userId));
		}
		final User friend = new User();
		if (friendId != null) {
			friend.setId(Integer.valueOf(friendId));
		}

		if (id != null) {
			invitationDao.setId(Integer.valueOf(id));
		}
		invitationDao.setUserId(user);
		invitationDao.setFriendId(friend);

		return invitationDao;
	}

	public String getId() {
		return id;
	}

	public String getUserId() {
		return userId;
	}

	public String getFriendId() {
		return friendId;
	}
}
