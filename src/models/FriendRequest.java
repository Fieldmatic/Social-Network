package models;

import enums.FriendRequestStatus;

import java.time.LocalDate;

public class FriendRequest {
    private User sender;
    private User receiver;
    private FriendRequestStatus status;
    private LocalDate date;

    public FriendRequest(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = FriendRequestStatus.WAIT;
        this.date = LocalDate.now();
    }

    public FriendRequest(User sender, User receiver, FriendRequestStatus status, LocalDate date) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = status;
        this.date = date;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public FriendRequestStatus getStatus() {
        return status;
    }

    public void setStatus(FriendRequestStatus status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "FriendRequest{" +
                "sender=" + sender +
                ", receiver=" + receiver +
                ", status=" + status +
                ", date=" + date +
                '}';
    }
}
