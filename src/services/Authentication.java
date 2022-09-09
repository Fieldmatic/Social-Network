package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.Repository;
import dto.LoginDTO;
import dto.RegistrationDTO;
import model.User;
import org.eclipse.jetty.http.HttpStatus;
import spark.Session;

import static spark.Spark.path;
import static spark.Spark.post;
import static spark.Spark.get;

public class Authentication {

    private final Repository repo;
    private static final GsonBuilder builder = new GsonBuilder();
    private static final Gson gson = builder.create();

    public Authentication(Repository repo){ this.repo = repo;}

    public void init() {
        path("/authentication", () ->
        {

            post("/register", (req,res) -> {
                String reqBody = req.body();
                res.type("application/json");

                RegistrationDTO registrationDTO = gson.fromJson(reqBody, RegistrationDTO.class);
                User newUser = repo.getUserDAO().dtoToUser(registrationDTO);
                if (repo.getUserDAO().credentialsAvailable(registrationDTO)) {
                    newUser.setProfilePicture("./static/images/default-profile.jpg");
                    repo.getUserDAO().users.add(newUser);
                    repo.getUserDAO().serialize();
                    return gson.toJson(newUser);
                }
                else {
                    res.status(406);
                    res.body("Username or email already taken.");
                    return res.body();
                }
            });


            post("/login", (req,res) -> {
               String reqBody = req.body();
               res.type("application/json");

               LoginDTO loginDTO = gson.fromJson(reqBody,LoginDTO.class);
               Session ss = req.session(true);
               User user = ss.attribute("user");

               if (user != null) {
                   res.status(403);
                   res.body("The user is already logged in!");
                   return res.body();
               }
               else if (repo.getUserDAO().validCredentials(loginDTO)) {
                   user = repo.getUserDAO().getUserByUsername(loginDTO.getUsername());
                   if (user.getBlocked()) { res.status(403); res.body("This account has been blocked."); return res.body();}
                   ss.attribute("user",user);
                   return gson.toJson(user);
               }
               else {
                   res.status(403);
                   res.body("Wrong username or password.");
                   return res.body();
               }
            });

            get("/logout",(req,res) -> {
                Session ss = req.session(true);
                User user = ss.attribute("user");
                if (user != null) {
                    ss.invalidate();
                    return HttpStatus.ACCEPTED_202;
                }
                else return HttpStatus.FORBIDDEN_403;
            });
        });
    }

}
