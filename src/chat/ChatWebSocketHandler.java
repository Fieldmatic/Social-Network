package chat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.Repository;
import dto.MessageDTO;
import model.Message;
import model.User;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@WebSocket
public class ChatWebSocketHandler {
    private static Map<Session, String> activeSessions = new ConcurrentHashMap<>();
    private static final GsonBuilder builder = new GsonBuilder();;
    private static final Gson gson = builder.create();
    private final Repository repo;

    public ChatWebSocketHandler(Repository repo) {this.repo = repo;}

    @OnWebSocketConnect
    public void onConnect(Session session) throws Exception {
        Map<String, List<String>> q = session.getUpgradeRequest().getParameterMap();
        String username = q.get("username").get(0);
        activeSessions.put(session,username);
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        activeSessions.remove(user);
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        MessageDTO messageDTO = gson.fromJson(message,MessageDTO.class);
        Message newMessage = repo.getMessageDAO().addMessage(messageDTO);
        broadcastMessage(session,newMessage);
    }

    public static void broadcastMessage(Session session, Message message) throws IOException {
       for (Map.Entry<Session,String> item : activeSessions.entrySet())
       {
           Session activeSession = item.getKey();
           String username = item.getValue();
           if (username.equals(message.getReceiver()))
           {
               activeSession.getRemote().sendString(gson.toJson(new MessageDTO(message.getSender(),message.getReceiver(),message.getMessageContent(),message.getTimeStamp().toString())));
               break;
           }

       }
    }
}
