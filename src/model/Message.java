package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Message {
    private String sender;
    private String receiver;
    private String messageContent;
    private LocalDateTime timeStamp;

    public Message(String sender, String receiver, String messageContent) {
        this.sender = sender;
        this.receiver = receiver;
        this.messageContent = messageContent;
        this.timeStamp = LocalDateTime.now().withSecond(0).withNano(0);
    }

    public Message(String sender, String receiver, String messageContent, LocalDateTime timeStamp) {
        this.sender = sender;
        this.receiver = receiver;
        this.messageContent = messageContent;
        this.timeStamp = timeStamp;
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

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setDate(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sender=" + sender +
                ", receiver=" + receiver +
                ", messageContent='" + messageContent + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }

    public String toRow() {
        return sender + "," + receiver + "," +messageContent + "," + timeStamp;
    }
}
