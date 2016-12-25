package com.digital.Filter;

import com.digital.Utils.Loader;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Denis on 01.11.2016.
 */
public class DegreeTransform {


    private  BufferedImage img ;
    private double gamma ;


    public DegreeTransform(BufferedImage img,double gamma) {
        this.img = img;
        this.gamma = gamma;

    }

    public void transform(){
        int w = this.getWidth();
        int h = this.getHeight();
        int r,g,b;
        for (int i = 0; i <w ; i++) {
            for (int j = 0; j <h ; j++) {
                int pixel = img.getRGB(i,j);
                //создание нового цвета  на основе полученного пикселя
                Color c  = new Color(pixel);
                //получение красного цвета
                r = c.getRed();
                //получение синего цвета
                g = c.getBlue();
                //получение зеленого цвета
                b = c.getGreen();
                //получение яркости пикселя
                int alpha = (int) (0.299*r + 0.587*g + 0.114*b);
                //возведение яркости в степень по формуле
                alpha = (int) (1*(Math.pow(alpha + 0,gamma) * 255)/Math.pow(255,gamma));
                //установление нового RGB
                int newRGB = this.getRGB(alpha, alpha, alpha);
                img.setRGB(i,j,newRGB);

                //System.out.print(alpha+" ");

            }
        }
    }


    private int getRGB(int red, int green, int blue) {
        Color newColor = new Color(red, green, blue);
        return newColor.getRGB();
    }

    int getWidth() {
        return img.getWidth();
    }

    int getHeight() {
        return img.getHeight();
    }

    public double getGamma() {
        return gamma;
    }


}
