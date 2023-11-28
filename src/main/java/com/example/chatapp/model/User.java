package com.example.chatapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

public class User {
    public enum ERole {
        SALESPERSON, CUSTOMER
    }

    private String username;
    private ERole role;

    @JsonIgnore
    private Set<ChatMessage> chats;

    public User() {
        chats = new HashSet<>();
    }

    public User(String username, ERole role) {
        this.username = username;
        this.role = role;
        chats = new HashSet<>();
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }

    public Set<ChatMessage> getChats() {
        return chats;
    }

    public void setChats(Set<ChatMessage> chats) {
        this.chats = chats;
    }
}
