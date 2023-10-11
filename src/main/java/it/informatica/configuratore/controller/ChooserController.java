package it.informatica.configuratore.controller;

import it.informatica.configuratore.others.Common;
import it.informatica.configuratore.others.ConnectorHandler;
import it.informatica.configuratore.model.CarHandler;
import it.informatica.configuratore.view.SceneHandler;
import it.informatica.configuratore.model.UserHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class ChooserController {

    @FXML
    private AnchorPane top;
    @FXML
    private MenuBar mb;
    @FXML
    private MenuItem customize, about;
    @FXML
    private FontIcon themeIcon;
    @FXML
    private Label car1, car11, car2, car21, car3, car31, price3, price2, price1;
    @FXML
    private BorderPane back;
    @FXML
    private Button first, second, third, forth, fifth, sixth, themeButton;

    private double x = 0, y = 0;

    @FXML
    private void initialize() throws SQLException {

        if (UserHandler.getInstance().isLogged()){
            mb.getMenus().get(0).getItems().remove(1);
            mb.getMenus().get(0).getItems().get(0).setOnAction(event -> {
                try {
                    SceneHandler.getInstance().createPersonalScene();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        SceneHandler.getInstance().createScaleAnimation(0.7, back, 0.5, 1, 1, false);
        SceneHandler.getInstance().createScaleAnimation(1.2, first, 0.5, 1, 1, false);
        SceneHandler.getInstance().createScaleAnimation(1.2, second, 0.5, 1, 1, false);
        SceneHandler.getInstance().createScaleAnimation(1.2, third, 0.5, 1, 1, false);
        SceneHandler.getInstance().createScaleAnimation(1.2, forth, 0.5, 1, 1, false);
        SceneHandler.getInstance().createScaleAnimation(1.2, fifth, 0.5, 1, 1, false);
        SceneHandler.getInstance().createScaleAnimation(1.2, sixth, 0.5, 1, 1, false);



        PreparedStatement stmt = ConnectorHandler.getInstance().getConnector().getConnection().prepareStatement(Common.CARS);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt(1);
            String marca = rs.getString(2);
            String modello = rs.getString(3);
            setName(id, marca, modello);
        }

        stmt.close();


        Random r = new Random();
        price1.setText(String.valueOf(r.nextFloat(50000.50f, 70000.50f)));
        price2.setText(String.valueOf(r.nextFloat(35000.50f, 85000.50f)));
        price3.setText(String.valueOf(r.nextFloat(120000.50f, 160000.50f)));

        initThemeButton();

        FontIcon fi = new FontIcon(Common.CUSTOMIZE_ICON);
        fi.setIconSize(15);
        customize.setGraphic(fi);

        fi = new FontIcon(Common.ABOUT_ICON);
        fi.setIconSize(15);
        about.setGraphic(fi);
    }

    private void setName(int id, String marca, String modello) {
        switch (id) {
            case 1 -> {
                car1.setText(marca);
                car11.setText(modello);
            }
            case 2 -> {
                car2.setText(marca);
                car21.setText(modello);
            }
            case 3 -> {
                car3.setText(marca);
                car31.setText(modello);
            }
        }
    }

    @FXML
    private void onCloseClick() {
        SceneHandler.getInstance().close();
    }

    @FXML
    private void onfirstclick() throws IOException {
        CarHandler.getInstance().loadCar(car1.getText(), car11.getText());
        CarHandler.getInstance().getCar().setPrice(Float.parseFloat(price1.getText()));
        SceneHandler.getInstance().createConfScene();
    }
    @FXML
    private void onsecondclick() throws IOException {
        CarHandler.getInstance().loadCar(car2.getText(), car21.getText());
        CarHandler.getInstance().getCar().setPrice(Float.parseFloat(price2.getText()));
        SceneHandler.getInstance().createConfScene();
    }
    @FXML
    private void onthirdclick() throws IOException {
        CarHandler.getInstance().loadCar(car3.getText(), car31.getText());
        CarHandler.getInstance().getCar().setPrice(Float.parseFloat(price3.getText()));
        SceneHandler.getInstance().createConfScene();
    }
    @FXML
    private void onforthclick() throws IOException {
        onsixthclick();
    }
    @FXML
    private void onfifthclick() throws IOException {
        onsixthclick();
    }
    @FXML
    private void onsixthclick() throws IOException {
        SceneHandler.getInstance().createComingScene();
    }

    @FXML
    private void changeTheme() {
        SceneHandler.getInstance().changeTheme();
        initThemeButton();
        SceneHandler.getInstance().createScaleAnimation(0.4, this.back, 1, 0.8, 2, true);
    }

    private void initThemeButton() {
        if (SceneHandler.getInstance().isLightTheme()) {
            themeIcon = new FontIcon(Common.LIGHT_ICON);
            themeButton.setText(Common.LIGHT_TEXT);
        } else {
            themeIcon = new FontIcon(Common.DARK_ICON);
            themeButton.setText(Common.DARK_TEXT);
        }
        themeIcon.setIconColor(Color.YELLOW);
        themeIcon.setIconSize(19);
        themeButton.setGraphic(themeIcon);
    }

    @FXML
    private void onPersonalizzaClick() throws IOException {
        SceneHandler.getInstance().createCustomizeScene();
    }

    @FXML
    private void onAboutClick() throws IOException {
        SceneHandler.getInstance().createInfoMessage(Common.NAME + "\t\tAbout", Common.ABOUT_PROCONF, Alert.AlertType.INFORMATION, false);
    }

    @FXML
    private void onHomeClick() throws IOException {
        SceneHandler.getInstance().createHomeScene();
    }
    @FXML
    private void onLoginCLick() throws IOException {
        SceneHandler.getInstance().createLoginScene();
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
