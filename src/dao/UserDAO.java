package dao;

import dto.LoginDTO;
import dto.RegistrationDTO;
import enums.Gender;
import enums.Role;
import model.FriendRequest;
import model.Post;
import model.User;

import java.io.*;
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

    public void serialize(){
        try {
            StringBuilder inputBuffer = new StringBuilder();
            inputBuffer.append("username,password,email,name,surname,birthDate,gender,role,profilePicture,privateAccount\n");
            for(User user : users) {
                inputBuffer.append(user.toRow());
                inputBuffer.append("\n");
            }
            FileOutputStream fileOut = new FileOutputStream("data/csv/User.csv");
            fileOut.write(inputBuffer.toString().getBytes());
            fileOut.close();
        } catch (Exception e) {
            System.out.println("Error");
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

    public User dtoToUser(RegistrationDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setBirthDate(LocalDate.parse(dto.getBirthDate(),DateTimeFormatter.ofPattern("yyyy.MM.dd")));
        user.setEmail(dto.getEmail());
        user.setRole(Role.USER);
        user.setProfilePicture(dto.getProfilePicture());
        user.setGender(Gender.valueOf(dto.getGender()));
        user.setPrivateAccount(dto.getPrivateAccount());
        return user;

    }

    public User getUserByUsername(String username) {
        for(User u : users) {
            if (u.getUsername().equals(username)) return u;
        }
        return null;
    }

    public boolean validCredentials(LoginDTO dto) {
        for (User user : users) {
            if (user.getUsername().equals(dto.getUsername()) && user.getPassword().equals(dto.getPassword())) return true;
        }
        return false;
    }

    public boolean credentialsAvailable(RegistrationDTO dto) {
        for (User user : users) {
            if (user.getUsername().equals(dto.getUsername()) || user.getEmail().equals(dto.getEmail())) return false;
        }
        return true;
    }


}
