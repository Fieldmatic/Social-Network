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
                RegistrationDTO registrationDTO = gson.fromJson(reqBody, RegistrationDTO.class);
                User newUser = repo.getUserDAO().dtoToUser(registrationDTO);
                if (repo.getUserDAO().credentialsAvailable(registrationDTO)) {
                    repo.getUserDAO().users.add(newUser);
                    repo.getUserDAO().serialize();
                    return HttpStatus.OK_200;
                }
                else return HttpStatus.NOT_ACCEPTABLE_406;
            });


            post("/login", (req,res) -> {
               String reqBody = req.body();
               LoginDTO loginDTO = gson.fromJson(reqBody,LoginDTO.class);
               Session ss = req.session(true);
               User user = ss.attribute("user");
               if (repo.getUserDAO().validCredentials(loginDTO) && user == null) {
                   user = repo.getUserDAO().getUserByUsername(loginDTO.getUsername());
                   ss.attribute("user",user);
                   return HttpStatus.ACCEPTED_202;
               }
               else return HttpStatus.FORBIDDEN_403;
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
