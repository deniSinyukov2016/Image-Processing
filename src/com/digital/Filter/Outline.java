package com.digital.Filter;

import com.digital.Utils.Utils;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgproc;

import java.awt.image.BufferedImage;

import static org.bytedeco.javacpp.opencv_core.*;

/**
 * Created by Denis on 08.12.2016.
 */
public class Outline extends opencv_imgproc {
    private BufferedImage img;

    public Outline(BufferedImage img) {
        this.img = img;

    }


    public  BufferedImage allocate() {
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
        opencv_core.IplConvKernel mat = cvCreateStructuringElementEx(5,5,3,3,CV_SHAPE_RECT);
        /*Функция дилатации
        * Аргумнты:
        * 1 - исх. изобр
        * 2 - преобр. изобр
        * 3 - кол-во итераций дилатации*/
        IplImage gray = cvCreateImage( cvGetSize(image), IPL_DEPTH_8U, 1 );
        IplImage  dst = cvCreateImage( cvGetSize(image), IPL_DEPTH_8U, 1 );
        // преобразуем в градации серого
        cvCvtColor(image, gray, CV_RGB2GRAY);

        // получаем границы
        cvCanny(gray, dst, 10, 100, 3);

        opencv_core.IplImage i = cvCloneImage(image);
        //cvCanny(image,i,10,100,3);
        //cvMorphologyEx(image,i,null,mat,CV_MOP_GRADIENT,1);
        //обнуляет структурный элемент
        cvReleaseStructuringElement(mat);
        // Преобразование в BufferedImage
        BufferedImage img = Utils.IplImageToBufferedImage(dst);
        return img;

    }
}
