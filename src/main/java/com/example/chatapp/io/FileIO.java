package com.example.chatapp.io;

import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;

public interface FileIO {
    BufferedImage readImage(String path);
    void writeImage(BufferedImage bufferedImage, String formatName, String outputPath);
}
