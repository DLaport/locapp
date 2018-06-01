package com.stalker.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class StandardExceptionMapper
implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(final Exception exception) {
		exception.printStackTrace();
		return Response.serverError().build();
	}
}
