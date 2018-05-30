package com.stalker.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.stalker.dao.InvitationDao;
import com.stalker.dao.model.Invitation;

@Secured
@Path("/user/{id}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InvitationController {

	@POST
	@Path("/invitation")
	public Response sendInvitation(@PathParam("id") final String userId, @Context final UriInfo uriInfo, final String json) {
		try {
			final ObjectNode node = new ObjectMapper().readValue(json, ObjectNode.class);
			final String friendId = node.has("friendId") ? node.get("friendId").asText() : "-1";
			try (InvitationDao invitationDao = new InvitationDao()) {
				return invitationDao.createInvitation(Integer.valueOf(userId), Integer.valueOf(friendId))
					.map(invitation -> {
						final URI uri = uriInfo.getAbsolutePathBuilder().path(Integer.toString(invitation.getId())).build();
						return Response.created(uri).entity(invitation).build();
					}).orElseGet(() -> Response.status(Response.Status.BAD_REQUEST).build());
			}
		} catch (final IOException e) {
			throw new BadRequestException(e);
		}
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

	@DELETE
	@Path("/invitation/{invitationId}")
	public void deleteInvitation(@PathParam("invitationId") final String invitationId) {
		try (InvitationDao invitationDao = new InvitationDao()) {
			invitationDao.deleteInvitation(invitationId);
		}
	}
}
