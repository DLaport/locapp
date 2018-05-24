package fr.upmc.gestionusers.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import fr.upmc.gestionusers.web.SecureResource;
import fr.upmc.gestionusers.web.UserResource;

@Component
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig() {
		register(UserResource.class);
		register(SecureResource.class);
	}
}
