package it.informatica.configuratore.controller;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import it.informatica.configuratore.others.Common;
import it.informatica.configuratore.model.CarHandler;
import it.informatica.configuratore.view.SceneHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeController {

    @FXML
    private AnchorPane top;
    @FXML
    private BorderPane back;

    @FXML
    private MenuBar mb;
    @FXML
    private Menu editMenu;
    @FXML
    private MenuItem customize, about;
    @FXML
    private Button themeButton;
    @FXML
    private FontIcon themeIcon;


    private double x = 0, y = 0;



    @FXML
    private void initialize(){
        SceneHandler.getInstance().createScaleAnimation(0.6, back, 0.5, 1, 1, false);

        initThemeButton();

        FontIcon fi = new FontIcon(Common.CUSTOMIZE_ICON);
        fi.setIconSize(15);
        customize.setGraphic(fi);

        fi = new FontIcon(Common.ABOUT_ICON);
        fi.setIconSize(15);
        about.setGraphic(fi);
    }

    @FXML
    private void onAboutClick() throws IOException {
        SceneHandler.getInstance().createInfoMessage(Common.NAME + "\t\tAbout", Common.ABOUT_PROCONF, Alert.AlertType.INFORMATION, false);
    }

    @FXML
    private void onPersonalizzaClick() throws IOException {
        SceneHandler.getInstance().createCustomizeScene();
    }
    @FXML
    private void onNuovaClick() throws IOException {
        SceneHandler.getInstance().createChooseScene();
    }
    @FXML
    private void onCaricaClick() throws IOException {
        FileChooser f = new FileChooser();
        f.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf"));
        File file = f.showOpenDialog(null);

        if (file != null){
            PdfReader pdfReader = new PdfReader(file.toURI().toString());

            Map<String, String> info = pdfReader.getInfo();

            String[] splitted0 = info.get("Subject").split(" ");
            CarHandler.getInstance().loadCar(splitted0[0], splitted0[1]);

            String textFromPdf = PdfTextExtractor.getTextFromPage(pdfReader, 1);

            String[] splitted = textFromPdf.split("\n");

            for (String s : splitted) {

                Pattern pattern1 = Pattern.compile("[1-3]_[1-3]_[1-3]");
                Pattern pattern2 = Pattern.compile("[1-9]+[,]*[0-9]*[ ]*€*");
                Matcher m1 = pattern1.matcher(s);
                Matcher m2 = pattern2.matcher(s);
                if (m1.matches()) {
                    String[] splitted2 = s.split("_");

                    CarHandler.getInstance().getCar().setColour("colore-"+splitted2[0]);
                    CarHandler.getInstance().getCar().setWheels("cerchio-"+splitted2[1]);
                    CarHandler.getInstance().getCar().setInside("interno-"+splitted2[2]);

                } else if (m2.find()) {                                     //se corrisponde al prezzo
                    String[] splitted3 = s.split(" ");
                    String prezzo = splitted3[1].replaceAll(",", ".");
                    CarHandler.getInstance().getCar().setPrice(Float.parseFloat(prezzo));
                }
            }

            pdfReader.close();
            CarHandler.getInstance().setNewConfiguration(false);
            SceneHandler.getInstance().createConfScene();
        }
    }
    @FXML
    private void onLoginClick() throws IOException {
        SceneHandler.getInstance().createLoginScene();
    }

    @FXML
    private void onCloseClick() {
        SceneHandler.getInstance().close();
    }
    private void initThemeButton() {
        if(SceneHandler.getInstance().isLightTheme()){
            themeIcon = new FontIcon(Common.LIGHT_ICON);
            themeButton.setText(Common.LIGHT_TEXT);
        }
        else {
            themeIcon = new FontIcon(Common.DARK_ICON);
            themeButton.setText(Common.DARK_TEXT);
        }
        themeIcon.setIconColor(Color.YELLOW);
        themeIcon.setIconSize(19);
        themeButton.setGraphic(themeIcon);
    }

    @FXML
    private void changeTheme() {
        SceneHandler.getInstance().changeTheme();
        initThemeButton();
        SceneHandler.getInstance().createScaleAnimation(0.2, back, 1, 0.7, 2, true);
    }


    @FXML
    private void setOnMouseDragged(MouseEvent mouseEvent) {
        Stage stage = (Stage) back.getScene().getWindow();
        stage.setX(mouseEvent.getScreenX() - x);
        stage.setY(mouseEvent.getScreenY() - y);
    }

    @FXML
    private void setOnMousePressed(MouseEvent mouseEvent) {
        x = mouseEvent.getSceneX();
        y = mouseEvent.getSceneY();
    }
}
