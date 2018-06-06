package com.stalker.controller;

import java.security.NoSuchAlgorithmException;

import javax.security.auth.login.CredentialException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.stalker.dao.UserDao;
import com.stalker.dao.model.User;
import com.stalker.dto.UserDto;
import com.stalker.filter.AuthenticationFilter;
import com.stalker.filter.Secured;

@Path("/authentication")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticationController {

	@POST
	public Response authenticateUser(final UserDto credentialsDto) {
		try {
			try (final UserDao dao = new UserDao();) {
				final User credentials = credentialsDto.toDao();
				final String newToken = dao.authenticateUser(credentials.getUsername(), credentials.getPassword());
				return Response.ok(new Object() {
					private final String token = newToken; // TODO check this

					public String getToken() {
						return token;
					}
				}).build();
			}
		} catch (final CredentialException e) {
			return Response.status(Response.Status.FORBIDDEN).build();
		} catch (final NoSuchAlgorithmException e) {
			return Response.serverError().build();
		}
	}

	@POST
	@Secured
	@Path("/logout")
	public void disconnectUser(@Context final HttpHeaders httpHeaders) {
		final String token = httpHeaders.getHeaderString(HttpHeaders.AUTHORIZATION)
			.substring(AuthenticationFilter.AUTHENTICATION_SCHEME.length()).trim();
		try (final UserDao dao = new UserDao();) {
			dao.disconnectUser(token);
		}
	}
}
