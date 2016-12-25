package com.digital;


//import com.digital.view.BottomController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javafx.scene.image.Image;

import java.io.IOException;

/**
 * Created by Denis on 07.10.2016.
 */
public class Main extends Application {

    private static BorderPane rootPane;
    public static Stage primaryStage;

    private RootController rootController;


    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Цифроая обработка изображений");
        //Загрузка иконки приложения
        this.primaryStage.getIcons().add(new Image("com/digital/ico.png"));
        initRoot();
    }

    private void initRoot() {

        try {
            rootPane = FXMLLoader.load(getClass().getResource("main.fxml"));
            primaryStage.setScene(new Scene(rootPane));
           // initLeft();

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    public void initBottom_2() {
//
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/bottom.fxml"));
//            AnchorPane pane = (AnchorPane)loader.load();
//            rootPane.setBottom(pane);
//            BottomController controller = loader.getController();
//            controller.setMainApp(this);
//            controller.setStage(primaryStage);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    public AnchorPane initLeft(){
        try {

            AnchorPane ancPane = FXMLLoader.load(getClass().getResource("leftPanel2.fxml"));

            return ancPane;
           // rootPane.getLeft().setVisible(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }


    public BorderPane getRootPane() {
        return rootPane;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
