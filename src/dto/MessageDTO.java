package dto;

import java.time.LocalDateTime;

public class MessageDTO {
    private String sender;
    private String receiver;
    private String messageContent;
    private String timeStamp;

    public MessageDTO(String sender, String receiver, String messageContent, String timeStamp) {
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

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
