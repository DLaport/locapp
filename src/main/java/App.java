import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class App {
	private static final URI BASE_URI = URI.create("http://localhost:8080/");
	
	public static void main(final String[] args) {
		try {
			final ResourceConfig resourceConfig = new ResourceConfig(HelloWorldResource.class);
			final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resourceConfig, false);
			
			Runtime.getRuntime().addShutdownHook(new Thread(() -> server.shutdown()));
			server.start();
			Thread.currentThread().join();
		} catch (IOException | InterruptedException ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, "", ex);
		}
		
	}
}
