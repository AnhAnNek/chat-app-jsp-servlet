package com.example.chatapp.io;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileIOImpl implements FileIO {

    @Override
    public BufferedImage readImage(String path) {
        try {
            File file = new File(path);
            return ImageIO.read(file);
        } catch (IOException e) {
            System.out.println("Error" + e.getMessage());
            return null;
        }
    }

    @Override
    public void writeImage(BufferedImage bufferedImage, String formatName, String outputPath) {
        try {
            File outputFile = new File(outputPath);
            ImageIO.write(bufferedImage, formatName, outputFile);
        } catch (IOException e) {
            System.out.println("Error" + e.getMessage());
        }
    }
}
