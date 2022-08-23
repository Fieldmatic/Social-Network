package dao;

import enums.FriendRequestStatus;
import model.FriendRequest;
import model.Post;
import model.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    private FriendRequest getFriendRequestFromRow(String[] row) {
        User sender = userDAO.getUserByUsername(row[0]);
        User receiver = userDAO.getUserByUsername(row[1]);
        FriendRequestStatus status = FriendRequestStatus.valueOf(row[2]);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
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
