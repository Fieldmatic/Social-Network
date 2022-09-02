package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.Repository;
import dto.NewPostDTO;
import model.User;
import org.eclipse.jetty.http.HttpStatus;
import spark.Session;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Base64;

import static spark.Spark.*;

public class Post {
    private final Repository repo;
    private static final GsonBuilder builder = new GsonBuilder();
    private static final Gson gson = builder.create();

    public Post(Repository repo){ this.repo = repo;}

    public void init () {
        path("/post", () -> {
            post("/add", (req,res) ->
            {
                res.type("application/json");
                Session ss = req.session(true);
                User user = ss.attribute("user");

                if (user != null) {
                    NewPostDTO postDTO = gson.fromJson(req.body(),NewPostDTO.class);
                    if (postDTO == null) { res.status(400); res.body("Post not created properly");}
                    else
                    {
                        if (!postDTO.getPictureName().isEmpty()) {
                            byte[] fileData = Base64.getDecoder().decode(postDTO.getPicture().split(",")[1]);
                            String path = "./data/images/" + user.getUsername() + "/" + postDTO.getPictureName();
                            postDTO.setPictureName(path);
                            File file = new File(path);
                            file.getParentFile().mkdirs();
                            try (OutputStream stream = new FileOutputStream(file,false)) {
                                stream.write(fileData);
                            }
                        }
                        repo.getPostDAO().createFromDTO(postDTO, user);
                        res.status(200);
                        res.body("Success");
                    }

                }
                else {
                    res.status(401);
                    res.body("User is not logged in !");
                }
                return res.body();
            });

            get("/getUserFeedPosts", (req, res) -> {
                res.type("application/json");
                Session ss = req.session(true);
                User user = ss.attribute("user");
                if (user != null) {
                    return gson.toJson(this.repo.getPostDAO().getUserFeedPosts(user));
                }
                else {
                    res.status(401);
                    res.body("User is not logged in !");
                    return res.body();
                }
            });

            get("/getUserPosts", (req, res) -> {
                res.type("application/json");
                Session ss = req.session(true);
                User user = ss.attribute("user");
                if (user != null) {
                    return gson.toJson(this.repo.getPostDAO().getUserPosts(user));
                }
                else {
                    res.status(401);
                    res.body("User is not logged in !");
                    return res.body();
                }
            });
        });
    }
}
