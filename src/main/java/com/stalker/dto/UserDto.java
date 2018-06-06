package com.stalker.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.stalker.dao.model.User;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
	private String id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String token;

	public UserDto(final int id, final String username, final String password, final String firstName, final String lastName, final String email, final String token) {
		this.id = Integer.toString(id);
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.token = token;
	}

	public User toDao() {
		final User user = new User();
		if (id != null) {
			user.setId(Integer.valueOf(id));
		}
		user.setUsername(username);
		user.setPassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setToken(token);
		return user;
	}

	public UserDto() {
	}

	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(final String token) {
		this.token = token;
	}
}
