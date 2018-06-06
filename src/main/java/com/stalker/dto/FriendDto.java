package com.stalker.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.stalker.dao.model.Friend;
import com.stalker.dao.model.User;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FriendDto {
	private String id;
	private String userId;
	private String friendId;

	public FriendDto() {
	}

	public FriendDto(final int id, final int userId, final int friendId) {
		this.id = Integer.toString(id);
		this.userId = Integer.toString(userId);
		this.friendId = Integer.toString(friendId);
	}

	public Friend toDao() {
		final Friend friendDao = new Friend();
		final User user = new User();
		if (userId != null) {
			user.setId(Integer.valueOf(userId));
		}
		final User friend = new User();
		if (friendId != null) {
			friend.setId(Integer.valueOf(friendId));
		}

		if (id != null) {
			friendDao.setId(Integer.valueOf(id));
		}
		friendDao.setUserId(user);
		friendDao.setFriendId(friend);

		return friendDao;
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
