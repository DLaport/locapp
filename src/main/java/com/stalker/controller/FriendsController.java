package com.stalker.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.stalker.dao.FriendDao;
import com.stalker.dao.model.Friend;
import com.stalker.dao.model.User;

@Secured
@Path("/user/{id}/friends")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FriendsController {
	@GET
	public List<User> getFriends(@PathParam("id") final String userId) {
		try (FriendDao friendDao = new FriendDao();) {
			return friendDao.getFriends(Integer.valueOf(userId));
		}
	}

	@GET
	@Path("/{friendId}")
	public Friend getFriend(@PathParam("id") final String userId, @PathParam("friendId") final String friendId) {
		// TODO: check if we need this
		throw new UnsupportedOperationException("Not yet implemented.");
	}

	@DELETE
	@Path("/{friendId}")
	public void deleteFriend(@PathParam("id") final String userId, @PathParam("friendId") final String friendId) {
		try (FriendDao friendDao = new FriendDao();) {
			friendDao.deleteFriend(Integer.valueOf(userId), Integer.valueOf(friendId));
		}
	}
}
