package com.example.projekt;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //Load XML and CSS
        URL xml = getClass().getResource("mainGUI.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(xml);
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        MainController controller = fxmlLoader.getController();
        controller.setHostServices(getHostServices());

        //Configure the window
        stage.setTitle("Projekt GUI!");
        stage.getIcons().add(new Image("file:target/classes/Icons/logo.png"));
        stage.setAlwaysOnTop(true);
        stage.setMinWidth(1152);
        stage.setMinHeight(648);
        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                confirmation.initOwner(stage);
                confirmation.setTitle("Confirmation");
                confirmation.setHeaderText("Are you sure you want to close the application?");
                confirmation.setContentText("All unsaved data will be lost.");
                Optional<ButtonType> result = confirmation.showAndWait();
                if(result.get() == ButtonType.OK){
                    Platform.exit();
                    System.exit(0);
                }else{
                    windowEvent.consume();
                }
            }
        });

        //Display the window
        stage.centerOnScreen();
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}