package com.example.chatapp.service;

import com.example.chatapp.io.FileIO;
import com.example.chatapp.io.FileIOImpl;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageService {
    private FileIO fileIO;

    public ImageService() {
        fileIO = new FileIOImpl();
    }

    public BufferedImage readImage(ServletContext context, String appendPath) {
        String targetPath = context.getRealPath("/") + appendPath;
        return fileIO.readImage(targetPath);
    }

    public BufferedImage readImage(String fullPath) {
        return fileIO.readImage(fullPath);
    }

    public void writeImage(ServletContext context, BufferedImage bi, String appendPath) {
        String savedPath = context.getRealPath("/") + appendPath;
        String formatName = getFileExtension(appendPath);
        fileIO.writeImage(bi, formatName, savedPath);
    }

    public String convertToBase64(BufferedImage image, String format) {
        String base64Image = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, format, baos);
            byte[] imageBytes = baos.toByteArray();
            base64Image = Base64.getEncoder().encodeToString(imageBytes);
            baos.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return base64Image;
    }

    public String getFileExtension(String filePath) {
        if (filePath != null && !filePath.isEmpty()) {
            int dot = filePath.lastIndexOf('.');
            if (dot > 0 && dot < filePath.length() - 1) {
                return filePath.substring(dot + 1);
            }
        }
        return "";
    }
}
