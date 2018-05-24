package fr.upmc.gestionusers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import fr.upmc.gestionusers.model.AppUser;
@Component
public interface UserRepository  extends JpaRepository<AppUser, Integer> {
	@Query("select u from AppUser u where u.username = ?1")
	public List<AppUser> findUser(String username);
	
	
	
	AppUser findByUsername(String username);
}
