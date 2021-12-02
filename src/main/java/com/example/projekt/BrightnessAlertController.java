package com.example.projekt;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

public class BrightnessAlertController {
    @FXML Slider brightness;
    @FXML Button confirm;

    @FXML private void confirm(){
        ((Stage)(confirm.getScene().getWindow())).close();
    }

    public double getData(){
        return brightness.getValue();
    }

}
