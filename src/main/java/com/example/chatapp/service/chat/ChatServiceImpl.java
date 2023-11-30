package com.example.chatapp.service.chat;

import com.example.chatapp.model.ChatMessage;
import com.example.chatapp.websocket.ChatWebsocket;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class ChatServiceImpl implements ChatService {
    private static ChatService chatService = null;
    protected static final Set<ChatWebsocket> chatWebsockets = new CopyOnWriteArraySet<>();

    private ChatServiceImpl() {
    }

    public synchronized static ChatService getIns() {
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
        return chatWebsockets
                .removeIf(cw -> cw.getSession().equals(chatWebsocket.getSession()));
    }

    @Override
    public void sendMessageToOneUser(ChatMessage message) {
        String targetUser = message.getReceiverUsername();
        for (ChatWebsocket chatWebsocket : chatWebsockets) {
            if (chatWebsocket.getSenderUsername().equals(targetUser)) {
                chatWebsocket.sendMessage(message);
                return;
            }
        }
    }
}
