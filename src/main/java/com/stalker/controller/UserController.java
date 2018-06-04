package com.stalker.controller;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.stalker.dao.UserDao;
import com.stalker.dao.model.User;
import com.stalker.filter.Secured;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

	@POST
	@Path("/user")
	public Response createUser(final User user, @Context final UriInfo uriInfo) {
		try (UserDao userDao = new UserDao();) {
			userDao.createUser(user);
			final URI uri = uriInfo.getAbsolutePathBuilder().path(Integer.toString(user.getId())).build();
			return Response.created(uri).entity(user).build();
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
