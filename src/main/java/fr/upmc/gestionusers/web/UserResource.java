package fr.upmc.gestionusers.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;

import fr.upmc.gestionusers.model.AppUser;
import fr.upmc.gestionusers.model.Position;
import fr.upmc.gestionusers.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@CrossOrigin(origins= {"http://localhost", "david.com"}, maxAge = 3600)
@Path("/user")
public class UserResource {
	@Autowired
	private UserRepository userRepository;

	@POST
	@Path("/register")
	@Consumes("application/json")
	public Response creatUser(AppUser user) throws URISyntaxException {
		if(user.getFirstName() == null && "".equals(user.getFirstName())) {
			return Response.status(400).entity("FirstName ne doit pas être null ou vide").build();	
		}
		AppUser verUser = userRepository.findByUsername(user.getUsername());
		if(verUser != null)
			throw new RuntimeException("This user already exists");
		userRepository.save(user);
		return Response.status(201).contentLocation(new URI(user.getFirstName())).build();
	}
	
	@POST
	@Path("/login")
	@Consumes("application/json")
	public Response signUp(AppUser login) throws URISyntaxException {
		
		
		String jwtToken = "";

	    if (login.getUsername() == null || login.getPassword() == null) {
	        throw new RuntimeException("Please fill in username and password");
	    }

	    String username = login.getUsername();
	    String password = login.getPassword();

	    AppUser user = userRepository.findByUsername(username);

	    if (user == null) {
	        throw new RuntimeException("User email not found.");
	    }

	    String pwd = user.getPassword();

	    if (!password.equals(pwd)) {
	        throw new RuntimeException("Invalid login. Please check your name and password.");
	    }

	    jwtToken = Jwts.builder().setSubject(username).claim("roles", "user").setIssuedAt(new Date())
	            .signWith(SignatureAlgorithm.HS256, "secretkey").compact();

	    return Response.status(201).header("authorization", "Bearer " + jwtToken).contentLocation(new URI(user.getUsername())).build();
	}
	
	@POST
	@Path("/user/{id}/position")
	@Consumes("application/json")
	public Response miseAJourPosition(@PathParam("id") Integer id, Position position) throws URISyntaxException {
		position.setLastUpdate(new Date());
		AppUser user = userRepository.findOne(id);
		if(user == null)
			throw new RuntimeException("This user not exists");
		user.setPosition(position);
		userRepository.save(user);
		return Response.status(201).contentLocation(new URI(user.getUsername())).build();
	} 
	
	@GET
	@Path("/user/{id}/friends")
	@Consumes("application/json")
	public List<AppUser> recupererAmis(@PathParam("id") Integer id) throws URISyntaxException {
		AppUser user = userRepository.findOne(id);
		if(user == null)
			throw new RuntimeException("This user not exists");
		
		return user.getFriends();
	} 
	
	
	
}
