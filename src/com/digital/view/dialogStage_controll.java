package com.digital.view;

import com.digital.RootController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Created by Denis on 16.12.2016.
 */
public class dialogStage_controll {

    private RootController rootController;

    @FXML
    private Button dsBtb;
    @FXML
    private TextField dsTf;

    @FXML
    void onDsBtn(ActionEvent event) {
        int temp = Integer.parseInt(dsTf.getText());

    }
}
