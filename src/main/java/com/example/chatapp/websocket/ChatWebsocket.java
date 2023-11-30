package com.example.chatapp.websocket;

import com.example.chatapp.model.ChatMessage;
import com.example.chatapp.model.User;
import com.example.chatapp.service.chat.ChatService;
import com.example.chatapp.service.chat.ChatServiceImpl;
import com.example.chatapp.service.MessageServiceImpl;
import com.example.chatapp.service.UserServiceImpl;
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
    private UserServiceImpl userService = UserServiceImpl.getIns();
    private MessageServiceImpl messageService = MessageServiceImpl.getIns();

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

        String msg = String.format("`%s` join chat.", senderUsername);
        ChatMessage chatMessage = new ChatMessage(msg, ChatMessage.EType.NOTIFICATION,
                Timestamp.from(Instant.now()), senderUsername, receiverUsername);
        chatService.sendMessageToOneUser(chatMessage);
    }

    @OnClose
    public void onClose(Session curSession) {
        if (chatService.close(this)) {
            String msg = String.format("`%s` close chat.", senderUsername);
            ChatMessage chatMessage = new ChatMessage(msg, ChatMessage.EType.NOTIFICATION,
                    Timestamp.from(Instant.now()), senderUsername, receiverUsername);
            chatService.sendMessageToOneUser(chatMessage);
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        ChatMessage chatMessage = new ChatMessage(message, ChatMessage.EType.TEXT, Timestamp.from(Instant.now()),
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
