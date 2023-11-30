package com.example.chatapp.rest.chat;

import com.example.chatapp.model.User;
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

@WebServlet("/get-chatted-users")
public class GetChattedUsersServlet extends HttpServlet {

    private MessageServiceImpl messageService = MessageServiceImpl.getIns();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");

        String jsonMsgs = getChattedUsers(username);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter pw = resp.getWriter();
        pw.println(jsonMsgs);
        pw.flush();
    }

    private String getChattedUsers(String username) {
        List<User> chattedUsers = messageService.getChattedUsersByCurrentUser(username);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(chattedUsers);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
