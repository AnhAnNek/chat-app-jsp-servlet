package com.example.chatapp.websocket;

import com.example.chatapp.model.ChatMessage;
import com.example.chatapp.model.User;
import com.example.chatapp.service.ChatService;
import com.example.chatapp.service.ChatServiceImpl;
import com.example.chatapp.service.MessageService;
import com.example.chatapp.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@ServerEndpoint("/chat/{senderUsername}/{receiverUsername}")
public class ChatWebsocket {
    private Session session;
    private String senderUsername;
    private String receiverUsername;
    private ChatService chatService = ChatServiceImpl.getIns();
    private UserService userService = UserService.getIns();
    private MessageService messageService = MessageService.getIns();

    @OnOpen
    public void onOpen(@PathParam("senderUsername") String senderUsername,
                       @PathParam("receiverUsername") String receiverUsername,
                       Session session) {
        if (!chatService.register(this)) {
            return;
        }
        this.session = session;
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
        User user = userService.getByUsername(senderUsername);
        if (user == null) {
            return;
        }
        List<ChatMessage> msgs = messageService.getMessages(senderUsername, receiverUsername);
        msgs.forEach(this::sendMessage);
    }

    @OnClose
    public void onClose(Session curSession) {
        chatService.close(this);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        ChatMessage chatMessage = new ChatMessage(message, Timestamp.from(Instant.now()),
                senderUsername, receiverUsername);
        chatService.sendMessageToOneUser(chatMessage);
        sendMessage(chatMessage);
        messageService.save(chatMessage);
    }

    public void sendMessage(ChatMessage message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMessage = objectMapper.writeValueAsString(message);

            session.getBasicRemote().sendText(jsonMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
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
