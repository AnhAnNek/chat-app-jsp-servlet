package com.example.chatapp.servlet;

import com.example.chatapp.model.ChatMessage;
import com.example.chatapp.model.User;
import com.example.chatapp.service.MessageService;
import com.example.chatapp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/info")
public class InfoServlet extends HttpServlet {

    private UserService userService;

    public InfoServlet() {
        userService = UserService.getIns();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");

        User user = userService.getByUsername(username);
        if (user == null) {
            user = new User(username, User.ERole.CUSTOMER);
        }

        req.setAttribute("user", user);

        String url;
        if (user.getRole().equals(User.ERole.SALESPERSON)) {
            List<User> customers = MessageService.getIns()
                    .getChattedUsersByCurrentUser(username);
            List<ChatMessage> userMessages = MessageService.getIns()
                    .getMessagesByReceiver(username, "queanpham");

            req.setAttribute("customers", customers);
            req.setAttribute("userMessages", userMessages);

            url = "/salesperson-chatbox.jsp";
        } else {
            url = "/customer-chatbox.jsp";
        }
        getServletContext().getRequestDispatcher(url).forward(req, resp);
    }
}
