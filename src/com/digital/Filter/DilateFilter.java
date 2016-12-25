package com.digital.Filter;

import com.digital.Utils.Utils;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgproc;

import java.awt.image.BufferedImage;

/**
 * Created by Denis on 08.12.2016.
 */
public class DilateFilter extends opencv_imgproc {
    private BufferedImage img;

    public DilateFilter(BufferedImage img) {
        this.img = img;

    }


    public  BufferedImage dilate() {
        //Преобразование в IplImage(javaCV)
        opencv_core.IplImage image = Utils.toIplImage(img);
        /*Создание структурного элемента
        * Аргументы:
        * 1 - кол-во колонок
        * 2 - кол-во строк
        * 3 - смещение относительно x
        * 4 - 3 - смещение относительно y
        * 5 - форма элемента
        * CV_SHAPE_RECT - прямоугольник
          CV_SHAPE_CROSS, - крестообразный элемент
          CV_SHAPE_ELLIPSE, - эллипс
          CV_SHAPE_CUSTOM, - пользовательский*/
        opencv_core.IplConvKernel mat = cvCreateStructuringElementEx(3,3,1,1,CV_SHAPE_RECT);
        /*Функция дилатации
        * Аргумнты:
        * 1 - исх. изобр
        * 2 - преобр. изобр
        * 3 - кол-во итераций дилатации*/
        cvDilate(image, image, mat, 1);
        //обнуляет структурный элемент
        cvReleaseStructuringElement(mat);
        // Преобразование в BufferedImage
        BufferedImage img = Utils.IplImageToBufferedImage(image);
        return img;

    }
}
