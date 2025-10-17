package browser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.staticFiles;

/**
 * Created by hug.
 */
public class NgordnetServer {

	private static final Logger log = LoggerFactory.getLogger(NgordnetServer.class);

	public void register(String URL, NgordnetQueryHandler nqh) {
		get(URL, nqh);
	}

	public void startUp() {
		try {
			URI resource = Objects.requireNonNull(NgordnetServer.class.getResource("/static")).toURI();
			Path fileName = Paths.get(resource).toAbsolutePath();
			staticFiles.externalLocation(fileName.toString());
		} catch (URISyntaxException | NullPointerException e) {
			log.error(e.getMessage());
		}

		/* Allow for all origin requests (since this is not an authenticated server, we do not
		 * care about CSRF).  */
		before((request, response) -> {
			response.header("Access-Control-Allow-Origin", "*");
			response.header("Access-Control-Request-Method", "*");
			response.header("Access-Control-Allow-Headers", "*");
		});
	}
}
