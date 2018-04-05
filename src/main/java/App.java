import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.RuntimeDelegate;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class App {
	public static void main(final String[] args)
	throws IOException, InterruptedException {
		startServer();
		System.out.println("Server started.");
		Thread.currentThread().join();
	}
	
	/**
	 * Starts the lightweight HTTP server serving the JAX-RS application.
	 *
	 * @return new instance of the lightweight HTTP server
	 * @throws IOException
	 */
	private static HttpServer startServer()
	throws IOException {
		// create a new server listening at port 8080
		final HttpServer server = HttpServer.create(new InetSocketAddress(getBaseURI().getPort()), 0);
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				server.stop(0);
			}
		}));

		// create a handler wrapping the JAX-RS application
		final HttpHandler handler = RuntimeDelegate.getInstance().createEndpoint(new JaxRsApplication(), HttpHandler.class);

		// map JAX-RS handler to the server root
		server.createContext(getBaseURI().getPath(), handler);

		// start the server
		server.start();

		return server;
	}
	
	private static int getPort(final int defaultPort) {
		final String port = System.getProperty("jersey.config.test.container.port");
		if (null != port) {
			try {
				return Integer.parseInt(port);
			} catch (final NumberFormatException e) {
				System.out.println("Value of jersey.config.test.container.port property" + //
				" is not a valid positive integer [" + port + "]." + " Reverting to default [" + defaultPort + "].");
			}
		}
		return defaultPort;
	}

	/**
	 * Gets base {@link URI}.
	 *
	 * @return base {@link URI}.
	 */
	public static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost/").port(getPort(8080)).build();
	}
}
