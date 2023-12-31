package com.example.chatapp;

import com.example.chatapp.model.Chat;
import com.example.chatapp.model.User;

import java.util.List;

public class DbStore {
    private static DbStore ins;

    private List<User> users;
    private List<Chat> chats;

    public synchronized static DbStore getIns() {
        if (ins == null) {
            ins = new DbStore();
        }
        return ins;
    }

    private DbStore() {
    }

}
