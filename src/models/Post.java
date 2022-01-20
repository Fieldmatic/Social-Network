package models;

import java.util.ArrayList;
import java.util.List;

public class Post {
    private String picture;
    private String text;
    private List<Comment> comments;

    public Post(String picture, String text) {
        this.picture = picture;
        this.text = text;
        comments = new ArrayList<>();
    }

    public Post(String picture, String text, List<Comment> comments) {
        this.picture = picture;
        this.text = text;
        this.comments = comments;
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

    @Override
    public String toString() {
        return "Post{" +
                "picture='" + picture + '\'' +
                ", text='" + text + '\'' +
                ", comments=" + comments +
                '}';
    }
}
