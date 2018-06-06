package com.stalker.filter;

import java.security.Principal;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import com.stalker.dao.UserDao;
import com.stalker.dao.model.User;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter
implements ContainerRequestFilter {
	public static final String AUTHENTICATION_SCHEME = "Bearer";

	/**
	 * Checks if the Authorization Header of the request containes a valid token.
	 * When the header is invalid (i.e, no token given / token not found in db) the request is aborted
	 * and a Response is sent back to the client with the status code 401 (Unauthorized).
	 */
	@Override
	public void filter(final ContainerRequestContext requestContext) {
		final String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		// If the header is valid, get the token. Otherwise, throw an Exception.
		final String token = validateHeader(authorizationHeader);

		// If the token is valid, get the user. Otherwise, throw an Exception.
		final User user = validateToken(token);

		// The username will be kept in the security context.
		updateSecurityContext(requestContext, user);
	}

	private String validateHeader(final String authorizationHeader) {
		if ((authorizationHeader == null) || !authorizationHeader.toLowerCase().startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ")) {
			throw new NotAuthorizedException("Invalid authorization header.", AUTHENTICATION_SCHEME);
		}
		return authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
	}

	private User validateToken(final String token) {
		try (final UserDao userDao = new UserDao();) {
			return userDao.getUserByToken(token)
				.orElseThrow(() -> new NotAuthorizedException("Invalid token.", AUTHENTICATION_SCHEME));
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
