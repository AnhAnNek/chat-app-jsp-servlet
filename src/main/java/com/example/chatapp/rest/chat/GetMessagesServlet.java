package com.example.chatapp.rest.chat;

import com.example.chatapp.model.ChatMessage;
import com.example.chatapp.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/get-messages")
public class GetMessagesServlet extends HttpServlet {

    private MessageService messageService = MessageService.getIns();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sender = req.getParameter("sender");
        String receiver = req.getParameter("receiver");

        PrintWriter pw = resp.getWriter();
        String jsonMsgs = getMsgs(sender, receiver);
        pw.println(jsonMsgs);
        pw.close();
    }

    private String getMsgs(String sender, String receiver) {
        List<ChatMessage> msgs = messageService.getMessagesByReceiver(sender, receiver);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(msgs);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
