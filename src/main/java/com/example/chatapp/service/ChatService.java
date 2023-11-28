package com.example.chatapp.service;

import com.example.chatapp.model.ChatMessage;
import com.example.chatapp.websocket.ChatWebsocket;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public interface ChatService {

    boolean register(ChatWebsocket chatWebsocket);

    boolean close(ChatWebsocket chatWebsocket);

    void sendMessageToOneUser(ChatMessage message);
}
