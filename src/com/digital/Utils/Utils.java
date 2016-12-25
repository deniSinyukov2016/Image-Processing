package com.digital.Utils;

import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;

/**
 * Created by Denis on 16.10.2016.
 */
public class Utils {


    //Преобразование Image(javaFX) в BufferedImage
    public static BufferedImage imageToBufferd(Image image ){
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image,null);
        return bufferedImage;
    }

    //Преобразование BufferedImage  в Image(javaFX)
    public static Image bufferdToimageFX(BufferedImage image ){
        Image imagefx = SwingFXUtils.toFXImage(image,null);
        return imagefx;
    }

    //Преобразование IplImage в BufferedImage
    public static BufferedImage IplImageToBufferedImage(opencv_core.IplImage src) {
        OpenCVFrameConverter.ToIplImage grabberConverter = new OpenCVFrameConverter.ToIplImage();
        Java2DFrameConverter paintConverter = new Java2DFrameConverter();
        org.bytedeco.javacv.Frame frame = grabberConverter.convert(src);
        return paintConverter.getBufferedImage(frame,1);

    }

    //Преобразование BufferedImage в IplImage
    public static opencv_core.IplImage toIplImage(BufferedImage bufImage) {

        OpenCVFrameConverter.ToIplImage iplConverter = new OpenCVFrameConverter.ToIplImage();
        Java2DFrameConverter java2dConverter = new Java2DFrameConverter();
        opencv_core.IplImage iplImage = iplConverter.convert(java2dConverter.convert(bufImage));
        return iplImage;
    }
}
