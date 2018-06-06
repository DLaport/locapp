package com.stalker.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.stalker.dao.model.Position;
import com.stalker.dao.model.User;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PositionDto {
	private String id;
	private String user;
	private String username;
	private String firstName;
	private String lastName;
	private String latitude;
	private String longitude;
	private Date lastUpdate;

	public PositionDto() {
	}

	public PositionDto(final int id, final User user, final double latitude, final double longitude, final Date lastUpdate) {
		this.id = Integer.toString(id);
		this.user = Integer.toString(user.getId());
		username = user.getUsername();
		firstName = user.getFirstName();
		lastName = user.getLastName();
		this.latitude = Double.toString(latitude);
		this.longitude = Double.toString(longitude);
		this.lastUpdate = lastUpdate;
	}

	public Position toDao() {
		final Position position = new Position();
		final User user = new User();
		if (this.user != null) {
			user.setId(Integer.valueOf(this.user));
		}

		position.setUser(user);
		if (id != null) {
			position.setId(id != null ? Integer.valueOf(id) : null);
		}
		if (lastUpdate != null) {
			position.setLastUpdate(lastUpdate);
		}
		if (latitude != null) {
			position.setLatitude(Double.valueOf(latitude));
		}
		if (longitude != null) {
			position.setLongitude(Double.valueOf(longitude));
		}
		return position;
	}

	public String getId() {
		return id;
	}

	public String getUser() {
		return user;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public String getUsername() {
		return username;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
}
