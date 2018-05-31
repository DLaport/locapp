package com.stalker.dao.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "INVITATION", uniqueConstraints = @UniqueConstraint(columnNames = {
	"USER_ID",
	"FRIEND_ID"
}))
public class Invitation {
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
