package it.informatica.configuratore.controller;

import it.informatica.configuratore.others.Common;
import it.informatica.configuratore.others.ConnectorHandler;
import it.informatica.configuratore.model.CarHandler;
import it.informatica.configuratore.view.SceneHandler;
import it.informatica.configuratore.model.UserHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Random;

public class ConfController {

    @FXML
    private AnchorPane back, top;
    @FXML
    private Button buttonOptional1, buttonOptional2, buttonOptional3, prossimo, cambia, next, previous;
    @FXML
    private Label optionalLabel, priceDisplay;
    @FXML
    private FontIcon previousIcon, nextIcon;
    @FXML
    private ListView<String> list;
    @FXML
    private Rectangle optional1, optional2, optional3;
    @FXML
    private ImageView imv;


    private double x = 0, y = 0, oldLayoutY1, oldLayoutY2, oldLayoutY3;
    private final String vai = "Vai a";

    private Image im;

    private int n_img_outside=1, n_img_inside=1, sceneNumber=1, selectedColor, selectedWheel, selectedInside, color2Price, color3Price, wheel2Price, wheel3Price, inside2Price, inside3Price, optional1Price, optional2Price, optional3Price;

    private final Random r = new Random();



    @FXML
    private void initialize() {

        SceneHandler.getInstance().createScaleAnimation(0.4, back, 0.5, 1, 1, false);

        if (!CarHandler.getInstance().isNewConfiguration()){
            cambia.setVisible(false);
        }

        initializeList();

        imv.requestFocus();

        generatePrice();

        loadOutsideImage();

        optionalLabel.setText("Scegli il colore");


        caricaColori("1", optional1);
        caricaColori("2", optional2);
        caricaColori("3", optional3);

        priceDisplay.setText("Prezzo ->\t\t" + String.format("%.2f", CarHandler.getInstance().getCar().getPrice()) + Common.CURRENCY);

        colorBorder();

        oldLayoutY1 = buttonOptional1.getLayoutY();
        oldLayoutY2 = buttonOptional2.getLayoutY();
        oldLayoutY3 = buttonOptional3.getLayoutY();


        Button primo = new Button("Home");

        primo.setId("Ricambia");

        primo.setLayoutY(15);
        primo.setLayoutX(70);


        primo.setOnAction(event -> {
            try {
                SceneHandler.getInstance().createPersonalScene();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        top.getChildren().add(primo);
    }


    private void initializeList() {
        list.setStyle("-fx-font-size: 15;");
        list.getItems().clear();
        list.getItems().add("Scelte :");
    }

    private void loadInsideImage() {
        try {
            imv.setPreserveRatio(true);
            im = new Image(Objects.requireNonNull(getClass().getResource(Common.IMG_PATH + CarHandler.getInstance().getCar().getMarca() + "/interni/" + CarHandler.getInstance().getCar().getInside() + File.separator + n_img_inside + Common.IMAGE_EXTENSION)).openStream());
            imv.setImage(im);
        } catch (IOException e) {
            e.printStackTrace();
            SceneHandler.getInstance().createErrorMessage("IMAGE NOT FOUND!", Alert.AlertType.ERROR);
        }
    }
    private void loadOutsideImage() {
        imv.setPreserveRatio(true);
        try {
            im = new Image(Objects.requireNonNull(getClass().getResource(Common.IMG_PATH + CarHandler.getInstance().getCar().getMarca() + File.separator + CarHandler.getInstance().getCar().getColour() + File.separator + CarHandler.getInstance().getCar().getWheels() + File.separator + n_img_outside + Common.IMAGE_EXTENSION)).openStream());
        } catch (IOException e) {
            e.printStackTrace();
            SceneHandler.getInstance().createErrorMessage("IMAGE NOT FOUND!", Alert.AlertType.ERROR);
        }
        imv.setImage(im);
    }



    private void generatePrice() {

        color2Price=r.nextInt(2000,3500);
        color3Price=r.nextInt(3000,5000);

        wheel2Price=r.nextInt(2000,3500);
        wheel3Price=r.nextInt(3000,5000);

        inside2Price=r.nextInt(2000,3500);
        inside3Price=r.nextInt(3000,5000);

        optional1Price=r.nextInt(1000,2000);
        optional2Price=r.nextInt(2000,3500);
        optional3Price=r.nextInt(3000,5000);
    }

    @FXML
    void onPreviousOutsideClick() {
        if (n_img_outside>1) {
            n_img_outside--;
        }
        else {
            n_img_outside = 3;
        }
        
        loadOutsideImage();
    }
    @FXML
    void onNextOutsideClick() {
        if (n_img_outside<3){
            n_img_outside++;
        }
        else{
            n_img_outside=1;
        }
        
        loadOutsideImage();
    }
    @FXML
    void  onNextInsideClick() {
        if (n_img_inside<2){
            n_img_inside++;
        }
        else{
            n_img_inside=1;
        }
        
        loadInsideImage();
    }
    @FXML
    void  onPreviousInsideClick() {
        if (n_img_inside>1) {
            n_img_inside--;
        }
        else {
            n_img_inside = 2;
        }
        
        loadInsideImage();
    }
    @FXML
    void  onCloseClick() {
        SceneHandler.getInstance().close();
    }
    @FXML
    void  onCambiaClick() throws IOException {
        SceneHandler.getInstance().createChooseScene();
    }

    private void alterBackground(Button button) {
        button.setStyle("-fx-background-color: dodgerblue;");
    }
    private void restoreBackground(Button button) {
        button.setStyle("-fx-background-color: transparent;");
    }
    private void alterBackgroundBorder(Button button) {
        FontIcon check = new FontIcon(Common.CHECK_ICON);
        check.setIconSize(15);
        button.setStyle("-fx-background-color: dodgerblue; -fx-border-color: black;");
        button.setGraphic(check);
    }
    private void restoreBackgroundBorder(Button button) {
        button.setStyle("-fx-background-color: transparent; -fx-border-color: black;");
        button.setGraphic(null);
    }
    private void restoreBackground(){
        restoreBackground(buttonOptional1);
        restoreBackground(buttonOptional2);
        restoreBackground(buttonOptional3);
    }           //ripristino bordi
    private void restoreLayout(){
        buttonOptional1.setLayoutY(oldLayoutY1);
        buttonOptional2.setLayoutY(oldLayoutY2);
        buttonOptional3.setLayoutY(oldLayoutY3);

        buttonOptional1.setPadding(new Insets(5));
        buttonOptional2.setPadding(new Insets(5));
        buttonOptional3.setPadding(new Insets(5));
    }


    @FXML
    void  onOptional1Unclick() {
        CarHandler.getInstance().getCar().decreasePrice(optional1Price);
        CarHandler.getInstance().getCar().setOptional1(false);

        buttonOptional1.setOnAction(event -> onOptional1Click());

        restoreBackgroundBorder(buttonOptional1);

        priceDisplay.setText("Prezzo ->\t\t" + CarHandler.getInstance().getCar().getPrice() + Common.CURRENCY);
    }          //rimozione optional
    @FXML
    void  onOptional2Unclick() {
        CarHandler.getInstance().getCar().decreasePrice(optional2Price);
        CarHandler.getInstance().getCar().setOptional2(false);

        buttonOptional2.setOnAction(event -> onOptional2Click());

        restoreBackgroundBorder(buttonOptional2);

        priceDisplay.setText("Prezzo ->\t\t" + CarHandler.getInstance().getCar().getPrice() + Common.CURRENCY);
    }           //rimozione optional
    @FXML
    void  onOptional3Unclick() {
        CarHandler.getInstance().getCar().decreasePrice(optional3Price);
        CarHandler.getInstance().getCar().setOptional3(false);

        buttonOptional3.setOnAction(event -> onOptional3Click());
        buttonOptional3.setGraphic(null);

        restoreBackgroundBorder(buttonOptional3);

        priceDisplay.setText("Prezzo ->\t\t" + CarHandler.getInstance().getCar().getPrice() + Common.CURRENCY);
    }           //rimozione optional
    @FXML
    void  onColore1Click() {
        if (selectedColor != 1) {
            switch (selectedColor) {
                case 2 -> {
                    restoreBackground(buttonOptional2);
                    CarHandler.getInstance().getCar().decreasePrice(color2Price);
                }
                case 3 -> {
                    restoreBackground(buttonOptional3);
                    CarHandler.getInstance().getCar().decreasePrice(color3Price);
                }
            }
            SceneHandler.getInstance().createScaleAnimation(0.3, optional1, 1, 1.3, 2, true);

            alterBackground(buttonOptional1);
            CarHandler.getInstance().getCar().setColour("colore-1");
            selectedColor=1;

            loadOutsideImage();
            priceDisplay.setText("Prezzo ->\t\t" + CarHandler.getInstance().getCar().getPrice() + Common.CURRENCY);
        }
    }           //click su colore
    @FXML
    void  onColore2Click() {
        if (selectedColor != 2) {
            switch (selectedColor) {
                case 3 -> {
                    restoreBackground(buttonOptional3);
                    CarHandler.getInstance().getCar().decreasePrice(color3Price);
                }
                case 1 -> restoreBackground(buttonOptional1);

            }
            SceneHandler.getInstance().createScaleAnimation(0.3, optional2, 1, 1.3, 2, true);

            alterBackground(buttonOptional2);
            CarHandler.getInstance().getCar().setColour("colore-2");
            selectedColor=2;
            CarHandler.getInstance().getCar().increasePrice(color2Price);

            loadOutsideImage();

            priceDisplay.setText("Prezzo ->\t\t" + CarHandler.getInstance().getCar().getPrice() + Common.CURRENCY);
        }
    }           //click su colore
    @FXML
    void  onColore3Click() {
        if(selectedColor != 3) {
            switch (selectedColor) {
                case 2 -> {
                    CarHandler.getInstance().getCar().decreasePrice(color2Price);
                    restoreBackground(buttonOptional2);
                }

                case 1 -> restoreBackground(buttonOptional1);
            }
            SceneHandler.getInstance().createScaleAnimation(0.3, optional3, 1, 1.3, 2, true);
            alterBackground(buttonOptional3);

            CarHandler.getInstance().getCar().setColour("colore-3");
            CarHandler.getInstance().getCar().increasePrice(color3Price);

            selectedColor=3;


            loadOutsideImage();

            priceDisplay.setText("Prezzo ->\t\t" + CarHandler.getInstance().getCar().getPrice() + Common.CURRENCY);
        }
    }           //click su colore
    @FXML
    void  onWheel1Click(){
        if(selectedWheel != 1) {
            switch (selectedWheel) {
                case 2 -> {
                    restoreBackground(buttonOptional2);
                    CarHandler.getInstance().getCar().decreasePrice(wheel2Price);
                }
                case 3 -> {
                    restoreBackground(buttonOptional3);
                    CarHandler.getInstance().getCar().decreasePrice(wheel3Price);
                }
            }
            SceneHandler.getInstance().createRotateAnimation(1.2, optional1, 360, 1);

            alterBackground(buttonOptional1);
            selectedWheel=1;
            CarHandler.getInstance().getCar().setWheels("cerchio-1");

            loadOutsideImage();
            priceDisplay.setText("Prezzo ->\t\t" + CarHandler.getInstance().getCar().getPrice() + Common.CURRENCY);
        }
    }           //click su cerchio
    @FXML
    void  onWheel2Click(){
        if (selectedWheel != 2){
            switch (selectedWheel) {
                case 1 -> restoreBackground(buttonOptional1);
                case 3 -> {
                    CarHandler.getInstance().getCar().decreasePrice(wheel3Price);
                    restoreBackground(buttonOptional3);
                }
            }
            SceneHandler.getInstance().createRotateAnimation(1.2, optional2, 360, 1);

            alterBackground(buttonOptional2);
            selectedWheel=2;
            CarHandler.getInstance().getCar().setWheels("cerchio-2");
            CarHandler.getInstance().getCar().increasePrice(wheel2Price);

            loadOutsideImage();

            priceDisplay.setText("Prezzo ->\t\t" + CarHandler.getInstance().getCar().getPrice() + Common.CURRENCY);
        }
    }           //click su cerchio
    @FXML
    void  onWheel3Click(){
        if (selectedWheel != 3) {
            switch (selectedWheel) {
                case 2 -> {
                    CarHandler.getInstance().getCar().decreasePrice(wheel2Price);
                    restoreBackground(buttonOptional2);
                }
                case 1 -> restoreBackground(buttonOptional1);
            }
            SceneHandler.getInstance().createRotateAnimation(1.2, optional3, 360, 1);

            alterBackground(buttonOptional3);
            selectedWheel=3;
            CarHandler.getInstance().getCar().setWheels("cerchio-3");
            CarHandler.getInstance().getCar().increasePrice(wheel3Price);

            loadOutsideImage();

            priceDisplay.setText("Prezzo ->\t\t" + CarHandler.getInstance().getCar().getPrice() + Common.CURRENCY);
        }
    }           //click su cerchio
    @FXML
    void  onInside1Click(){
        if (selectedInside != 1) {
            switch (selectedInside) {
                case 2 -> {
                    restoreBackground(buttonOptional2);
                    CarHandler.getInstance().getCar().decreasePrice(inside2Price);
                }
                case 3 -> {
                    restoreBackground(buttonOptional3);
                    CarHandler.getInstance().getCar().decreasePrice(inside3Price);
                }
            }
            SceneHandler.getInstance().createScaleAnimation(0.3, optional1, 1, 1.3, 2, true);
            alterBackground(buttonOptional1);
            selectedInside=1;
            CarHandler.getInstance().getCar().setInside("interno-1");

            loadInsideImage();

            priceDisplay.setText("Prezzo ->\t\t" + CarHandler.getInstance().getCar().getPrice() + Common.CURRENCY);
        }
    }           //click su interno
    @FXML
    void  onInside2Click(){
        if (selectedInside != 2) {
            switch (selectedInside) {
                case 3 -> {
                    restoreBackground(buttonOptional3);
                    CarHandler.getInstance().getCar().decreasePrice(inside3Price);
                }
                case 1 -> restoreBackground(buttonOptional1);
            }
            SceneHandler.getInstance().createScaleAnimation(0.3, optional2, 1, 1.3, 2, true);

            alterBackground(buttonOptional2);
            selectedInside=2;
            CarHandler.getInstance().getCar().setInside("interno-2");
            CarHandler.getInstance().getCar().increasePrice(inside2Price);

            loadInsideImage();

            priceDisplay.setText("Prezzo ->\t\t" + CarHandler.getInstance().getCar().getPrice() + Common.CURRENCY);
        }
    }           //click su interno
    @FXML
    void  onInside3Click(){
        if (selectedInside != 3) {
            switch (selectedInside) {
                case 1:
                    restoreBackground(buttonOptional1);
                case 2:
                    CarHandler.getInstance().getCar().decreasePrice(inside2Price);
                    restoreBackground(buttonOptional2);
                    break;
            }
            SceneHandler.getInstance().createScaleAnimation(0.3, optional3, 1, 1.3, 2, true);

            alterBackground(buttonOptional3);
            selectedInside=3;
            CarHandler.getInstance().getCar().setInside("interno-3");
            CarHandler.getInstance().getCar().increasePrice(inside3Price);

            loadInsideImage();

            priceDisplay.setText("Prezzo ->\t\t" + CarHandler.getInstance().getCar().getPrice() + Common.CURRENCY);
        }
    }           //click su interno
    @FXML
    void  onOptional1Click() {
        CarHandler.getInstance().getCar().increasePrice(optional1Price);
        CarHandler.getInstance().getCar().setOptional1(true);

        buttonOptional1.setOnAction(event -> onOptional1Unclick());

        alterBackgroundBorder(buttonOptional1);

        priceDisplay.setText("Prezzo ->\t\t" + CarHandler.getInstance().getCar().getPrice() + Common.CURRENCY);
    }       //click su optional
    @FXML
    void  onOptional2Click() {
        CarHandler.getInstance().getCar().increasePrice(optional2Price);
        CarHandler.getInstance().getCar().setOptional2(true);

        buttonOptional2.setOnAction(event -> onOptional2Unclick());

        alterBackgroundBorder(buttonOptional2);

        priceDisplay.setText("Prezzo ->\t\t" + CarHandler.getInstance().getCar().getPrice() + Common.CURRENCY);
    }       //click su optional
    @FXML
    void  onOptional3Click() {
        CarHandler.getInstance().getCar().increasePrice(optional3Price);
        CarHandler.getInstance().getCar().setOptional3(true);

        buttonOptional3.setOnAction(event -> onOptional3Unclick());

        alterBackgroundBorder(buttonOptional3);
        priceDisplay.setText("Prezzo ->\t\t" + CarHandler.getInstance().getCar().getPrice() + Common.CURRENCY);
    }       //click su optional
    @FXML
    void  onProssimoClick() throws IOException {
        switch (sceneNumber) {
            case 1 -> {
                restoreBackground();
                sceneNumber++;
                list.getItems().add("\t" + CarHandler.getInstance().getCar().getColour());
                prossimo.setText(vai + " gli Interni");
                optionalLabel.setText("Scegli i cerchi");

                caricaCerchi("cerchio-1", optional1);
                caricaCerchi("cerchio-2", optional2);
                caricaCerchi("cerchio-3", optional3);

                buttonOptional1.setOnAction(event -> onWheel1Click());
                buttonOptional2.setOnAction(event -> onWheel2Click());
                buttonOptional3.setOnAction(event -> onWheel3Click());

                addPrimo();
                wheelsBorder();
            }
            case 2 -> {
                restoreBackground();
                sceneNumber++;
                list.getItems().add("\t" + CarHandler.getInstance().getCar().getWheels());
                prossimo.setText(vai + " gli Optional");
                optionalLabel.setText("Scegli la stoffa");

                caricaInterni("interno-1", optional1);
                caricaInterni("interno-2", optional2);
                caricaInterni("interno-3", optional3);
                
                loadInsideImage();

                previous.setOnAction(event -> onPreviousInsideClick());
                next.setOnAction(event -> onNextInsideClick());

                buttonOptional1.setOnAction(event -> onInside1Click());
                buttonOptional2.setOnAction(event -> onInside2Click());
                buttonOptional3.setOnAction(event -> onInside3Click());

                previousIcon.setIconColor(Color.WHITE);
                nextIcon.setIconColor(Color.WHITE);
                addSecondo();
                insideBorder();
            }
            case 3 -> {
                restoreBackground();
                sceneNumber++;
                list.getItems().add("\t" + CarHandler.getInstance().getCar().getInside());

                prossimo.setText("Conferma");
                prossimo.setId("Conferma");

                optionalLabel.setText("Scegli gli optional");

                addTerzo();

                caricaOptional();
            }
            case 4 -> {
                if (SceneHandler.getInstance().createInfoMessage("Conferma", "Confermando non potrai più tornare indietro", Alert.AlertType.CONFIRMATION, true)) {
                    if (UserHandler.getInstance().isLogged()) {
                        if (CarHandler.getInstance().isNewConfiguration()) {
                            TextInputDialog td = new TextInputDialog();
                            td.initStyle(StageStyle.TRANSPARENT);
                            td.setHeaderText(null);
                            td.setContentText("Inserisci il nome della configurazione");
                            td.showAndWait();
                            try {
                                PreparedStatement stmt = ConnectorHandler.getInstance().getConnector().getConnection().prepareStatement(Common.INSERT_CONFIGURATION);
                                stmt.setString(1, td.getEditor().getText());
                                stmt.setString(2, CarHandler.getInstance().getCar().getMarca());
                                stmt.setString(3, CarHandler.getInstance().getCar().getModello());
                                stmt.setString(4, CarHandler.getInstance().getCar().getColour());
                                stmt.setString(5, CarHandler.getInstance().getCar().getWheels());
                                stmt.setString(6, CarHandler.getInstance().getCar().getInside());
                                stmt.setBoolean(7, CarHandler.getInstance().getCar().hasOptional1());
                                stmt.setBoolean(8, CarHandler.getInstance().getCar().hasOptional2());
                                stmt.setBoolean(9, CarHandler.getInstance().getCar().hasOptional3());
                                stmt.setString(10, String.valueOf(CarHandler.getInstance().getCar().getPrice()));
                                stmt.setString(11, String.valueOf(UserHandler.getInstance().getU().getId()));
                                stmt.execute();
                                stmt.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                                SceneHandler.getInstance().createErrorMessage("DATABASE ERROR!", Alert.AlertType.ERROR);
                            }
                        } else {
                            try {
                                PreparedStatement stmt = ConnectorHandler.getInstance().getConnector().getConnection().prepareStatement(Common.UPDATE_CONFIGURATION);
                                stmt.setString(1, CarHandler.getInstance().getCar().getMarca());
                                stmt.setString(2, CarHandler.getInstance().getCar().getModello());
                                stmt.setString(3, CarHandler.getInstance().getCar().getColour());
                                stmt.setString(4, CarHandler.getInstance().getCar().getWheels());
                                stmt.setString(5, CarHandler.getInstance().getCar().getInside());
                                stmt.setBoolean(6, CarHandler.getInstance().getCar().hasOptional1());
                                stmt.setBoolean(7, CarHandler.getInstance().getCar().hasOptional2());
                                stmt.setBoolean(8, CarHandler.getInstance().getCar().hasOptional3());
                                stmt.setString(9, String.valueOf(CarHandler.getInstance().getCar().getPrice()));
                                stmt.setString(10, String.valueOf(CarHandler.getInstance().getCar().getConfId()));
                                stmt.execute();
                                stmt.close();               //crea oggetto utente
                            } catch (SQLException e) {
                                e.printStackTrace();
                                SceneHandler.getInstance().createErrorMessage("DATABASE ERROR!", Alert.AlertType.ERROR);
                            }
                        }
                    }
                }
            }
        }
    }       //click sul pulsante che fa continuare



    private void addPrimo() {
        Button primo = new Button("Colore");

        primo.setId("Ricambia");

        primo.setLayoutY(15);
        primo.setLayoutX(15);

        primo.setOnAction(event -> tornaColori());

        top.getChildren().add(primo);
    }           //aggiunge il bottone per tornare a modificare il colore
    private void addSecondo() {
        Button secondo = new Button("Cerchi");

        secondo.setId("Ricambia2");

        secondo.setLayoutY(15);
        secondo.setLayoutX(85);

        secondo.setOnAction(event -> tornaCerchi());

        top.getChildren().add(secondo);
    }           //aggiunge il bottone per tornare a modificare i cerchi
    private void addTerzo() {
        Button terzo = new Button("Interni");

        terzo.setId("Ricambia3");

        terzo.setLayoutY(15);
        terzo.setLayoutX(155);

        terzo.setOnAction(event -> tornaInterni());

        top.getChildren().add(terzo);
    }               //aggiunge il bottone per tornare a modificare gli interni

    private void caricaColori(String colore, Rectangle optionalImage) {
        optionalImage.setVisible(true);
        ImagePattern pattern = null;
        try {
            pattern = new ImagePattern(new Image(Objects.requireNonNull(getClass().getResource(CarHandler.getInstance().getCar().getPath() + "/colori/" + colore + Common.IMAGE_EXTENSION)).openStream()));
        } catch (IOException e) {
            e.printStackTrace();
            SceneHandler.getInstance().createErrorMessage("IMAGE NOT FOUND!", Alert.AlertType.ERROR);
        }
        optionalImage.setFill(pattern);
    }
    private void caricaCerchi(String cerchio, Rectangle optionalImage) {
        optionalImage.setVisible(true);
        ImagePattern pattern = null;
        try {
            pattern = new ImagePattern(new Image(Objects.requireNonNull(getClass().getResource(CarHandler.getInstance().getCar().getPath() + "/cerchi/" + cerchio + Common.IMAGE_EXTENSION)).openStream()));
        } catch (IOException e) {
            e.printStackTrace();
            SceneHandler.getInstance().createErrorMessage("IMAGE NOT FOUND!", Alert.AlertType.ERROR);
        }
        optionalImage.setFill(pattern);
    }
    private void caricaInterni(String interno, Rectangle optionalImage) {
        optionalImage.setVisible(true);
        ImagePattern pattern = null;
        try {
            pattern = new ImagePattern(new Image(Objects.requireNonNull(getClass().getResource(CarHandler.getInstance().getCar().getPath() + "/interni/" + interno + Common.IMAGE_EXTENSION)).openStream()));
        } catch (IOException e) {
            e.printStackTrace();
            SceneHandler.getInstance().createErrorMessage("IMAGE NOT FOUND!", Alert.AlertType.ERROR);
        }
        optionalImage.setFill(pattern);
    }
    private void caricaOptional() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader( CarHandler.getInstance().getCar().getOptionalPath() + "\\optionals.txt"));

        buttonOptional1.setGraphic(null);
        buttonOptional2.setGraphic(null);
        buttonOptional3.setGraphic(null);

        buttonOptional1.setText(br.readLine());
        buttonOptional2.setText(br.readLine());
        buttonOptional3.setText(br.readLine());

        buttonOptional1.setPadding(new Insets(30, 10, 30, 10));
        buttonOptional2.setPadding(new Insets(30, 10, 30, 10));
        buttonOptional3.setPadding(new Insets(30, 10, 30, 10));

        buttonOptional2.setLayoutY(buttonOptional2.getLayoutY()+15);
        buttonOptional3.setLayoutY(buttonOptional3.getLayoutY()+30);

        buttonOptional1.setStyle("-fx-border-color: black;");
        buttonOptional2.setStyle("-fx-border-color: black;");
        buttonOptional3.setStyle("-fx-border-color: black;");

        buttonOptional1.setWrapText(true);
        buttonOptional2.setWrapText(true);
        buttonOptional3.setWrapText(true);

        CarHandler.getInstance().getCar().setOptional1Text(buttonOptional1.getText());
        CarHandler.getInstance().getCar().setOptional2Text(buttonOptional2.getText());
        CarHandler.getInstance().getCar().setOptional3Text(buttonOptional3.getText());


        if (CarHandler.getInstance().getCar().hasOptional1()){
            alterBackgroundBorder(buttonOptional1);
            buttonOptional1.setOnAction(event -> onOptional1Unclick());
        }
        else{
            buttonOptional1.setOnAction(event -> onOptional1Click());
        }
        if (CarHandler.getInstance().getCar().hasOptional2()){
            alterBackgroundBorder(buttonOptional2);
            buttonOptional2.setOnAction(event -> onOptional2Unclick());
        }
        else {
            buttonOptional2.setOnAction(event -> onOptional2Click());
        }
        if (CarHandler.getInstance().getCar().hasOptional3()){
            alterBackgroundBorder(buttonOptional3);
            buttonOptional3.setOnAction(event -> onOptional3Unclick());
        }
        else {
            buttonOptional3.setOnAction(event -> onOptional3Click());
        }
    }


    private void tornaColori() {

        loadOutsideImage();

        restoreBackground();
        restoreLayout();

        prossimo.setId("prossimo");

        top.getChildren().clear();

        initializeList();

        sceneNumber=1;

        prossimo.setText(vai + " i Cerchi");

        optionalLabel.setText("Scegli il colore");


        caricaColori("1", optional1);
        caricaColori("2", optional2);
        caricaColori("3", optional3);

        setButtons();

        buttonOptional1.setOnAction(event -> onColore1Click());
        buttonOptional2.setOnAction(event -> onColore2Click());
        buttonOptional3.setOnAction(event -> onColore3Click());

        previous.setOnAction(event -> onPreviousOutsideClick());
        next.setOnAction(event -> onNextOutsideClick());
        previousIcon.setIconColor(Color.BLACK);
        nextIcon.setIconColor(Color.BLACK);

        colorBorder();
    }
    private void tornaCerchi() {
        restoreBackground();
        restoreLayout();

        prossimo.setId("prossimo");

        top.getChildren().clear();
        initializeList();
        list.getItems().add("\t" + CarHandler.getInstance().getCar().getColour());
        addPrimo();

        sceneNumber=2;

        prossimo.setText(vai + " gli Interni");

        optionalLabel.setText("Scegli il cerchio");


        caricaCerchi("cerchio-1", optional1);
        caricaCerchi("cerchio-2", optional2);
        caricaCerchi("cerchio-3", optional3);

        setButtons();

        buttonOptional1.setOnAction(event -> onWheel1Click());
        buttonOptional2.setOnAction(event -> onWheel2Click());
        buttonOptional3.setOnAction(event -> onWheel3Click());

        previous.setOnAction(event -> onPreviousOutsideClick());
        next.setOnAction(event -> onNextOutsideClick());
        previousIcon.setIconColor(Color.BLACK);
        nextIcon.setIconColor(Color.BLACK);


        loadOutsideImage();

        wheelsBorder();
    }
    private void tornaInterni() {
        restoreBackground();
        restoreLayout();

        prossimo.setId("prossimo");

        top.getChildren().clear();

        initializeList();
        list.getItems().add("\t" + CarHandler.getInstance().getCar().getColour());
        list.getItems().add("\t" + CarHandler.getInstance().getCar().getWheels());

        addPrimo();
        addSecondo();

        sceneNumber=3;

        prossimo.setText(vai + " gli Optional");

        optionalLabel.setText("Scegli la stoffa");


        caricaInterni("interno-1", optional1);
        caricaInterni("interno-2", optional2);
        caricaInterni("interno-3", optional3);

        previous.setOnAction(event -> onPreviousInsideClick());
        next.setOnAction(event -> onNextInsideClick());

        setButtons();

        buttonOptional1.setOnAction(event -> onInside1Click());
        buttonOptional2.setOnAction(event -> onInside2Click());
        buttonOptional3.setOnAction(event -> onInside3Click());

        loadInsideImage();

        insideBorder();
    }



    //colorare il bordo dei bottoni relativi agli optional scelti
    private void colorBorder() {
        switch (CarHandler.getInstance().getCar().getColour()) {
            case "colore-1" -> {
                alterBackground(buttonOptional1);
                selectedColor=1;
            }
            case "colore-2" -> {
                alterBackground(buttonOptional2);
                selectedColor=2;
            }
            case "colore-3" -> {
                alterBackground(buttonOptional3);
                selectedColor=3;
            }
        }
    }
    private void wheelsBorder() {
        switch (CarHandler.getInstance().getCar().getWheels()) {
            case "cerchio-1" -> {
                alterBackground(buttonOptional1);
                selectedWheel=1;
            }
            case "cerchio-2" -> {
                alterBackground(buttonOptional2);
                selectedWheel=2;
            }
            case "cerchio-3" -> {
                alterBackground(buttonOptional3);
                selectedWheel=3;
            }
        }
    }
    private void insideBorder() {
        switch (CarHandler.getInstance().getCar().getInside()) {
            case "interno-1" -> {
                alterBackground(buttonOptional1);
                selectedInside=1;
            }
            case "interno-2" -> {
                alterBackground(buttonOptional2);
                selectedInside=2;
            }
            case "interno-3" -> {
                alterBackground(buttonOptional3);
                selectedInside=3;
            }
        }
    }


    private void setButtons(){
        buttonOptional1.setText(null);
        buttonOptional2.setText(null);
        buttonOptional3.setText(null);

        buttonOptional1.setGraphic(optional1);
        buttonOptional2.setGraphic(optional2);
        buttonOptional3.setGraphic(optional3);
    }


    //spostamento finestra
    @FXML
    void  setOnMouseDragged(MouseEvent mouseEvent) {
        Stage stage = (Stage) top.getScene().getWindow();
        stage.setX(mouseEvent.getScreenX() - x);
        stage.setY(mouseEvent.getScreenY() - y);
    }
    @FXML
    void  setOnMousePressed(MouseEvent mouseEvent) {
        x = mouseEvent.getSceneX();
        y = mouseEvent.getSceneY();
    }
}
