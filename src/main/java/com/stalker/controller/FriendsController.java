package com.stalker.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import com.stalker.dao.FriendDao;
import com.stalker.dao.model.User;
import com.stalker.filter.Secured;

@Secured
@Path("/user/{id}/friends")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FriendsController {
	@Context
	SecurityContext securityContext;

	@GET
	public List<User> getFriends(@PathParam("id") final String userId) {
		try (FriendDao friendDao = new FriendDao(securityContext);) {
			return friendDao.getFriends(Integer.valueOf(userId));
		}
	}

	@DELETE
	@Path("/{friendId}")
	public void deleteFriend(@PathParam("id") final String userId, @PathParam("friendId") final String friendId) {
		try (FriendDao friendDao = new FriendDao(securityContext);) {
			friendDao.deleteFriend(Integer.valueOf(userId), Integer.valueOf(friendId));
		}
	}
}
