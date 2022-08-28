package model;

import java.util.ArrayList;
import java.util.List;

public class Post {
    private Integer id;
    private String user;
    private String picture;
    private String text;
    private List<Comment> comments;
    private Boolean deleted;

    public Post(Integer id, String user, String picture, String text, Boolean deleted) {
        this.id = id;
        this.user = user;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", user=" + user +
                ", picture='" + picture + '\'' +
                ", text='" + text + '\'' +
                ", comments=" + comments +
                ", deleted=" + deleted +
                '}';
    }

    public String toRow() {
        return id + "," + user + "," + picture + "," + text + "," + deleted;
    }
}
