package com.example.chatapp.model;

import java.sql.Timestamp;

public class ChatMessage {
    private String message;
    private Timestamp sendingTime;
    private String senderUsername;
    private String receiverUsername;

    public ChatMessage() {
    }

    public ChatMessage(String message, Timestamp sendingTime, String senderUsername, String receiverUsername) {
        this.message = message;
        this.sendingTime = sendingTime;
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "message='" + message + '\'' +
                ", sendingTime=" + sendingTime +
                ", sender=" + senderUsername +
                ", receiver=" + receiverUsername +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
