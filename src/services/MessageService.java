package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.Repository;
import dto.ChatItemDTO;
import dto.MessageDTO;
import model.Message;
import model.User;
import spark.Session;

import java.util.List;

import static spark.Spark.*;


public class MessageService {

    private final Repository repo;
    private static final GsonBuilder builder = new GsonBuilder();
    private static final Gson gson = builder.create();

    public MessageService(Repository repo) {this.repo = repo;}

    public void init() {
        path("/message",() -> {

            get("/chat",(req,res) -> {
                String username = req.queryParams("username");
                Session ss = req.session(true);
                User user = ss.attribute("user");
                res.type("application/json");
                if (user != null) {
                    List<MessageDTO> chat = repo.getMessageDAO().getChat(user.getUsername(),username);
                    res.status(200);
                    return gson.toJson(chat);
                } else {
                    res.status(401);
                    res.body("User is not logged in !");
                }
                return res.body();
            });

            post("/send",(req,res) -> {
                String messageJSON = req.body();
                Session ss = req.session(true);
                User user=ss.attribute("user");
                res.type("application/json");
                if (user != null) {
                    MessageDTO messageDTO = gson.fromJson(messageJSON, MessageDTO.class);
                    Message message = repo.getMessageDAO().addMessage(messageDTO);
                    MessageDTO response = new MessageDTO(message.getSender(), message.getReceiver(), message.getMessageContent(), message.getTimeStamp().toString());
                    return gson.toJson(response);
                } else {
                    res.status(401);
                    res.body("User is not logged in !");
                }
                return res.body();
            });

            get("/history",(req,res) -> {
                Session ss = req.session(true);
                User user=ss.attribute("user");
                res.type("application/json");
                if (user != null) {
                    List<ChatItemDTO> chatItems = repo.getMessageDAO().getChatHistory(user);
                    return gson.toJson(chatItems);
                }
                else{
                    res.status(401);
                    res.body("User is not logged in !");
                }
                return res.body();
            });

        });
    }
}
