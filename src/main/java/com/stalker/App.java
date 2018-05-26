package com.stalker;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.stalker.controller.AuthenticationController;
import com.stalker.controller.AuthenticationFilter;
import com.stalker.controller.FriendsController;
import com.stalker.controller.InvitationController;
import com.stalker.controller.PositionController;
import com.stalker.controller.UserController;

public class App {
	private static final URI BASE_URI = URI.create("http://localhost:8080/");
	private static final Class<?>[] resources = {
		AuthenticationController.class,
		AuthenticationFilter.class,
		UserController.class,
		FriendsController.class,
		PositionController.class,
		InvitationController.class
	};

	public static void main(final String[] args) {
		try {
			final ResourceConfig resourceConfig = new ResourceConfig(resources);
			final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resourceConfig, false);

			Runtime.getRuntime().addShutdownHook(new Thread(() -> server.shutdown()));
			server.start();
			Thread.currentThread().join();
		} catch (final Exception ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, "", ex);
		}
	}
}
