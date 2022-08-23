package model;

import java.time.LocalDate;

public class Message {
    private User sender;
    private User receiver;
    private String messageContent;
    private LocalDate date;

    public Message(User sender, User receiver, String messageContent, LocalDate date) {
        this.sender = sender;
        this.receiver = receiver;
        this.messageContent = messageContent;
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

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sender=" + sender +
                ", receiver=" + receiver +
                ", messageContent='" + messageContent + '\'' +
                ", date=" + date +
                '}';
    }
}
