package com.example.chatapp.service;

import com.example.chatapp.model.Chat;
import com.example.chatapp.websocket.ChatWebsocket;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public abstract class ChatService {
    protected static final Set<ChatWebsocket> chatWebsockets = new CopyOnWriteArraySet<>();

    public abstract boolean register(ChatWebsocket chatWebsocket);

    public abstract boolean close(ChatWebsocket chatWebsocket);

    public abstract void sendMessageToAllUsers(Chat message);

    public abstract void sendMessageToOneUser(Chat message);

    public abstract boolean isUserOnline(String username);

    protected Set<String> getUsernames() {
        Set<String> usernames = new HashSet<String>();
        chatWebsockets.forEach(chatWebsocket -> {
            usernames.add(chatWebsocket.getUsername());
        });
        return usernames;
    }
}
