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

import fr.upmc.gestionusers.model.AppUser;
import fr.upmc.gestionusers.model.Position;
import fr.upmc.gestionusers.repository.UserRepository;


@Path("/users")
public class UserResource {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
		
	@GET
	@Path("/user")
    @Produces("application/json")
    public List<AppUser> getAllUsers() {
		return userRepository.findAll();
    }
	
	
	@GET
	@Path("/user/{search}")
	@Produces("application/json")
	public Response findUser(@PathParam("search") String username) throws URISyntaxException {
		List<AppUser> users = userRepository.findUser(username);
		if(users.isEmpty()) {
            return Response.status(404).build();
        }
		return Response
                .status(200)
                .entity(users.get(0))
                .contentLocation(new URI("/user-management/"+username)).build();
	}

	@POST
	@Path("/user")
	@Consumes("application/json")
	public Response creatUser(AppUser user) throws URISyntaxException {
		if(user.getFirstName() == null && "".equals(user.getFirstName())) {
			return Response.status(400).entity("FirstName ne doit pas être null ou vide").build();	
		}
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		AppUser verUser = userRepository.findByUsername(user.getUsername());
		if(verUser != null)
			throw new RuntimeException("This user already exists");
		userRepository.save(user);
		return Response.status(201).contentLocation(new URI(user.getFirstName())).build();
	}
	
	@POST
	@Path("/authentication")
	@Consumes("application/json")
	public Response signUp(AppUser user) throws URISyntaxException {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return Response.status(201).contentLocation(new URI(user.getUsername())).build();
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
