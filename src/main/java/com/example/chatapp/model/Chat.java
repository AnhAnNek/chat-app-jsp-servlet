package com.example.chatapp.model;

public class Chat {
    private String username;
    private String message;
    private String receiver;

    public Chat() {
    }

    public Chat(String username, String message, String receiver) {
        this.username = username;
        this.message = message;
        this.receiver = receiver;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
