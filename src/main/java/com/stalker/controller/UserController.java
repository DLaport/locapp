package com.stalker.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.stalker.dao.UserDao;
import com.stalker.dao.model.User;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {
	private final UserDao userDao = new UserDao(); // TODO: close this

	@POST
	@Path("/user")
	public User createUser(final User user) {
		userDao.createUser(user);
		return user; // TODO: return less info
	}

	@GET
	@Secured
	@Path("/users")
	public List<User> getUsers(@QueryParam("search") final String searchText) {
		return userDao.searchUsers(searchText);
	}
}
