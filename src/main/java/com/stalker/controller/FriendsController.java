package com.stalker.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.stalker.dao.model.Friend;

@Secured
@Path("/user/{id}/friends")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FriendsController {
	@GET
	public List<Friend> getFriends(@PathParam("id") final String userId) {
		throw new UnsupportedOperationException("Not yet implemented.");
	}

	@GET
	@Path("/{friendId}")
	public Friend getFriend(@PathParam("id") final String userId, @PathParam("friendId") final String friendId) {
		throw new UnsupportedOperationException("Not yet implemented.");
	}

	@DELETE
	@Path("/{friendId}")
	public Response deleteFriend(@PathParam("id") final String userId, @PathParam("friendId") final String friendId) {
		throw new UnsupportedOperationException("Not yet implemented.");
	}
}
