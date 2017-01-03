package com.digital;

import com.digital.Filter.*;
import com.digital.Utils.Loader;
import com.digital.Utils.Utils;
import com.digital.histogram.Histogram_Image;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Denis on 07.10.2016.
 * Класс контроллер для главного окна приложения
 */
public class RootController implements Initializable{

    //загружаемое изображение
    public Image image;

    //Доступ к главному окну
    private Main main = new Main();

    //обеспечивает отображение изображений
    public ImageView imageView;

    //Центральная панель главного окна
    public BorderPane paneCenter;

    //Класс фильтрации
    private MadianFilter medianFilter;

    //Класс для наложения негатива изображения
    private Negative negativeFilter;

    //Класс наложения степенного преобразования
    private DegreeTransform degreeTransform;

    //Проверки загрузки изображения
    private boolean load = false;

    //Путь к файлу(изображению)
    private String pathFile;

    //Проверка вкл панели для степенного преобразования
    //private boolean isPaneDown = false;

    //Изображение для обработки
    private BufferedImage dublicatImage ;

    private BufferedImage startImage;
    private BufferedImage start_2_Image;
    private BufferedImage neg_Image;
    private BufferedImage deegre_Image;
    private BufferedImage threshold_Image;
    private BufferedImage median_Image;
    private BufferedImage histogram_Image;
    private BufferedImage erode_image;
    private BufferedImage dilate_image;
    private BufferedImage open_image;
    private BufferedImage close_image;
    private BufferedImage outline_image;

    private boolean vis = false;

    //Гистограмма
    private Histogram_Image histogramImage ;
    private CategoryAxis xAxis ;
    private NumberAxis yAxis ;
    private LineChart<String, Number> chartHistogram ;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private Pane pane;

    //Кнопка сохранения изображения
    @FXML
    private MenuItem miSave;

    //Главный Layout приложения
    @FXML
    private BorderPane rootPane;

    //Menu "Изображение"
    @FXML
    private Menu menuImage;

    //Кнопка "Гистограммы изображения"
    @FXML
    private MenuItem miHistogram;

    //Нижняя панель для степенного преобразования
    @FXML
    private AnchorPane paneDownDegree;

    //Кнопка выполнения степенного преобразования
    @FXML
    private Button btnTrasform;

    //поле для ввода степени при степенном преобразовании
    @FXML
    private TextField tfDegree;

    //down панель главного меню
    @FXML
    private AnchorPane downPanel;

    @FXML
    private Button btnTransform;

    @FXML
    private Button btnTheshold;

    @FXML
    private Button btnOK;


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Открытие файла
    public void open(ActionEvent actionEvent) throws IOException {
        
        try {
            FileChooser fc = new FileChooser();
            fc.setTitle("Открыть файл..");
            // Задаём фильтр расширений
            FileChooser.ExtensionFilter textFilter = new FileChooser.ExtensionFilter(
                    "Image files (*.png)", "*.png");
            FileChooser.ExtensionFilter textFilter2 = new FileChooser.ExtensionFilter(
                    "Image files (*.jpg)", "*.jpg");
            FileChooser.ExtensionFilter textFilter3 = new FileChooser.ExtensionFilter(
                    "Image files (*.bmp)", "*.bmp");
            fc.getExtensionFilters().addAll(textFilter3, textFilter, textFilter2);

            //Установка начальной директории
            fc.setInitialDirectory(new File("C:\\Users\\Denis\\IdeaProjects\\Image_Processing\\res\\images"));

            // Показываем диалог загрузки файла
            File selectedFile = fc.showOpenDialog(null);

            //Если пользователь ничего не выбрал возвращается null
            if (selectedFile == null) {
                return;
                //Иначе выполняем блок действий ниже
            } else {

                 pathFile = selectedFile.getPath();

                //Создаю изобжение по выбранному пути
                image = new Image("file:" + pathFile);
                startImage = Loader.loadImage(pathFile);
                start_2_Image = Loader.loadImage(pathFile);



                //Преобразование Image(javaFX) в BufferedImage
                //BufferedImage bufferedImage = Utils.imageToBufferd(image);

                imageView = new ImageView(image);
                miSave.setDisable(false);

                imageView.setFitWidth(image.getWidth());
                imageView.setFitHeight(image.getHeight());

                //Проверка : Если изображение больше (по высоте или ширине)сцены -> FullScreen
                if ((image.getWidth() > main.getPrimaryStage().getWidth() ||
                        (image.getHeight() > main.getPrimaryStage().getHeight()))) {
                    //Установка FullScreen
                    main.getPrimaryStage().setFullScreen(true);

                }

                paneCenter = new BorderPane();
                paneCenter.setCenter(imageView);

                //Добавляю изображение на главную форму в центр
                rootPane.setCenter(paneCenter);


                //Отображаю путь к выбранному файлу\
                main.getPrimaryStage().setTitle(pathFile);

                load = true;
                //disHisto = true;

                //вкл меню "Изображение"
               menuImage.setDisable(false);

                //вкл меню "Изображение"
                //miHistogram.setDisable(false);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Ошибка");
            alert.setContentText("Невозможно открыть файл! ");
            alert.showAndWait();
        }
    }


    //Выход
    public void onExit(ActionEvent actionEvent) {
        System.exit(0);
    }


    //Вызов окна при нажатии  "Помощь"
    public void onAbout(ActionEvent actionEvent) {

        try {
            Stage aboutStage = new Stage();
            aboutStage.setTitle("О разработчиках");
            aboutStage.setResizable(false);
            AnchorPane aboutPane = FXMLLoader.load(getClass().getResource("view/aboutPanel.fxml"));
            aboutStage.setScene(new Scene(aboutPane));
            aboutStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Возвращает загруженное изображение
    public Image getImage() {
        return image;
    }


    //Действие при нажатии кнопки "Медианный фильтр"
    public void medianFilter(ActionEvent actionEvent) {
            hide_downPanel(false);
            median_Image = startImage;
            medianFilter = new MadianFilter(median_Image);
            medianFilter.addMedianFilter(median_Image);
            Image newImage = Utils.bufferdToimageFX(median_Image);
            imageView.setImage(newImage);
            paneCenter.setCenter(imageView);



    }

    /**
     * Построение гистограммы загруженного изображения
     **/
    public void createHistogram_Alfa(ActionEvent actionEvent) {
        hide_downPanel(false);

        histogram_Image = startImage;

        histogramImage = new Histogram_Image(histogram_Image);
        histogramImage.createGistogram();

        xAxis = new CategoryAxis();
        xAxis.setLabel("Яркость");

        yAxis = new NumberAxis();
        yAxis.setLabel("Количество пикселей");

        chartHistogram = new LineChart<>(xAxis, yAxis);
        chartHistogram.setTitle("Гистограмма изображения");
        chartHistogram.setCreateSymbols(false);

        chartHistogram.getData().clear();
        chartHistogram.getData().add(histogramImage.getSeriesAlpha());

        paneCenter.setCenter(chartHistogram);

        }

    public void createHistogram_Red(ActionEvent actionEvent) {

        hide_downPanel(false);

        dublicatImage = Utils.imageToBufferd(getImage());
        histogramImage = new Histogram_Image(dublicatImage);
        histogramImage.createGistogram();

        xAxis = new CategoryAxis();
        xAxis.setLabel("Red");

        yAxis = new NumberAxis();
        yAxis.setLabel("Количество пикселей");

        chartHistogram = new LineChart<>(xAxis, yAxis);
        chartHistogram.setTitle("Гистограмма изображения");
        chartHistogram.setCreateSymbols(false);

        chartHistogram.getData().clear();
        chartHistogram.getData().add(histogramImage.getSeriesRed());

        paneCenter.setCenter(chartHistogram);
    }

    public void createHistogram_Blue(ActionEvent actionEvent) {

        hide_downPanel(false);

        dublicatImage = Utils.imageToBufferd(getImage());
        histogramImage = new Histogram_Image(dublicatImage);
        histogramImage.createGistogram();

        xAxis = new CategoryAxis();
        xAxis.setLabel("Blue");

        yAxis = new NumberAxis();
        yAxis.setLabel("Количество пикселей");

        chartHistogram = new LineChart<>(xAxis, yAxis);
        chartHistogram.setTitle("Гистограмма изображения");
        chartHistogram.setCreateSymbols(false);

        chartHistogram.getData().clear();
        chartHistogram.getData().add(histogramImage.getSeriesBlue());

        paneCenter.setCenter(chartHistogram);
    }

    public void createHistogram_Green(ActionEvent actionEvent) {

        hide_downPanel(false);

        dublicatImage = Utils.imageToBufferd(getImage());
        histogramImage = new Histogram_Image(dublicatImage);
        histogramImage.createGistogram();

        xAxis = new CategoryAxis();
        xAxis.setLabel("Green");

        yAxis = new NumberAxis();
        yAxis.setLabel("Количество пикселей");

        chartHistogram = new LineChart<>(xAxis, yAxis);
        chartHistogram.setTitle("Гистограмма изображения");
        chartHistogram.setCreateSymbols(false);

        chartHistogram.getData().clear();
        chartHistogram.getData().add(histogramImage.getSeriesGreen());

        paneCenter.setCenter(chartHistogram);
    }

    public void createHistogram_All(ActionEvent actionEvent) {

        hide_downPanel(false);

        dublicatImage = Utils.imageToBufferd(getImage());
        histogramImage = new Histogram_Image(dublicatImage);
        histogramImage.createGistogram();


        xAxis = new CategoryAxis();
        //xAxis.setLabel("Яркость");

        yAxis = new NumberAxis();
        yAxis.setLabel("Количество пикселей");

        chartHistogram = new LineChart<>(xAxis, yAxis);
        chartHistogram.setTitle("Гистограмма изображения");
        chartHistogram.setCreateSymbols(false);

        chartHistogram.getData().clear();
        chartHistogram.getData().addAll(histogramImage.getSeriesAlpha(),histogramImage.getSeriesRed(),
                histogramImage.getSeriesGreen(),histogramImage.getSeriesBlue());

        paneCenter.setCenter(chartHistogram);
    }


    //Метод для наложения негатива на изображение
    public void onNegative(ActionEvent actionEvent) {
        hide_downPanel(false);
//        BufferedImage dublImage = Utils.imageToBufferd(getImage());
//        negativeFilter = new Negative(dublImage);
//        negativeFilter.negative();
//        Image newImage = Utils.bufferdToimageFX(dublImage);
//        imageView.setImage(newImage);
//        paneCenter.setCenter(imageView);
        neg_Image = startImage;
        negativeFilter = new Negative(neg_Image);
        negativeFilter.negative();
        Image newImage = Utils.bufferdToimageFX(neg_Image);
        imageView.setImage(newImage);
        paneCenter.setCenter(imageView);

    }

    //Метод степенного преобразования
    public void onDegree(ActionEvent actionEvent) {
        hide_downPanel(true);
        tfDegree.setText("");

    }


    //Метод выполнения степенного преобразования
    public void onTransform(ActionEvent actionEvent) {


            String text = tfDegree.getText();
            double gamma = Double.parseDouble(text);

//            BufferedImage img = Utils.imageToBufferd(getImage());
//            degreeTransform = new DegreeTransform(img,gamma);
//            degreeTransform.transform();
//            Image newImage = Utils.bufferdToimageFX(img);
//            imageView.setImage(newImage);
//            paneCenter.setCenter(imageView);
        deegre_Image = startImage;
        degreeTransform = new DegreeTransform(deegre_Image,gamma);
            degreeTransform.transform();
            Image newImage = Utils.bufferdToimageFX(deegre_Image);
            imageView.setImage(newImage);
            paneCenter.setCenter(imageView);


    }


    // Вызывается при запуске программы, вначале всех методов.
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    // Метод для порогового фильтра. Вызывается при нажатии на MenuItem.
    public void onThreshold(ActionEvent actionEvent) {

        hide_downPanel(false);
        Stage stage = new Stage();
        AnchorPane anchtor = new AnchorPane();
        Label label = new Label("Введите порог:    ");
        label.setLayoutX(14.0);
        label.setLayoutY(25);
        TextField tf = new TextField();
        tf.setLayoutX(134);
        tf.setLayoutY(23);
        tf.setPrefWidth(46);
        tf.setPrefHeight(25);
        Button button = new Button("Выполнить");
        button.setPrefWidth(171);
        button.setPrefHeight(33);
        button.setLayoutX(14);
        button.setLayoutY(66);
        anchtor.getChildren().addAll(label,tf,button);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int tmp = Integer.parseInt(tf.getText());
                threshold_Image = startImage;
                ThresholdFilter thresholdFilter = new ThresholdFilter(threshold_Image,tmp);
                thresholdFilter.activeFilter();
                Image newImage = Utils.bufferdToimageFX(threshold_Image);
                imageView.setImage(newImage);
                paneCenter.setCenter(imageView);
                stage.hide();
            }
        });

        stage.setScene(new Scene(anchtor,196,116));
        stage.setTitle("Пороговая фильтрация");
        stage.setResizable(false);
        stage.getIcons().add(new Image("com/digital/dialog_ico.png"));
        stage.showAndWait();












    }

    //Сброс фильтра
    public void clearFilter(ActionEvent actionEvent) {
        startImage = start_2_Image;
        imageView.setImage(Utils.bufferdToimageFX(startImage));
        hide_downPanel(false);
        paneCenter.setCenter(imageView);
    }

    //Выключение нижней панели гланого окна
    public  void hide_downPanel(boolean flag){
        downPanel.setVisible(flag);
    }

    public String getPathFile(){
        return pathFile;
    }


    //Морфология - эрозия
    public void On_erode(ActionEvent actionEvent) {
        hide_downPanel(false);
        erode_image =startImage;
        ErodeFilter dilation = new ErodeFilter(erode_image);
        //erode_image = dilation.erode();
        Image newImage = Utils.bufferdToimageFX(dilation.erode());
        imageView.setImage(newImage);
        paneCenter.setCenter(imageView);

    }

    //Морфология - дилатация
    public void on_dilate(ActionEvent actionEvent) {
        hide_downPanel(false);
        dilate_image = startImage;
        DilateFilter dilateFilter = new DilateFilter(dilate_image);
        //dilate_image = dilateFilter.dilate();
        Image newImage = Utils.bufferdToimageFX(dilateFilter.dilate());
        imageView.setImage(newImage);
        paneCenter.setCenter(imageView);
    }

    //Морфология - размыкание
    public void on_open(ActionEvent actionEvent) {
        hide_downPanel(false);
        open_image = startImage;
        OpenFilter openFilter = new OpenFilter(open_image);
        open_image = openFilter.open();
        Image newImage = Utils.bufferdToimageFX(open_image);
        imageView.setImage(newImage);
        paneCenter.setCenter(imageView);
    }

    //Морфология - замыкание
    public void on_close(ActionEvent actionEvent) {
        hide_downPanel(false);
        close_image = startImage;
        CloseFilter closeFilter = new CloseFilter(close_image);
        close_image = closeFilter.close();
        Image newImage = Utils.bufferdToimageFX(close_image);
        imageView.setImage(newImage);
        paneCenter.setCenter(imageView);
    }

    public void on_outline(ActionEvent actionEvent) {
        hide_downPanel(false);
        outline_image = startImage;
        Outline outline = new Outline(outline_image);
        outline_image = outline.allocate();
        Image newImage = Utils.bufferdToimageFX(outline_image);
        imageView.setImage(newImage);
        paneCenter.setCenter(imageView);
    }

    public void onOK(ActionEvent actionEvent) {
    }

//    Сохранение изображения
    public void onSavePicture(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        //Класс работы с диалогом выборки и сохранения
         fileChooser.setTitle("Save Document");
        // Заголовок диалога
        FileChooser.ExtensionFilter extFilter =  new FileChooser.ExtensionFilter("Picture files (*.jpeg)", "*.jpeg");
        //Расширение
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(null);
        String path = null;
        //Указываем текущую сцену
        Image image = imageView.getImage();
        BufferedImage img = Utils.imageToBufferd(image);
         if (file != null) {
             try {
                 path = file.getPath();
                 ImageIO.write(img, "png", new File(path));
             } catch (IOException e) {
                 e.printStackTrace();
             }
             //System.out.println(path);
         }
        }


}

