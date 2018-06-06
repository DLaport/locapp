package com.stalker.controller;

import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import com.stalker.dao.UserDao;
import com.stalker.dao.model.User;
import com.stalker.dto.UserDto;
import com.stalker.filter.Secured;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

	@POST
	@Path("/user")
	public Response createUser(final UserDto userDto, @Context final UriInfo uriInfo) {
		try (UserDao userDao = new UserDao();) {
			final User user = userDao.createUser(userDto.toDao());
			final URI uri = uriInfo.getAbsolutePathBuilder().path(Integer.toString(user.getId())).build();
			return Response.created(uri).entity(user.toDto()).build();
		} catch (final NoSuchAlgorithmException e) {
			return Response.serverError().build();
		}
	}

	@GET
	@Secured
	@Path("/users")
	public List<UserDto> getUsers(@QueryParam("search") final String searchText, @Context final SecurityContext securityContext) {
		try (UserDao userDao = new UserDao();) {
			return userDao.searchUsers(securityContext.getUserPrincipal().getName(), searchText)
				.stream().map(User::toDto).collect(Collectors.toList());
		}
	}
}
