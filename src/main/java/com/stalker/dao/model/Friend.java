package com.stalker.dao.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "FRIEND")
public class Friend {
	private int id;
	private User userId;
	private User friendId;

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	public User getUserId() {
		return userId;
	}

	public void setUserId(final User userid) {
		userId = userid;
	}

	@ManyToOne
	@JoinColumn(name = "FRIEND_ID")
	public User getFriendId() {
		return friendId;
	}

	public void setFriendId(final User friendId) {
		this.friendId = friendId;
	}
}
