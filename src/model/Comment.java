package model;

import java.time.LocalDate;

public class Comment {
    private User user;
    private LocalDate date;
    private LocalDate dateChanged;

    public Comment(User user) {
        this.user = user;
        this.date = LocalDate.now();
    }

    public Comment(User user, LocalDate date, LocalDate dateChanged) {
        this.user = user;
        this.date = date;
        this.dateChanged = dateChanged;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(LocalDate dateChanged) {
        this.dateChanged = dateChanged;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "user=" + user +
                ", date=" + date +
                ", dateChanged=" + dateChanged +
                '}';
    }
}
