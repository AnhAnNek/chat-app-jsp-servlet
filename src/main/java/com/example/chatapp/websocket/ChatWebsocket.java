package com.example.chatapp.websocket;

import com.example.chatapp.model.Chat;
import com.example.chatapp.service.ChatService;
import com.example.chatapp.service.ChatServiceImpl;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat/{username}")
public class ChatWebsocket {
    private String username;
    private Session session;

    private ChatService chatService = ChatServiceImpl.getInstance();

    @OnOpen
    public void onOpen(@PathParam("username") String username, Session curSession) {
        if (chatService.register(this)) {
            this.session = session;
            this.username = username;
            String receiver = "all";
            Chat msgResponse = new Chat(this.username, "[P]open", receiver);
            chatService.sendMessageToAllUsers(msgResponse);
        }
    }

    @OnClose
    public void onClose(Session curSession) {
        if (chatService.close(this)) {
            String receiver = "all";
            Chat msgResponse = new Chat(this.username, "[P]close", receiver);
            chatService.sendMessageToAllUsers(msgResponse);
        }
    }

//    @OnMessage
//    public void onMessage(Session userSession) {
//        chatService.sendMessageToOneUser(message);
//    }

    public String getUsername() {
        return username;
    }

    public Session getSession() {
        return session;
    }
}
