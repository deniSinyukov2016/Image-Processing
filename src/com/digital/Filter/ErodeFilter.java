package com.digital.Filter;


import com.digital.Utils.Utils;
import javafx.scene.image.Image;
import org.bytedeco.javacpp.opencv_core.*;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacv.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.bytedeco.javacpp.opencv_core.cvReleaseImage;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvSaveImage;
import static org.bytedeco.javacpp.opencv_imgproc.cvCreateStructuringElementEx;

/**
 * Created by Denis on 05.12.2016.
 */
public class ErodeFilter extends opencv_imgproc {
    private BufferedImage img;

    public ErodeFilter(BufferedImage img) {
        this.img = img;

    }


    public  BufferedImage erode() {
        //Преобразование в IplImage(javaCV)
        IplImage image = Utils.toIplImage(img);
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
        IplConvKernel mat = cvCreateStructuringElementEx(5,5,3,3,CV_SHAPE_RECT);
        /*Функция эрозии
        * Аргумнты:
        * 1 - исх. изобр
        * 2 - преобр. изобр
        * 3 - кол-во итераций эрозии*/
            cvErode(image, image, mat, 1);
        //обнуляет структурный элемент
        cvReleaseStructuringElement(mat);
        // Преобразование в BufferedImage
            BufferedImage img = Utils.IplImageToBufferedImage(image);
        return img;

        }


}
