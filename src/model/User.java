package model;

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
    private transient List<Post> posts;
    private List<String> pictures;
    private List<FriendRequest> friendRequests;
    private List<String> friends;

    private Boolean privateAccount;

    private Boolean blocked;


    public User(String username, String password, String email, String name, String surname, LocalDate birthDate, Gender gender, Role role, String profilePicture, List<Post> posts, List<String> pictures, List<FriendRequest> friendRequests, List<String> friends, Boolean privateAccount, Boolean blocked) {
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
        this.privateAccount = privateAccount;
        this.blocked = blocked;
    }

    public User(String username, String password, String email, String name, String surname, LocalDate birthDate, Gender gender, Role role, String profilePicture, Boolean privateAccount, Boolean blocked) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.gender = gender;
        this.role = role;
        this.profilePicture = profilePicture;
        this.privateAccount = privateAccount;
        this.blocked = blocked;
        this.posts = new ArrayList<>();
        this.pictures = new ArrayList<>();
        this.friendRequests = new ArrayList<>();
        this.friends = new ArrayList<>();
    }

    public User() {

    }

    public boolean getBlocked() {return blocked;}
    public void setBlocked(Boolean blocked) {this.blocked = blocked;}

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

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public Boolean getPrivateAccount() {
        return privateAccount;
    }

    public void setPrivateAccount(Boolean privateAccount) {
        this.privateAccount = privateAccount;
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
                ", pictures=" + pictures +
                ", friendRequests=" + friendRequests +
                ", friends=" + friends +
                ", privateAccount=" + privateAccount +
                '}';
    }

    public String toRow() {
        return username+","+password+","+email+","+name+","+surname+","+birthDate+","+gender+","+role+","+profilePicture+","+privateAccount+","+blocked;
    }
}
