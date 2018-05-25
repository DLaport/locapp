package com.stalker.controller.authentication;

import java.io.IOException;
import java.security.Principal;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import com.stalker.dao.UserDao;
import com.stalker.dao.model.User;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter
implements ContainerRequestFilter {
	private static final String AUTHENTICATION_SCHEME = "Bearer";
	
	@Override
	public void filter(final ContainerRequestContext requestContext) {
		final String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		
		// Validate the authorization header
		if (authorizationHeader == null || !authorizationHeader.toLowerCase().startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ")) {
			abortWithUnauthorized(requestContext);
			return;
		}
		
		// Extract the token
		final String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();

		try {
			// If the token is valid, get the user. Otherwise, throw an Exception.
			final User user = validateToken(token);
			
			updateSecurityContext(requestContext, user);
		} catch (final IOException e) {
			requestContext.abortWith(Response.serverError().build()); // TODO: either add a message or don't close the dao
		} catch (final Exception e) {
			abortWithUnauthorized(requestContext);
		}
	}

	private void abortWithUnauthorized(final ContainerRequestContext requestContext) {
		try (
		// Build unautorized response
		final Response response = Response.status(Response.Status.UNAUTHORIZED)
		.header(HttpHeaders.WWW_AUTHENTICATE, AUTHENTICATION_SCHEME).build();) {
			
			requestContext.abortWith(response);
		}
	}

	private User validateToken(final String token)
	throws Exception {
		try (final UserDao userDao = new UserDao();) {
			return userDao.getUserByToken(token).orElseThrow(Exception::new);
		}
	}

	private void updateSecurityContext(final ContainerRequestContext requestContext, final User user) {
		final SecurityContext securityContext = requestContext.getSecurityContext();
		requestContext.setSecurityContext(new SecurityContext() {
			@Override
			public boolean isUserInRole(final String role) {
				return true;
			}

			@Override
			public boolean isSecure() {
				return securityContext.isSecure();
			}

			@Override
			public Principal getUserPrincipal() {
				return () -> user.getUsername();
			}

			@Override
			public String getAuthenticationScheme() {
				return AUTHENTICATION_SCHEME;
			}
		});
	}
}
