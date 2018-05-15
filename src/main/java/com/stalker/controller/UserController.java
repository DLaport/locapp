package com.stalker.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.stalker.dao.Friend;
import com.stalker.dao.Invitation;
import com.stalker.dao.Position;
import com.stalker.dao.User;
import com.stalker.dao.UserDao;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {
	
	@POST
	public User createUser(final User user) {
		final UserDao dao = new UserDao();
		dao.createUser(user);
		return user;
	}
	
	@GET
	@Path("/{search}")
	public List<User> getUsers(@PathParam("search") final String searchText) {
		throw new UnsupportedOperationException("Not yet implemented.");
	}
	
	@PUT
	@Path("/{id}/position")
	public void updateUserPosition(@PathParam("id") final String userId, final Position position) {
		throw new UnsupportedOperationException("Not yet implemented.");
	}
	
	@POST
	@Path("/{id}/invitation")
	public Invitation sendInvitation(@PathParam("id") final String userId, final Invitation invitation) {
		throw new UnsupportedOperationException("Not yet implemented.");
	}
	
	@GET
	@Path("/{id}/invitations")
	public List<Invitation> getInvitations(@PathParam("id") final String userId) {
		throw new UnsupportedOperationException("Not yet implemented.");
	}
	
	@PUT
	@Path("/{id}/invitation")
	public void acceptInvitation(@PathParam("id") final String userId, final Invitation invitation) {
		throw new UnsupportedOperationException("Not yet implemented.");
	}
	
	@GET
	@Path("/{id}/friends")
	public List<Friend> getFriends(@PathParam("id") final String userId) {
		throw new UnsupportedOperationException("Not yet implemented.");
	}
	
	@GET
	@Path("/{id}/friends/{friendId}")
	public Friend getFriend(@PathParam("id") final String userId, @PathParam("friendId") final String friendId) {
		throw new UnsupportedOperationException("Not yet implemented.");
	}
	
	@DELETE
	@Path("/{id}/friends/{friendId}")
	public void deleteFriend(@PathParam("id") final String userId, @PathParam("friendId") final String friendId) {
		throw new UnsupportedOperationException("Not yet implemented.");
	}
	
}
