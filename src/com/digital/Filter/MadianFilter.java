package com.digital.Filter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * Created by Denis on 16.10.2016.
 */

/**Медианный фильтр. Захватывает цвет из 8 пикселей вокруг целевого пикселя.В том числе целевого пикселя ,итого будет 9 пикселей.
  * Изолирует R,G,B значения каждого пикселя и вносит их в массив.Сортирует массивы .Получает среднее значение массива
  * Это будет медиана значений цвета в этих 9 пикселей.Устанавливает цвет для целевого пикселя и двигается дальше*/

public class MadianFilter  {

    private BufferedImage originalImage;

    public MadianFilter(BufferedImage originalImage) {
        this.originalImage = originalImage;
    }

    public BufferedImage addMedianFilter(BufferedImage img) {

        Color[] pixel=new Color[9];
        int[] R=new int[9];
        int[] B=new int[9];
        int[] G=new int[9];

        for(int i=1;i<img.getWidth()-1;i++)
            for(int j=1;j<img.getHeight()-1;j++)
            {
                pixel[0]=new Color(img.getRGB(i-1,j-1));//0 0
                pixel[1]=new Color(img.getRGB(i-1,j));  //0 1
                pixel[2]=new Color(img.getRGB(i-1,j+1));//0 2
                pixel[3]=new Color(img.getRGB(i,j+1));  //1 2
                pixel[4]=new Color(img.getRGB(i+1,j+1));//2 2
                pixel[5]=new Color(img.getRGB(i+1,j));  //2 1
                pixel[6]=new Color(img.getRGB(i+1,j-1));//2 0
                pixel[7]=new Color(img.getRGB(i,j-1));  //1 0
                pixel[8]=new Color(img.getRGB(i,j));    //1 1
                for(int k=0;k<9;k++){
                    R[k]=pixel[k].getRed();
                    B[k]=pixel[k].getBlue();
                    G[k]=pixel[k].getGreen();
                }
                Arrays.sort(R);
                Arrays.sort(G);
                Arrays.sort(B);
                img.setRGB(i,j,new Color(R[4],B[4],G[4]).getRGB());
            }
      return img;
    }
}

