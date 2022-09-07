import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;
import static spark.Spark.webSocket;

import java.io.File;
import java.io.IOException;
import java.security.Key;

import chat.ChatWebSocketHandler;
import dao.Repository;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import services.*;

public class Main {
	static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	static Repository repository = new Repository();
	static Authentication authentication = new Authentication(repository);

	static Post post = new Post(repository);
	static User user = new User(repository);
	static CommentService commentService = new CommentService(repository);

	static MessageService messageService = new MessageService(repository);
	static ChatWebSocketHandler chatWebSocketHandler = new ChatWebSocketHandler(repository);

	public static void main(String[] args) throws IOException {
		repository.load();
		port(8000);
		
		staticFiles.externalLocation(new File("./static").getCanonicalPath());
		webSocket("/chat", chatWebSocketHandler);

		authentication.init();
		post.init();
		user.init();
		commentService.init();
		messageService.init();

	}

}
