package com.example.chatapp.model;

import java.sql.Timestamp;

public class ChatMessage {
    public enum EType {
        TEXT, NOTIFICATION
    }

    private String message;
    private EType type;
    private Timestamp sendingTime;
    private String senderUsername;
    private String receiverUsername;

    public ChatMessage() {
    }

    public ChatMessage(String message, EType type, Timestamp sendingTime, String senderUsername, String receiverUsername) {
        this.message = message;
        this.type = type;
        this.sendingTime = sendingTime;
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "message='" + message + '\'' +
                ", type=" + type +
                ", sendingTime=" + sendingTime +
                ", senderUsername='" + senderUsername + '\'' +
                ", receiverUsername='" + receiverUsername + '\'' +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public EType getType() {
        return type;
    }

    public void setType(EType type) {
        this.type = type;
    }

    public Timestamp getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(Timestamp sendingTime) {
        this.sendingTime = sendingTime;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }
}
