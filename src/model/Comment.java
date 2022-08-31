package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Comment {
    private Integer id;
    private User user;
    private Integer post;
    private String text;
    private LocalDateTime date;
    private LocalDateTime dateChanged;
    private Boolean deleted;

    public Comment(Integer id, User user, Integer post, String text, LocalDateTime date, LocalDateTime dateChanged, Boolean deleted) {
        this.id = id;
        this.user = user;
        this.post = post;
        this.text = text;
        this.date = date;
        this.dateChanged = dateChanged;
        this.deleted = deleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getPost() {
        return post;
    }

    public void setPost(Integer post) {
        this.post = post;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(LocalDateTime dateChanged) {
        this.dateChanged = dateChanged;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", user=" + user +
                ", post=" + post +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", dateChanged=" + dateChanged +
                ", deleted=" + deleted +
                '}';
    }

    public String toRow() {
        return id + "," + post + "," + user.getUsername() + "," + text + "," + date + "," + dateChanged + "," + deleted;
    }
}
