package services;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;
import static spark.Spark.webSocket;
import spark.Session;

import java.io.File;
import java.io.IOException;
import java.security.Key;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class App {
	static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	public static void main(String[] args) throws IOException {
		port(8000);
		
		staticFiles.externalLocation(new File("./static").getCanonicalPath());
		
		get("/rest/demo/test", (req, res) -> {
			return "Works";
		});
		

	}

}
