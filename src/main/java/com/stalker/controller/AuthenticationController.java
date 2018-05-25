package com.stalker.controller;

import java.util.UUID;

import javax.security.auth.login.CredentialException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.stalker.controller.authentication.Secured;
import com.stalker.dao.UserDao;
import com.stalker.dao.model.User;

@Path("/authentication")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticationController {
	
	@POST
	public Response authenticateUser(final User credentials) {
		try {
			try (final UserDao dao = new UserDao();) {
				dao.authenticateUser(credentials.getUsername(), credentials.getPassword());
				final String token = UUID.randomUUID().toString();
				return Response.ok(token).build();
			}
		} catch (final CredentialException e) {
			return Response.status(Response.Status.FORBIDDEN).build();
		} catch (final Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@POST
	@Secured
	@Path("/logout")
	public void disconnectUser() {
		throw new UnsupportedOperationException("Not yet implemented.");
	}
}
