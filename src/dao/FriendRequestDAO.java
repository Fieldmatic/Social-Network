package dao;

import enums.FriendRequestStatus;
import model.FriendRequest;
import model.Post;
import model.User;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FriendRequestDAO {
    public List<FriendRequest> friendRequests;
    private UserDAO userDAO;

    public FriendRequestDAO(UserDAO userDAO){
        this.friendRequests = new ArrayList<>();
        this.userDAO = userDAO;
    }


    public void load(){
        File file = new File("data/csv/FriendRequest.csv");
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                br.readLine();
                while ((line = br.readLine()) != null) {
                    String[] row = line.split(",");
                    friendRequests.add(getFriendRequestFromRow(row));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File not found");
        }
    }

    public void serialize(){
        try {
            StringBuilder inputBuffer = new StringBuilder();
            inputBuffer.append("senderId,receiverId,status,date\n");
            for(FriendRequest friendRequest : friendRequests) {
                inputBuffer.append(friendRequest.toRow());
                inputBuffer.append("\n");
            }
            FileOutputStream fileOut = new FileOutputStream("data/csv/FriendRequest.csv");
            fileOut.write(inputBuffer.toString().getBytes());
            fileOut.close();
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public boolean acceptRequest(String sender, String receiver) {
        for (FriendRequest request: friendRequests) {
            if ((request.getSender().equals(sender)) && (request.getReceiver().equals(receiver)) && (request.getStatus().equals(FriendRequestStatus.WAIT)))
            {
                request.setStatus(FriendRequestStatus.ACCEPTED);
                userDAO.getUserByUsername(sender).getFriends().add(receiver);
                userDAO.getUserByUsername(receiver).getFriends().add(sender);
                serialize();
                return true;
            }
        }
        return false;
    }

    public void createRequest(String sender, String receiver){
        this.friendRequests.add(new FriendRequest(sender,receiver));
        serialize();
    }

    public boolean requestExists(String sender, String receiver) {
        for (FriendRequest request: friendRequests) {
            if ((request.getSender().equals(sender)) && (request.getReceiver().equals(receiver)) && (request.getStatus().equals(FriendRequestStatus.WAIT)))
                return true;
        }
        return false;
    }

    public boolean stopFriendship(String username, String friendUsername){
        for (FriendRequest request: friendRequests) {
            if (((request.getSender().equals(username)) && (request.getReceiver().equals(friendUsername)) && (request.getStatus().equals(FriendRequestStatus.ACCEPTED)))
                    || ((request.getSender().equals(friendUsername)) && (request.getReceiver().equals(username)) && (request.getStatus().equals(FriendRequestStatus.ACCEPTED))))
            {
                friendRequests.remove(request);
                userDAO.getUserByUsername(username).getFriends().remove(friendUsername);
                userDAO.getUserByUsername(friendUsername).getFriends().remove(username);
                serialize();
                return true;
            }
        }
        return false;
    }

    public boolean areFriends(String username1, String username2) {
        for (FriendRequest request: friendRequests) {
            if (((request.getSender().equals(username1)) && (request.getReceiver().equals(username2)) && (request.getStatus().equals(FriendRequestStatus.ACCEPTED)))
                    || ((request.getSender().equals(username2)) && (request.getReceiver().equals(username1)) && (request.getStatus().equals(FriendRequestStatus.ACCEPTED))))
                return true;
        }
        return false;
    }

    public boolean rejectRequest(String sender, String receiver) {
        for (FriendRequest request: friendRequests) {
            if ((request.getSender().equals(sender)) && (request.getReceiver().equals(receiver)) && (request.getStatus().equals(FriendRequestStatus.WAIT)))
            {
                request.setStatus(FriendRequestStatus.REJECTED);
                serialize();
                return true;
            }
        }
        return false;
    }

    public List<User> getFriendsForUser(User user) {
        List<model.User> friends = new ArrayList<>();
        for (FriendRequest friendRequest : friendRequests) {
            if (user.getUsername().equals(friendRequest.getReceiver()) && friendRequest.getStatus() == FriendRequestStatus.ACCEPTED) friends.add(userDAO.getUserByUsername(friendRequest.getSender()));
            if (user.getUsername().equals(friendRequest.getSender()) && friendRequest.getStatus() == FriendRequestStatus.ACCEPTED) friends.add(userDAO.getUserByUsername(friendRequest.getReceiver()));
        }
        return friends;
    }

    public List<User> getMutualFriends(User user1, User user2) {
        List<model.User> friends = new ArrayList<>();
        List<model.User> user1Friends = getFriendsForUser(user1);
        List<model.User> user2Friends = getFriendsForUser(user2);
        friends= user1Friends.stream()
                .filter(user2Friends::contains)
                .collect(Collectors.toList());
        return friends;
    }

    public List<FriendRequest> getFriendRequestsForUser(User user) {
        List<FriendRequest> requests = new ArrayList<>();
        for (FriendRequest friendRequest : friendRequests) {
            if (user.getUsername().equals(friendRequest.getReceiver()) && friendRequest.getStatus() == FriendRequestStatus.WAIT) requests.add(friendRequest);
        }
        return requests;
    }

    private FriendRequest getFriendRequestFromRow(String[] row) {
        User sender = userDAO.getUserByUsername(row[0]);
        User receiver = userDAO.getUserByUsername(row[1]);
        FriendRequestStatus status = FriendRequestStatus.valueOf(row[2]);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(row[3],formatter);
        FriendRequest friendRequest = new FriendRequest(row[0],row[1],status,dateTime);
        if (status.equals(FriendRequestStatus.ACCEPTED)) {
           sender.getFriends().add(row[1]);
           receiver.getFriends().add(row[0]);
        }
        sender.getFriendRequests().add(friendRequest);
        receiver.getFriendRequests().add(friendRequest);
        return friendRequest;
    }
}
