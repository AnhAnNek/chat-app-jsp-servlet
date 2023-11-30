package com.example.chatapp.service;

import com.example.chatapp.model.ChatMessage;
import com.example.chatapp.model.User;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MessageServiceImpl {

    private static MessageServiceImpl ins;

    public synchronized static MessageServiceImpl getIns() {
        if (ins == null) {
            ins = new MessageServiceImpl();
        }
        return ins;
    }

    private List<ChatMessage> messages;

    private MessageServiceImpl() {
        messages = new ArrayList<>();

        messages.add(new ChatMessage("Chào Shop", ChatMessage.EType.TEXT, Timestamp.from(Instant.now()),
                "queanpham", "vanannek"));
        messages.add(new ChatMessage("Cho e hỏi", ChatMessage.EType.TEXT, Timestamp.from(Instant.now()),
                "queanpham", "vanannek"));
        messages.add(new ChatMessage("Laptop Asus X515 bao nhiêu ạ", ChatMessage.EType.TEXT,
                Timestamp.from(Instant.now()), "queanpham", "vanannek"));

        messages.add(new ChatMessage( "`vanannek` open chat.", ChatMessage.EType.NOTIFICATION, Timestamp.from(Instant.now()),
                "vanannek", "queanpham"));
        messages.add(new ChatMessage("Đéo Biết", ChatMessage.EType.TEXT, Timestamp.from(Instant.now()),
                "vanannek", "queanpham"));
        messages.add(new ChatMessage("Hihi", ChatMessage.EType.TEXT, Timestamp.from(Instant.now()),
                "vanannek", "queanpham"));
        messages.add(new ChatMessage( "`vanannek` close chat.", ChatMessage.EType.NOTIFICATION, Timestamp.from(Instant.now()),
                "vanannek", "queanpham"));


        messages.add(new ChatMessage("Chào Shop", ChatMessage.EType.TEXT, Timestamp.from(Instant.now()),
                "anhoang", "vanannek"));
        messages.add(new ChatMessage("Adapter cho Asus X515 bao nhiêu ạ", ChatMessage.EType.TEXT,
                Timestamp.from(Instant.now()), "anhoang", "vanannek"));
        messages.add(new ChatMessage("Đéo Có bán", ChatMessage.EType.TEXT, Timestamp.from(Instant.now()),
                "vanannek", "anhoang"));
        messages.add(new ChatMessage("Hihi", ChatMessage.EType.TEXT, Timestamp.from(Instant.now()),
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

        return receiverUsernames.stream()
                .map(username -> UserServiceImpl.getIns().getByUsername(username))
                .distinct()
                .collect(Collectors.toList());
    }

    public List<ChatMessage> getMessagesForCustomer(String username) {
        return messages.stream()
                .filter(cm -> cm.getReceiverUsername().equals(username) ||
                        cm.getSenderUsername().equals(username))
                .sorted(Comparator.comparing(ChatMessage::getSendingTime))
                .collect(Collectors.toList());
    }

    public List<ChatMessage> getMessagesForSalesperson(String sender, String receiver) {
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

    public List<ChatMessage> getMessagesForSalesperson() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }
}
