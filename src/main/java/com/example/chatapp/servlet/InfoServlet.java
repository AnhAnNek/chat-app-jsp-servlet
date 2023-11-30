package com.example.chatapp.servlet;

import com.example.chatapp.model.User;
import com.example.chatapp.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/info")
public class InfoServlet extends HttpServlet {

    private UserServiceImpl userService;

    public InfoServlet() {
        userService = UserServiceImpl.getIns();
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
//            List<User> customers = MessageService.getIns()
//                    .getChattedUsersByCurrentUser(username);
//            List<ChatMessage> userMessages = MessageService.getIns()
//                    .getMessagesByReceiver(username, "queanpham");
//
//            req.setAttribute("customers", customers);
//            req.setAttribute("userMessages", userMessages);

            url = "/WEB-INF/views/salesperson-chatbox.jsp";
        } else {
            url = "/WEB-INF/views/customer-main-page.jsp";
        }
        getServletContext().getRequestDispatcher(url).forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = "/WEB-INF/views/input.jsp";
        getServletContext().getRequestDispatcher(url).forward(req, resp);
    }
}
