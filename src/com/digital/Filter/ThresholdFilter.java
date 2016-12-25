package com.digital.Filter;

import com.digital.Utils.Loader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * Created by Denis on 11.11.2016.
 * Пороговая фильтрация изображения
 */
public class ThresholdFilter {

    private BufferedImage img;
    //Черный цвет
    private  static final int BLACK = 0;
    //Белый цвет
    private  static final int WHITE = 255;
    // Порог
    private int threshold;

    public ThresholdFilter(BufferedImage img, int threshold) {
        this.img = img;
        this.threshold = threshold;
    }


    public void activeFilter() {

        int w = img.getWidth();
        int h = img.getHeight();
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

                int newRGB = 0;

                //Проверка на превышение яркости порога
                if(alpha <=  threshold){
                     newRGB = this.getRGB(BLACK, BLACK, BLACK);
                }else {
                    newRGB = this.getRGB(WHITE,WHITE,WHITE);
                }
                //установление нового RGB
                img.setRGB(i,j,newRGB);



            }
        }
    }
    private int getRGB(int red, int green, int blue) {
        Color newColor = new Color(red, green, blue);
        return newColor.getRGB();
    }

}
