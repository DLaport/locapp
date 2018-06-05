package com.stalker.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper
implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(final Throwable throwable) {
		if (throwable instanceof WebApplicationException) {
			return ((WebApplicationException) throwable).getResponse();
		}
		throwable.printStackTrace();
		return Response.serverError().build();
	}
}
