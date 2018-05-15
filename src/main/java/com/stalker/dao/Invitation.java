package com.stalker.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "INVITATION")
public class Invitation {
	private int id;
	private int userid;
	private int friendId;
	
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	public int getId() {
		return id;
	}
	
	public void setId(final int id) {
		this.id = id;
	}
	
	public int getUserid() {
		return userid;
	}
	
	public void setUserid(final int userid) {
		this.userid = userid;
	}
	
	public int getFriendId() {
		return friendId;
	}
	
	public void setFriendId(final int friendId) {
		this.friendId = friendId;
	}
}
