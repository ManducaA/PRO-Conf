package it.informatica.configuratore.controller;

import it.informatica.configuratore.others.Common;
import it.informatica.configuratore.threads.PdfGenerator;
import it.informatica.configuratore.model.CarHandler;
import it.informatica.configuratore.view.SceneHandler;
import it.informatica.configuratore.model.UserHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Objects;

public class ConfirmController {
    @FXML
    private FontIcon homeIcon;
    @FXML
    private ProgressBar progress;
    @FXML
    private Button esporta, area;
    @FXML
    private AnchorPane top;
    @FXML
    private Label optional1Label, optional2Label, optional3Label, priceDisplay;
    @FXML
    private Rectangle colorImage, wheelImage, insideImage;
    @FXML
    private ImageView outside, inside;
    @FXML
    private ScrollPane imagePane;
    @FXML
    private HBox images;

    private final String insideN = CarHandler.getInstance().getCar().getInside();
    private int colore, cerchi, interni;




    private double x = 0, y = 0;



    @FXML
    private void initialize(){

        SceneHandler.getInstance().createScrollAnimation(imagePane, 2, 1, 2, true);

        if (!UserHandler.getInstance().isLogged()){     //nasconde pulsante area personale se nessuno è loggato
            homeIcon.setIconLiteral(Common.HOME_ICON);
            homeIcon.setIconSize(40);
            area.setGraphic(homeIcon);
            area.setOnAction(event -> {
                try {
                    SceneHandler.getInstance().createHomeScene();
                } catch (IOException e) {
                    e.printStackTrace();
                    SceneHandler.getInstance().createErrorMessage("FXML NOT FOUND!", Alert.AlertType.ERROR);
                }
            });
        }

        images = new HBox();
        Image im = null;
        try {
            im = new Image(Objects.requireNonNull(getClass().getResource(CarHandler.getInstance().getCar().getPath() + "/interni/" + CarHandler.getInstance().getCar().getInside() + "/1.png")).openStream());
        } catch (IOException e) {
            e.printStackTrace();
            SceneHandler.getInstance().createErrorMessage("FXML NOT FOUND!", Alert.AlertType.ERROR);
        }
        inside=new ImageView(im);
        inside.setPreserveRatio(true);
        inside.setFitHeight(531);
        try {
            im = new Image(Objects.requireNonNull(getClass().getResource(CarHandler.getInstance().getCar().getPath() + File.separator + CarHandler.getInstance().getCar().getColour() + File.separator + CarHandler.getInstance().getCar().getWheels() + "/1.png")).openStream());
        } catch (IOException e) {
            e.printStackTrace();
            SceneHandler.getInstance().createErrorMessage("IMAGE NOT FOUND!", Alert.AlertType.ERROR);
        }
        outside= new ImageView(im);
        outside.setPreserveRatio(true);
        outside.setFitHeight(531);

        images.getChildren().addAll(outside, inside);

        imagePane.setContent(images);

        try {
            switch (CarHandler.getInstance().getCar().getColour()) {
                case "colore-1" -> colore = 1;
                case "colore-2" -> colore = 2;
                case "colore-3" -> colore = 3;
            }
            colorImage.setFill(new ImagePattern(new Image(Objects.requireNonNull(getClass().getResource(CarHandler.getInstance().getCar().getPath() + "/colori/" + colore + Common.IMAGE_EXTENSION)).openStream())));

            switch (CarHandler.getInstance().getCar().getWheels()) {
                case "cerchio-1" -> cerchi = 1;
                case "cerchio-2" -> cerchi = 2;
                case "cerchio-3" -> cerchi = 3;
            }
            wheelImage.setFill(new ImagePattern(new Image(Objects.requireNonNull(getClass().getResource(CarHandler.getInstance().getCar().getPath() + "/cerchi/" + CarHandler.getInstance().getCar().getWheels() + Common.IMAGE_EXTENSION)).openStream())));

            switch (insideN){
                case "interno-1" -> interni = 1;
                case "interno-2" -> interni = 2;
                case "interno-3" -> interni = 3;
            }

            insideImage.setFill(new ImagePattern(new Image(Objects.requireNonNull(getClass().getResource(CarHandler.getInstance().getCar().getPath() + "/interni/" + insideN + Common.IMAGE_EXTENSION)).openStream())));
        } catch (IOException e){
            e.printStackTrace();
            SceneHandler.getInstance().createErrorMessage("IMAGENOT FOUND!", Alert.AlertType.ERROR);
        }
        if (CarHandler.getInstance().getCar().hasOptional1()){
            optional1Label.setVisible(true);
            optional1Label.setText(CarHandler.getInstance().getCar().getOptional1Text());
        }
        if (CarHandler.getInstance().getCar().hasOptional2()){
            optional2Label.setVisible(true);
            optional2Label.setText(CarHandler.getInstance().getCar().getOptional2Text());
        }
        if (CarHandler.getInstance().getCar().hasOptional3()){
            optional3Label.setVisible(true);
            optional3Label.setText(CarHandler.getInstance().getCar().getOptional3Text());
        }

        priceDisplay.setText(" " + new DecimalFormat("##.##").format(CarHandler.getInstance().getCar().getPrice()) + Common.CURRENCY);
    }



    @FXML
    private void onCloseClick() {
        SceneHandler.getInstance().close();
    }
    @FXML
    private void onAreaClick() throws IOException {
        SceneHandler.getInstance().createPersonalScene();
    }
    @FXML
    private void onEsportaClick() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null){
            new PdfGenerator(file, colore, cerchi, interni, priceDisplay.getText(), progress, esporta).start();
        }
    }




    @FXML
    private void setOnMouseDragged(MouseEvent mouseEvent) {
        Stage stage = (Stage) top.getScene().getWindow();
        stage.setX(mouseEvent.getScreenX() - x);
        stage.setY(mouseEvent.getScreenY() - y);
    }
    @FXML
    private void setOnMousePressed(MouseEvent mouseEvent) {
        x = mouseEvent.getSceneX();
        y = mouseEvent.getSceneY();
    }
}
