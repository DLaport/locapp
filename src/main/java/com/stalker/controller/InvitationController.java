package com.stalker.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import com.stalker.dao.FriendDao;
import com.stalker.dao.InvitationDao;
import com.stalker.dao.model.Friend;
import com.stalker.dao.model.Invitation;
import com.stalker.dto.FriendDto;
import com.stalker.dto.InvitationDto;
import com.stalker.filter.Secured;

@Secured
@Path("/user/{id}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InvitationController {
	@Context
	SecurityContext securityContext;

	@POST
	@Path("/invitation")
	public Response sendInvitation(@PathParam("id") final String userId, @Context final UriInfo uriInfo, final InvitationDto invitationDto) {
		try (InvitationDao invitationDao = new InvitationDao(securityContext)) {
			final Invitation invitation = invitationDto.toDao();
			return invitationDao.createInvitation(Integer.valueOf(userId), Integer.valueOf(invitation.getFriendId().getId()))
				.map(invite -> {
					final URI uri = uriInfo.getAbsolutePathBuilder().path(Integer.toString(invite.getId())).build();
					return Response.created(uri).entity(invite.toDto()).build();
				}).orElseGet(() -> Response.status(Response.Status.BAD_REQUEST).build());
		}
	}

	@GET
	@Path("/invitations")
	public List<InvitationDto> getInvitations(@PathParam("id") final String userId) {
		try (InvitationDao invitationDao = new InvitationDao(securityContext);) {
			return invitationDao.getInvitations(Integer.valueOf(userId))
				.stream().map(Invitation::toDto).collect(Collectors.toList());
		}
	}

	@PUT
	@Path("/invitation/{invitationId}")
	public FriendDto acceptInvitation(@PathParam("id") final String id, @PathParam("invitationId") final String invitationId) {
		try (FriendDao friendDao = new FriendDao(securityContext);) {
			final Friend friend = friendDao.addFriend(Integer.valueOf(invitationId), Integer.valueOf(id)).orElseThrow(BadRequestException::new);
			deleteInvitation(id, invitationId);
			return friend.toDto();
		}
	}

	@DELETE
	@Path("/invitation/{invitationId}")
	public void deleteInvitation(@PathParam("id") final String id, @PathParam("invitationId") final String invitationId) {
		try (InvitationDao invitationDao = new InvitationDao(securityContext)) {
			invitationDao.deleteInvitation(Integer.valueOf(id), invitationId);
		}
	}
}
