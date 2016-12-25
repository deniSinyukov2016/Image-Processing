package com.digital.Filter;

import com.digital.Utils.Utils;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgproc;

import java.awt.image.BufferedImage;

import static org.bytedeco.javacpp.opencv_core.cvCloneImage;

/**
 * Created by Denis on 08.12.2016.
 */
public class CloseFilter  extends opencv_imgproc {
    private BufferedImage img;

    public CloseFilter(BufferedImage img) {
        this.img = img;

    }


    public  BufferedImage close() {
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

        opencv_core.IplImage i = cvCloneImage(image);

        cvMorphologyEx(image,i,null,mat,CV_MOP_CLOSE,1);
        //cvMorphologyEx(image,i,null,mat,MORPH_OPEN,1);
        //обнуляет структурный элемент
        cvReleaseStructuringElement(mat);
        // Преобразование в BufferedImage
        BufferedImage img = Utils.IplImageToBufferedImage(i);
        return img;

    }
}
