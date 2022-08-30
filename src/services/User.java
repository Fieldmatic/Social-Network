package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.Repository;
import dto.NewPostDTO;
import model.FriendRequest;
import spark.Session;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static spark.Spark.*;

public class User {
    private final Repository repo;
    private static final GsonBuilder builder = new GsonBuilder();
    private static final Gson gson = builder.create();

    public User(Repository repo) {this.repo = repo;}

    public void init() {
        path("/user", () -> {
            get("/search",(req,res) ->
            {
                String name = req.queryParams("name");
                String surname = req.queryParams("surname");
                String startDate = req.queryParams("startDate");
                String endDate = req.queryParams("endDate");
                List<model.User> users = repo.getUserDAO().search(name,surname,startDate,endDate);
                return gson.toJson(users);
            });

            get("/loggedUser",(req,res) ->
            {
                Session ss = req.session(true);
                model.User user = ss.attribute("user");
                res.type("application/json");
                if (user != null) {
                    return gson.toJson(user);

                }
                else {
                    res.status(401);
                    res.body("User is not logged in !");
                    return res.body();
                }

            });

            get("/data",(req,res) ->
            {
                String username = req.queryParams("username");
                model.User user = repo.getUserDAO().getUserByUsername(username);
                res.type("application/json");
                if (user != null) {
                    return gson.toJson(user);

                }
                else {
                    res.status(401);
                    res.body("User is not logged in !");
                    return res.body();
                }

            });

            get("/picture", (req, res) -> {
                String path = req.queryParams("path");
                try {
                    File file = new File(path);
                    res.type("image/gif");
                    Files.copy(file.toPath(), res.raw().getOutputStream());
                    res.status(200);
                } catch(Exception e) {
                    res.status(404);
                    res.body("Picture not found!");
                }
                return res;
            });

            get("/loggedUserFriends",(req,res) -> {
                Session ss = req.session(true);
                model.User user = ss.attribute("user");
                if (user != null) {
                    List<model.User> friends = repo.getFriendRequestDAO().getFriendsForUser(user);
                    return gson.toJson(friends);

                }
                else {
                    res.status(401);
                    res.body("User is not logged in !");
                }
                return res.body();
            });

            get("/friends",(req,res) -> {
                res.type("application/json");
                String username = req.queryParams("username");
                List<model.User> friends = repo.getFriendRequestDAO().getFriendsForUser(repo.getUserDAO().getUserByUsername(username));
                return gson.toJson(friends);
            });

            get("/friendRequests",(req,res) -> {
                Session ss = req.session(true);
                model.User user = ss.attribute("user");
                if (user != null) {
                    List<FriendRequest> requests = repo.getFriendRequestDAO().getFriendRequestsForUser(user);
                    List<model.User> senders = repo.getUserDAO().getFriendRequestSenders(requests);
                    return gson.toJson(senders);

                }
                else {
                    res.status(401);
                    res.body("User is not logged in !");
                }
                return res.body();
            });

            post("/acceptRequest",(req, res) -> {
                String sender = req.queryParams("sender");
                Session ss = req.session(true);
                model.User user = ss.attribute("user");
                res.type("application/json");
                if (user != null) {
                    if (repo.getFriendRequestDAO().acceptRequest(sender,user.getUsername())) {res.status(200); res.body("Success");}
                    else {res.status(404); res.body("Not found");}
                }
                else {
                    res.status(401);
                    res.body("User is not logged in !");
                }
                return res.body();
            });

            post("/rejectRequest",(req, res) -> {
                String sender = req.queryParams("sender");
                Session ss = req.session(true);
                model.User user = ss.attribute("user");
                res.type("application/json");
                if (user != null) {
                    if (repo.getFriendRequestDAO().rejectRequest(sender,user.getUsername())) {res.status(200); res.body("Success");}
                    else {res.status(404); res.body("Not found");}
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
