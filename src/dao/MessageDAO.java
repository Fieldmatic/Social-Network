package dao;

import dto.ChatItemDTO;
import dto.MessageDTO;
import model.Comment;
import model.Message;
import model.Post;
import model.User;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageDAO {
    private List<Message> messages;

    private UserDAO userDAO;


    public MessageDAO(UserDAO userDAO){
        this.messages = new ArrayList<>();
        this.userDAO = userDAO;
    }

    public void load(){
        File file = new File("data/csv/Message.csv");
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                br.readLine();
                while ((line = br.readLine()) != null) {
                    String[] row = line.split(",");
                    messages.add(getMessageFromRow(row));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File not found");
        }
    }

    private Message getMessageFromRow(String[] row) {
        String sender = row[0];
        String receiver = row[1];
        String messageContent = row[2];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime timeStamp = LocalDateTime.parse(row[3],formatter);
        return new Message(sender,receiver,messageContent,timeStamp);
    }

    public void serialize(){
        try {
            StringBuilder inputBuffer = new StringBuilder();
            inputBuffer.append("sender,receiver,message,timestamp\n");
            for(Message message : this.messages) {
                inputBuffer.append(message.toRow());
                inputBuffer.append("\n");
            }
            FileOutputStream fileOut = new FileOutputStream("data/csv/Message.csv");
            fileOut.write(inputBuffer.toString().getBytes());
            fileOut.close();
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public List<ChatItemDTO> getChatHistory(User user) {
        Map<String,Message> messageMap = new HashMap<>();
        List<ChatItemDTO> chatHistory = new ArrayList<>();
        for(Message message : messages)
        {
            if (message.getSender().equals(user.getUsername()))
                messageMap.put(message.getReceiver(), message);
            if (message.getReceiver().equals(user.getUsername()))
                messageMap.put(message.getSender(), message);
        }
        for (String key : messageMap.keySet()) {
            chatHistory.add(new ChatItemDTO(userDAO.getUserByUsername(key),messageMap.get(key)));
        }
        chatHistory.sort((c1,c2) -> c2.getMessage().getTimeStamp().compareTo(c1.getMessage().getTimeStamp()));
        return chatHistory;
    }

    public List<MessageDTO> getChat(String username1, String username2) {
        List<MessageDTO> chat = new ArrayList<>();
        for (Message message : messages) {
            if (((message.getSender().equals(username1)) && (message.getReceiver().equals(username2)))
                ||(message.getSender().equals(username2)) && (message.getReceiver().equals(username1)))
            {
                chat.add(new MessageDTO(message.getSender(), message.getReceiver(), message.getMessageContent(), message.getTimeStamp().toString()));
            }
        }
        return chat;
    }

    public Message addMessage(MessageDTO messageDTO) {
        Message message = new Message(messageDTO.getSender(),messageDTO.getReceiver(),messageDTO.getMessageContent());
        this.messages.add(message);
        serialize();
        return message;
    }
}
