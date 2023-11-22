package com.example.chatapp.service;

import com.example.chatapp.model.Chat;
import com.example.chatapp.websocket.ChatWebsocket;

import javax.websocket.EncodeException;
import java.io.IOException;

public class ChatServiceImpl extends ChatService {

    private static ChatService chatService = null;

    private ChatServiceImpl() {
    }

    public synchronized static ChatService getInstance() {
        if (chatService == null) {
            chatService = new ChatServiceImpl();
        }
        return chatService;
    }

    @Override
    public boolean register(ChatWebsocket chatWebsocket) {
        return chatWebsockets.add(chatWebsocket);
    }

    @Override
    public boolean close(ChatWebsocket chatWebsocket) {
        return chatWebsockets.remove(chatWebsocket);
    }

    @Override
    public void sendMessageToAllUsers(Chat message) {
        chatWebsockets.stream().forEach(chatWebsocket -> {
            try {
                chatWebsocket.getSession().getBasicRemote().sendObject(message);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void sendMessageToOneUser(Chat message) {
    }

    @Override
    public boolean isUserOnline(String username) {
        for (ChatWebsocket chatWebsocket : chatWebsockets) {
            if (chatWebsocket.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
}
