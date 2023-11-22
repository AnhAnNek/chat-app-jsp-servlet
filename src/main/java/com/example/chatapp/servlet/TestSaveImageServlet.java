package com.example.chatapp.servlet;

import com.example.chatapp.service.ImageService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@WebServlet("/test-save-image")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class TestSaveImageServlet extends HttpServlet {

    private ImageService imageService;

    @Override
    public void init() throws ServletException {
        super.init();
        imageService = new ImageService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part filePart = req.getPart("image");
        String fileName = filePart.getSubmittedFileName();
        String path = getServletContext().getRealPath("/") + "app/prod" + File.separator + fileName;
        filePart.write(path);

//        BufferedImage bi = ImageIO.read(new File(path));
//        imageService.writeImage(getServletContext(), bi, "app/prod/" + fileName);

        getServletContext().getRequestDispatcher("/test-save-image.jsp").forward(req, resp);
    }

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : partHeader.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    private String getFileExtension(String fileName) {
        if (fileName == null) {
            return null;
        }
        int extensionIndex = fileName.lastIndexOf('.');
        if (extensionIndex == -1) {
            return "";
        }
        return fileName.substring(extensionIndex + 1);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/test-save-image.jsp").forward(req, resp);
    }
}
