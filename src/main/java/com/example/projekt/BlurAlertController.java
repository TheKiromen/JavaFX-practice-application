package com.example.projekt;

import javafx.fxml.FXML;
import javafx.geometry.Dimension2D;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

public class BlurAlertController{
    @FXML Slider blurWidth;
    @FXML Slider blurHeight;
    @FXML Button confirm;

    @FXML private void confirm(){
        ((Stage)(confirm.getScene().getWindow())).close();
    }

    public Dimension2D getData(){
        return new Dimension2D(blurWidth.getValue(),blurHeight.getValue());
    }

}
