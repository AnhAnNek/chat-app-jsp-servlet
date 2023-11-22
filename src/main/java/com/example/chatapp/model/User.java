package com.example.chatapp.model;

public class User {
    private String username;
    private String conversationId;
    private boolean isOnline;

    public User() {
    }

    public User(String username, String conversationId, boolean isOnline) {
        this.username = username;
        this.conversationId = conversationId;
        this.isOnline = isOnline;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", isOnline=" + isOnline +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }
}
