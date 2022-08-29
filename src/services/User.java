package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.Repository;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.path;

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
        });
    }
}
