package com.example.projekt;

import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Dimension2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

public class MainController {
    @FXML BorderPane scene;
    @FXML StackPane imagePane;
    @FXML ImageView imageFrame;
    @FXML VBox imagesPreview;

    FileChooser chooser = new FileChooser();
    FileChooser.ExtensionFilter imagesFilter = new FileChooser.ExtensionFilter("Images","*.jpg","*.png");
    List<File> inputFiles;
    ImageHandler imageEventHandler = new ImageHandler();
    HostServices browser;

    public void setHostServices(HostServices services){
        this.browser=services;
    }

    @FXML private void initialize(){
        chooser.getExtensionFilters().add(imagesFilter);
        imageFrame.fitHeightProperty().bind(scene.heightProperty());
        imageFrame.fitWidthProperty().bind(imagePane.widthProperty());
    }

    @FXML private void loadImages(){
        inputFiles = chooser.showOpenMultipleDialog(scene.getScene().getWindow());
        if (inputFiles != null) {
            for(File f : inputFiles){
                ImageView img = new ImageView(new Image(f.toURI().toString(),160,100,true,false));
                img.addEventHandler(MouseEvent.MOUSE_CLICKED,imageEventHandler);
                img.setCursor(Cursor.HAND);
                VBox wrapper = new VBox();
                wrapper.getChildren().add(img);
                wrapper.setMaxWidth(img.getFitWidth());
                wrapper.getStyleClass().add("preview");
                imagesPreview.getChildren().add(wrapper);
            }
        }
    }

    @FXML private void saveImage(){
        chooser.showSaveDialog(scene.getScene().getWindow());
    }

    @FXML private void exit(){
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.initOwner(scene.getScene().getWindow());
        confirmation.setTitle("Confirmation");
        confirmation.setHeaderText("Are you sure you want to close the application?");
        confirmation.setContentText("All unsaved data will be lost.");
        Optional<ButtonType> result = confirmation.showAndWait();
        if(result.get() == ButtonType.OK){
            Platform.exit();
            System.exit(0);
        }
    }

    @FXML private void blur(){
        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(5);
        imageFrame.setEffect(blur);
    }

    @FXML private void clearEffects(){
        imageFrame.setEffect(null);
    }

    @FXML private void pickBoxBlur() throws IOException {
        URL xml = getClass().getResource("BoxBlurGUI.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(xml);
        Scene scene = new Scene(fxmlLoader.load());
        BlurAlertController controller = fxmlLoader.getController();

        Stage blurModal = new Stage();
        blurModal.initOwner(this.scene.getScene().getWindow());
        blurModal.setScene(scene);
        blurModal.showAndWait();

        Dimension2D tmp = controller.getData();
        BoxBlur blur = new BoxBlur();
        blur.setInput(imageFrame.getEffect());
        blur.setWidth(tmp.getWidth());
        blur.setHeight(tmp.getHeight());
        blur.setIterations(1);

        imageFrame.setEffect(blur);
    }

    @FXML private void convertToGrayscale(){
        ColorAdjust filter = new ColorAdjust();
        filter.setInput(imageFrame.getEffect());
        filter.setSaturation(-1);
        imageFrame.setEffect(filter);
    }

    @FXML private void changeBrightness() throws IOException {
        URL xml = getClass().getResource("BrightnessGUI.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(xml);
        Scene scene = new Scene(fxmlLoader.load());
        BrightnessAlertController controller = fxmlLoader.getController();

        Stage brightnessModal = new Stage();
        brightnessModal.initOwner(this.scene.getScene().getWindow());
        brightnessModal.setScene(scene);
        brightnessModal.showAndWait();

        ColorAdjust filter = new ColorAdjust();
        filter.setInput(imageFrame.getEffect());
        filter.setBrightness(controller.getData());

        imageFrame.setEffect(filter);
    }

    @FXML private void addSepiaEffect(){
        SepiaTone filter = new SepiaTone();
        filter.setLevel(0.6f);

        imageFrame.setEffect(filter);
    }

    @FXML private void showAbout(){
        browser.showDocument("https://docs.oracle.com/javafx/2/overview/jfxpub-overview.htm");
    }

    @FXML private void showDocumentation(){
        browser.showDocument("https://openjfx.io/openjfx-docs/");
    }

    @FXML private void showHelp(){
        browser.showDocument("https://www.google.com");
    }

    class ImageHandler implements EventHandler{
        @Override
        public void handle(Event e) {
            ImageView source = (ImageView)e.getSource();
            imageFrame.setImage(new Image(source.getImage().getUrl()));
        }
    }

}
