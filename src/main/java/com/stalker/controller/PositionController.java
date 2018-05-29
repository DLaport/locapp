package com.stalker.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.stalker.dao.PositionDao;
import com.stalker.dao.model.Position;

@Secured
@Path("/user/{id}/position")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PositionController {

	@PUT
	public Position updateUserPosition(@PathParam("id") final String userId, final Position position) {
		try (PositionDao dao = new PositionDao();) {
			return dao.updatePosition(userId, position);
		}
	}
}
