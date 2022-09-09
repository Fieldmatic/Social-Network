package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.Repository;
import dto.NewPostDTO;
import enums.Role;
import model.User;
import org.eclipse.jetty.http.HttpStatus;
import spark.Session;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Objects;

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
                    if (user.getRole() == Role.USER) return gson.toJson(this.repo.getPostDAO().getUserFeedPosts(user));
                    else return gson.toJson(this.repo.getPostDAO().getAllPosts());
                }
                else {
                    res.status(401);
                    res.body("User is not logged in !");
                    return res.body();
                }
            });

            get("/getUserPosts", (req, res) -> {
                String username = req.queryParams("username");
                User user = this.repo.getUserDAO().getUserByUsername(username);
                res.type("application/json");
                if (user != null) {
                    return gson.toJson(this.repo.getPostDAO().getUserPosts(user));
                }
                else {
                    res.status(401);
                    res.body("Username" + username + " is not valid!");
                    return res.body();
                }
            });

            post("/deletePost", (req, res) ->
            {
                Session ss = req.session(true);
                User user = ss.attribute("user");
                Integer postId = Integer.parseInt(req.queryParams("postId"));
                res.type("application/json");

                if (user != null) {
                    model.Post postForDeleting = this.repo.getPostDAO().deletePost(postId);
                    if (postForDeleting == null) { res.status(400); res.body("Post is incorrect.");}
                    else {
                        this.repo.getCommentDAO().deletePostComments(postForDeleting);
                        res.status(200);
                        res.body("Success");
                    }
                } else {
                    res.status(401);
                    res.body("User is not logged in !");
                }
                return res.body();
            });

            put("/leaveLikeOrDislike", (req, res) -> {
                res.type("application/json");
                Integer postId = Integer.parseInt(req.queryParams("postId"));
                String likeOrDislike = req.queryParams("likeOrDislike");
                Session ss = req.session(true);
                User user = ss.attribute("user");
                if (user != null) {
                    if (likeOrDislike.equals("like")) this.repo.getPostDAO().leaveLike(postId, user.getUsername());
                    else this.repo.getPostDAO().leaveDislike(postId, user.getUsername());
                    res.status(200);
                    res.body("Success");
                } else {
                    res.status(401);
                    res.body("User is not logged in !");
                }
                return res.body();
            });
        });
    }
}
