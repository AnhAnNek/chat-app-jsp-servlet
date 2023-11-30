package com.example.chatapp.rest.chat;

import com.example.chatapp.model.ChatMessage;
import com.example.chatapp.service.MessageServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/get-messages-for-salesperson")
public class GetMessagesForSalespersonServlet extends HttpServlet {

    private MessageServiceImpl messageService = MessageServiceImpl.getIns();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sender = req.getParameter("sender");
        String receiver = req.getParameter("receiver");

        String jsonMsgs = getMsgsForSalesperson(sender, receiver);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter pw = resp.getWriter();
        pw.println(jsonMsgs);
        pw.close();
    }

    private String getMsgsForSalesperson(String sender, String receiver) {
        List<ChatMessage> msgs = messageService.getMessagesForSalesperson(sender, receiver);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(msgs);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
