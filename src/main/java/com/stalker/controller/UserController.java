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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.stalker.dao.Friend;
import com.stalker.dao.Invitation;
import com.stalker.dao.Position;
import com.stalker.dao.User;
import com.stalker.dao.UserDao;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {
	private final UserDao userDao = new UserDao();

	@POST
	@Path("/user")
	public User createUser(final User user) {
		userDao.createUser(user);
		return user; // TODO: return less info
	}
	
	@GET
	@Path("/users")
	public List<User> getUsers(@QueryParam("search") final String searchText) {
		return userDao.getUsers(searchText);
	}
	
	@PUT
	@Path("/user/{id}/position")
	public Response updateUserPosition(@PathParam("id") final String userId, final Position position) {
		throw new UnsupportedOperationException("Not yet implemented.");
	}
	
	@POST
	@Path("/user/{id}/invitation")
	public Invitation sendInvitation(@PathParam("id") final String userId, final Invitation invitation) {
		throw new UnsupportedOperationException("Not yet implemented.");
	}
	
	@GET
	@Path("/user/{id}/invitations")
	public List<Invitation> getInvitations(@PathParam("id") final String userId) {
		throw new UnsupportedOperationException("Not yet implemented.");
	}
	
	@PUT
	@Path("/user/{id}/invitation")
	public Response acceptInvitation(@PathParam("id") final String userId, final Invitation invitation) {
		throw new UnsupportedOperationException("Not yet implemented.");
	}
	
	@GET
	@Path("/user/{id}/friends")
	public List<Friend> getFriends(@PathParam("id") final String userId) {
		throw new UnsupportedOperationException("Not yet implemented.");
	}
	
	@GET
	@Path("/user/{id}/friends/{friendId}")
	public Friend getFriend(@PathParam("id") final String userId, @PathParam("friendId") final String friendId) {
		throw new UnsupportedOperationException("Not yet implemented.");
	}
	
	@DELETE
	@Path("/user/{id}/friends/{friendId}")
	public Response deleteFriend(@PathParam("id") final String userId, @PathParam("friendId") final String friendId) {
		throw new UnsupportedOperationException("Not yet implemented.");
	}
	
}
