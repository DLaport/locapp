package com.stalker.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper
implements ExceptionMapper<Throwable> {

	/**
	 * Maps a Throwable to a jax-rs Response.
	 * By default, all errors are mapped to 'Server Error' (500).
	 * When the throwable is an instance of WebApplicationException,
	 * the appropriate response is extracted directly from the exception.
	 * Instances of IllegalArgumentExceptions thrown on purpose are mapped to 'Unauthorized' (401).
	 *
	 * @return Jax-Rs response sent to the client.
	 */
	@Override
	public Response toResponse(final Throwable throwable) {
		if (throwable instanceof WebApplicationException) {
			return ((WebApplicationException) throwable).getResponse();
		} else if (throwable instanceof IllegalArgumentException) {
			if (throwable.getMessage().equals("unauthorized")) {
				return Response.status(Response.Status.UNAUTHORIZED).build();
			}
		}
		throwable.printStackTrace();
		return Response.serverError().build();
	}
}
