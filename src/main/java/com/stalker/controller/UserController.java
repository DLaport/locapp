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

	@POST
	@Path("/user")
	public User createUser(final User user) {
		try (UserDao userDao = new UserDao();) {
			userDao.createUser(user);
			return user;
		}
	}

	@GET
	@Secured
	@Path("/users")
	public List<User> getUsers(@QueryParam("search") final String searchText) {
		try (UserDao userDao = new UserDao();) {
			return userDao.searchUsers(searchText);
		}
	}
}
