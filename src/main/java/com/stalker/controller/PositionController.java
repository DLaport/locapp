package com.stalker.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.stalker.controller.authentication.Secured;
import com.stalker.dao.model.Position;

@Secured
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PositionController {
	@PUT
	@Path("/user/{id}/position")
	public Response updateUserPosition(@PathParam("id") final String userId, final Position position) {
		throw new UnsupportedOperationException("Not yet implemented.");
	}
}
