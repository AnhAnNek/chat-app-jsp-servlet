package com.example.chatapp.servlet;

import com.example.chatapp.service.ImageService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@WebServlet("/test-load-image")
public class TestLoadImageServlet extends HttpServlet {

    private ImageService imageService;

    @Override
    public void init() throws ServletException {
        super.init();
        imageService = new ImageService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/test-load-image.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String appendPath = req.getParameter("appendPath");
        BufferedImage bi = imageService.readImage(getServletContext(), appendPath);

        String format = imageService.getFileExtension(appendPath);
        String base64Image = imageService.convertToBase64(bi, format);
        req.setAttribute("base64Image", base64Image);

        getServletContext().getRequestDispatcher("/test-load-image.jsp").forward(req, resp);
    }
}
