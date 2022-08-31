package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.Repository;
import dto.NewPostDTO;
import model.Comment;
import model.User;
import spark.Session;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;

import static spark.Spark.path;
import static spark.Spark.post;

public class CommentService {
    private final Repository repo;
    private static final GsonBuilder builder = new GsonBuilder();
    private static final Gson gson = builder.create();

    public CommentService(Repository repo) {this.repo = repo;}

    public void init() {
        path("/comment", () -> {
            post("/add", (req,res) ->
            {
                Integer postId = Integer.parseInt(req.queryParams("postId"));
                String commentText = req.queryParams("commentText");
                Session ss = req.session(true);
                User user = ss.attribute("user");
                res.type("application/json");

                if (user != null) {
                    repo.getCommentDAO().createComment(user.getUsername(), postId, commentText);
                    res.status(200);
                    res.body("Success");
                }
                else {
                    res.status(401);
                    res.body("User is not logged in !");
                }
                return res.body();
            });
        });
    }
}
