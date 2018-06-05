package com.stalker.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import com.stalker.dao.PositionDao;
import com.stalker.dao.model.Position;
import com.stalker.filter.Secured;

@Secured
@Path("/user/{id}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PositionController {
	@Context
	SecurityContext securityContext;

	@PUT
	@Path("/position")
	public Position updateUserPosition(@PathParam("id") final String userId, final Position position) {
		try (PositionDao dao = new PositionDao(securityContext);) {
			return dao.updatePosition(Integer.valueOf(userId), position);
		}
	}

	@GET
	@Path("/positions/friends")
	public List<Position> getFriendsPositions(@PathParam("id") final String userId) {
		try (PositionDao dao = new PositionDao(securityContext);) {
			return dao.getFriendsPositions(Integer.valueOf(userId));
		}
	}
}
