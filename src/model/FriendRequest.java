package model;

import enums.FriendRequestStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FriendRequest {
    private String sender;
    private String receiver;
    private FriendRequestStatus status;
    private LocalDateTime date;

    public FriendRequest(String sender, String receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = FriendRequestStatus.WAIT;
        this.date = LocalDateTime.now();
    }

    public FriendRequest(String sender, String receiver, FriendRequestStatus status, LocalDateTime date) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = status;
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public FriendRequestStatus getStatus() {
        return status;
    }

    public void setStatus(FriendRequestStatus status) {
        this.status = status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
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
