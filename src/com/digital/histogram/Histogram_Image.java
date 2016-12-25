package com.digital.histogram;
import javafx.scene.chart.XYChart;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Created by Denis on 17.10.2016.
 */
public class Histogram_Image {

    private XYChart.Series seriesAlpha;
    private XYChart.Series seriesRed;
    private XYChart.Series seriesGreen;
    private XYChart.Series seriesBlue;

    private BufferedImage img;

    private int[] hist = new int[256];
    private int[] hist_r = new int[256];
    private int[] hist_g = new int[256];
    private int[] hist_b = new int[256];

    private  int max_hist = hist[0];

//    public Histogram_Image() {
//       img = Loader.loadImage("res/images/rentgen.bmp");
//    }

    public Histogram_Image(BufferedImage img) {
        this.img = img;

        seriesAlpha = new XYChart.Series();
        seriesAlpha.setName("яркость");

        seriesRed = new XYChart.Series();
        seriesRed.setName("Red");

        seriesGreen = new XYChart.Series();
        seriesGreen.setName("Green");

        seriesBlue = new XYChart.Series();
        seriesBlue.setName("Blue");


    }

    public void createGistogram() {
        int width = img.getWidth();
        int height = img.getHeight();
        int r, g, b;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int pixel = img.getRGB(i, j);
                //создание нового цвета  на основе полученного пикселя
                Color c = new Color(pixel);
                //получение красного цвета
                r = c.getRed();
                //получение синего цвета
                g = c.getBlue();
                //получение зеленого цвета
                b = c.getGreen();
                //получение яркости пикселя
                int alpha = (int) (0.299 * r + 0.587 * g + 0.114 * b);
                //Инкримент значений яркости.  Сколько пикселей имеют яркость от 0 до 255 по всем 256 значениям.
                hist[alpha]++;
                hist_r[r]++;
                hist_g[g]++;
                hist_b[b]++;
            }

        }
        for (int i = 0; i <hist.length ; i++) {
            if(hist[i] > getMax_hist())
                max_hist = hist[i];
        }
        //Заполнение данными
        for (int i = 0; i <256 ; i++) {
            seriesAlpha.getData().add(new XYChart.Data(String.valueOf(i), hist[i]));
            seriesRed.getData().add(new XYChart.Data(String.valueOf(i), hist_r[i]));
            seriesGreen.getData().add(new XYChart.Data(String.valueOf(i), hist_g[i]));
            seriesBlue.getData().add(new XYChart.Data(String.valueOf(i), hist_b[i]));
        }
    }


    public XYChart.Series getSeriesAlpha() {
        return seriesAlpha;
    }

    public XYChart.Series getSeriesRed() {
        return seriesRed;
    }
    public XYChart.Series getSeriesGreen() {
        return seriesGreen;
    }
    public XYChart.Series getSeriesBlue() {
        return seriesBlue;
    }

    public int getMax_hist() {
        return max_hist;
    }
}
