package model;

import java.util.ArrayList;
import java.util.List;

public class Post {
    private Integer id;
    private String ownerUsername;
    private String ownerName;
    private String ownerSurname;
    private String ownerProfilePicture;
    private String picture;
    private String text;
    private List<Comment> comments;
    private Boolean deleted;

    public Post(Integer id, String username, String name, String surname, String ownerProfilePicture, String picture, String text, Boolean deleted) {
        this.id = id;
        this.ownerUsername = username;
        this.ownerName = name;
        this.ownerSurname = surname;
        this.ownerProfilePicture = ownerProfilePicture;
        this.picture = picture;
        this.text = text;
        this.comments = new ArrayList<>();
        this.deleted = deleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerSurname() {
        return ownerSurname;
    }

    public void setOwnerSurname(String ownerSurname) {
        this.ownerSurname = ownerSurname;
    }

    public String getOwnerProfilePicture() {
        return ownerProfilePicture;
    }

    public void setOwnerProfilePicture(String ownerProfilePicture) {
        this.ownerProfilePicture = ownerProfilePicture;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", user=" + ownerUsername +
                ", picture='" + picture + '\'' +
                ", text='" + text + '\'' +
                ", comments=" + comments +
                ", deleted=" + deleted +
                '}';
    }

    public String toRow() {
        return id + "," + ownerUsername + "," + picture + "," + text + "," + deleted;
    }

}
