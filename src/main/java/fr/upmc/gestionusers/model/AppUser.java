package fr.upmc.gestionusers.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class AppUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column
	private String username;
	@Column
	private String firstName;
	@Column
	private String lastname;
	@Column
	private String email;
	@Column
	private String password;
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "appUser", cascade = CascadeType.ALL)
	private Position position;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "friend", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "friendId"))
	List<AppUser> friends = null;

//	@ManyToMany(mappedBy = "friend")
//	protected List<AppUser> befriended = null;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public List<AppUser> getFriends() {
		return friends;
	}

	public void setFriends(List<AppUser> friends) {
		this.friends = friends;
	}

//	public List<AppUser> getBefriended() {
//		return befriended;
//	}
//
//	public void setBefriended(List<AppUser> befriended) {
//		this.befriended = befriended;
//	}

}
