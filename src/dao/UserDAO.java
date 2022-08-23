package dao;

import enums.Gender;
import enums.Role;
import model.FriendRequest;
import model.Post;
import model.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public List<User> users;

    public UserDAO () { users = new ArrayList<>(); }

    public void load(){
        File file = new File("data/csv/User.csv");
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                br.readLine();
                while ((line = br.readLine()) != null) {
                    String[] row = line.split(",");
                    users.add(getUserFromRow(row));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File not found");
        }
    }

    public User getUserFromRow(String[] row) {
        String username = row[0];
        String password = row[1];
        String email = row[2];
        String name = row[3];
        String surname = row[4];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDate = LocalDate.parse(row[5], formatter);
        Gender gender = Gender.valueOf(row[6]);
        Role role = Role.valueOf(row[7]);
        String profilePicture = row[8];
        Boolean privateAccount = Boolean.parseBoolean(row[9]);
        return new User(username,password,email,name,surname,birthDate,gender,role,profilePicture,privateAccount);
    }

    public User getUserByUsername(String username) {
        for(User u : users) {
            if (u.getUsername().equals(username)) return u;
        }
        return null;
    }


}
