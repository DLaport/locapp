package com.stalker.dao.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import com.stalker.dto.PositionDto;

@Entity
@Table(name = "POSITION")
public class Position {
	private int id;
	private User user;
	private double latitude;
	private double longitude;
	private Date lastUpdate;

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@OneToOne
	@JoinColumn(name = "USER_ID")
	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	@NotNull
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(final double latitude) {
		this.latitude = latitude;
	}

	@NotNull
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(final double longitude) {
		this.longitude = longitude;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_DATE")
	@UpdateTimestamp
	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(final Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public PositionDto toDto() {
		return new PositionDto(id, user, latitude, longitude, lastUpdate);
	}
}
