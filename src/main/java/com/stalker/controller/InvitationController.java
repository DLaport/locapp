package com.stalker.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.stalker.controller.authentication.Secured;
import com.stalker.dao.model.Invitation;

@Secured
@Path("/user/{id}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InvitationController {
	@POST
	@Path("/invitation")
	public Invitation sendInvitation(@PathParam("id") final String userId, final Invitation invitation) {
		throw new UnsupportedOperationException("Not yet implemented.");
	}

	@GET
	@Path("/invitations")
	public List<Invitation> getInvitations(@PathParam("id") final String userId) {
		throw new UnsupportedOperationException("Not yet implemented.");
	}

	@PUT
	@Path("/invitation")
	public Response acceptInvitation(@PathParam("id") final String userId, final Invitation invitation) {
		throw new UnsupportedOperationException("Not yet implemented.");
	}
}
