package com.example.chatapp.service;

import com.example.chatapp.model.ChatMessage;
import com.example.chatapp.model.User;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MessageService {

    private static MessageService ins;

    public synchronized static MessageService getIns() {
        if (ins == null) {
            ins = new MessageService();
        }
        return ins;
    }

    private List<ChatMessage> messages;

    private MessageService() {
        messages = new ArrayList<>();

        messages.add(new ChatMessage("Chào Shop", Timestamp.from(Instant.now()),
                "queanpham", "vanannek"));
        messages.add(new ChatMessage("Cho e hỏi", Timestamp.from(Instant.now()),
                "queanpham", "vanannek"));
        messages.add(new ChatMessage("Laptop Asus X515 bao nhiêu ạ", Timestamp.from(Instant.now()),
                "queanpham", "vanannek"));

        messages.add(new ChatMessage("Đéo Biết", Timestamp.from(Instant.now()),
                "vanannek", "queanpham"));


        messages.add(new ChatMessage("Chào Shop", Timestamp.from(Instant.now()),
                "anhoang", "vanannek"));
        messages.add(new ChatMessage("Adapter cho Asus X515 bao nhiêu ạ", Timestamp.from(Instant.now()),
                "anhoang", "vanannek"));
        messages.add(new ChatMessage("Đéo Có bán", Timestamp.from(Instant.now()),
                "vanannek", "anhoang"));
    }

    public void save(ChatMessage chatMessage) {
        messages.add(chatMessage);
    }

    public List<User> getChattedUsersByCurrentUser(String curUsername) {
        List<String> receiverUsernames = messages.stream()
                .filter(cm -> !cm.getReceiverUsername().equals(curUsername) &&
                        cm.getSenderUsername().equals(curUsername))
                .map(ChatMessage::getReceiverUsername)
                .collect(Collectors.toList());

        List<User> users = receiverUsernames.stream()
                .map(username -> UserService.getIns().getByUsername(username))
                .collect(Collectors.toList());
        return users;
    }

    public List<ChatMessage> getMessagesByReceiver(String sender, String receiver) {
        return messages.stream()
                .filter(cm -> isMyChatMessage(cm, sender, receiver))
                .sorted(Comparator.comparing(ChatMessage::getSendingTime))
                .collect(Collectors.toList());
    }

    private boolean isMyChatMessage(ChatMessage cm, String sender, String receiver) {
        if (cm.getSenderUsername().equals(sender) &&
                cm.getReceiverUsername().equals(receiver))
            return true;
        if (cm.getSenderUsername().equals(receiver) &&
                cm.getReceiverUsername().equals(sender))
            return true;
        return false;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }
}
