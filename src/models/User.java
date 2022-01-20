package models;

import enums.AccountType;
import enums.Gender;
import enums.Role;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private String email;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private Gender gender;
    private Role role;
    private String profilePicture;
    private List<Post> posts;
    private List<String> pictures;
    private List<FriendRequest> friendRequests;
    private List<User> friends;
    private AccountType accountType;


    public User(String username, String password, String email, String name, String surname, LocalDate birthDate, Gender gender, Role role, String profilePicture, List<Post> posts, List<String> pictures, List<FriendRequest> friendRequests, List<User> friends, AccountType accountType) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.gender = gender;
        this.role = role;
        this.profilePicture = profilePicture;
        this.posts = posts;
        this.pictures = pictures;
        this.friendRequests = friendRequests;
        this.friends = friends;
        this.accountType = accountType;
    }

    public User(String username, String password, String email, String name, String surname, LocalDate birthDate, Gender gender, Role role, AccountType accountType) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.gender = gender;
        this.role = role;
        this.accountType = accountType;
        this.posts = new ArrayList<>();
        this.pictures = new ArrayList<>();
        this.friendRequests = new ArrayList<>();
        this.friends = new ArrayList<>();
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public List<FriendRequest> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(List<FriendRequest> friendRequests) {
        this.friendRequests = friendRequests;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                ", role=" + role +
                ", profilePicture='" + profilePicture + '\'' +
                ", posts=" + posts +
                ", pictures=" + pictures +
                ", friendRequests=" + friendRequests +
                ", friends=" + friends +
                ", accountType=" + accountType +
                '}';
    }
}
