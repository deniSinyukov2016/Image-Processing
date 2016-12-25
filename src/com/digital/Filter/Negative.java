package com.digital.Filter;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Denis on 25.10.2016.
 */
public class Negative {

    private BufferedImage img;

    public Negative(BufferedImage img) {
        this.img = img;

    }

    //Наложение негатива
    public  void negative() {
        int m = this.getWidth();
        int n = this.getHeight();
        for (int i = 0; i < m ; i++) {
            for (int j = 0; j < n ; j++) {
                int rgb = this.getPixel(i, j);
                int red = this.getRed(rgb);
                int green = this.getGreen(rgb);
                int blue = this.getBlue(rgb);
                int newRed = 255 - red;
                int newGreen = 255 - green;
                int newBlue = 255 - blue;
                int newRGB = this.getRGB(newRed, newGreen, newBlue);
                img.setRGB(i, j, newRGB);
            }
        }
    }
    int getPixel(int x, int y) {
        return img.getRGB(x, y);
    }

    int getRed(int rgb) {
        Color newColor = new Color(rgb);
        return newColor.getRed();
    }

    int getGreen(int rgb) {
        Color newColor = new Color(rgb);
        return newColor.getGreen();
    }

    int getBlue(int rgb) {
        Color newColor = new Color(rgb);
        return newColor.getBlue();
    }

    int getRGB(int red, int green, int blue) {
        Color newColor = new Color(red, green, blue);
        return newColor.getRGB();
    }

    int getWidth() {
        return img.getWidth();
    }

    int getHeight() {
        return img.getHeight();
    }
}
