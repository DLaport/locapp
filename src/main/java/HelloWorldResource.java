import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Example that demonstrates how to create endpoints with Jersey.
 */
@Path("/hello")
public class HelloWorldResource {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getHello() {
		return "Hello World!";
	}
}
