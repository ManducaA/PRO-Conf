package it.informatica.configuratore.controller;

import it.informatica.configuratore.model.Configuration;
import it.informatica.configuratore.others.Common;
import it.informatica.configuratore.others.ConnectorHandler;
import it.informatica.configuratore.model.CarHandler;
import it.informatica.configuratore.view.SceneHandler;
import it.informatica.configuratore.model.UserHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class PersController {
    @FXML
    private MenuItem customize, about;
    @FXML
    private TableView<Configuration> Configurations;
    @FXML
    private TableColumn<Configuration, Integer> id;
    @FXML
    private TableColumn<Configuration, String> name, make, model, colour, wheels, inside;
    @FXML
    private TableColumn<Configuration, Float> price;
    @FXML
    private FontIcon editIcon, deleteIcon, themeIcon;
    @FXML
    private Button edit, delete, themeButton;
    @FXML
    private TextField searchText;
    @FXML
    private AnchorPane back,top;
    @FXML
    private Label title, tipLabel;

    private double x = 0, y = 0;

    private PreparedStatement stmt;


    private int idC;
    private String nome, marca, modello, colore, cerchi, interni;
    private boolean optional1, optional2, optional3;
    private float prezzo;


    @FXML
    private void initialize() throws SQLException {


        SceneHandler.getInstance().createScaleAnimation(0.6, back, 0.5, 1, 1, false);

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        make.setCellValueFactory(new PropertyValueFactory<>("make"));
        model.setCellValueFactory(new PropertyValueFactory<>("model"));
        colour.setCellValueFactory(new PropertyValueFactory<>("colour"));
        wheels.setCellValueFactory(new PropertyValueFactory<>("wheels"));
        inside.setCellValueFactory(new PropertyValueFactory<>("inside"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        Configurations.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Configuration> observableValue, Configuration oldValue, Configuration newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (newValue != null) {
                    idC = newValue.getId();
                    edit.setVisible(true);
                    delete.setVisible(true);
                }
                else {
                    edit.setVisible(false);
                    delete.setVisible(false);
                }
            }
        });

        editIcon = new FontIcon(Common.EDIT_ICON);
        deleteIcon = new FontIcon(Common.DELETE_ICON);

        editIcon.setIconSize(20);
        deleteIcon.setIconSize(20);

        edit.setGraphic(editIcon);
        delete.setGraphic(deleteIcon);

        edit.setVisible(false);
        delete.setVisible(false);

        FontIcon fi = new FontIcon(Common.CUSTOMIZE_ICON);
        fi.setIconSize(15);
        customize.setGraphic(fi);

        fi = new FontIcon(Common.ABOUT_ICON);
        fi.setIconSize(15);
        about.setGraphic(fi);


        title.setText("Le configurazioni di " + UserHandler.getInstance().getU().getUsername());

        loadConfigurations(null);

        initThemeButton();

        searchText.setOnKeyPressed(event -> {
            if (Objects.requireNonNull(event.getCode()) == KeyCode.ENTER) {
                try {
                    onSearchClick();
                } catch (SQLException e) {
                    e.printStackTrace();
                    SceneHandler.getInstance().createErrorMessage("DATABASE ERROR!", Alert.AlertType.ERROR);
                }
            }
        });

        Thread t = new Thread(() -> {
            try {
                Thread.sleep(5000);
                tipLabel.setVisible(false);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
    }


    @FXML
    private void onCloseClick() {
        SceneHandler.getInstance().close();
    }
    @FXML
    private void onNuovaClick() throws IOException {
        CarHandler.getInstance().setNewConfiguration(true);
        SceneHandler.getInstance().createChooseScene();
    }
    @FXML
    private void onEditClick(int id, String marca, String modello, String colore, String cerchi, String interni, boolean optional1, boolean optional2, boolean optional3, float price) throws IOException {
        CarHandler.getInstance().loadCar(marca, modello);
        CarHandler.getInstance().getCar().setConfID(id);
        CarHandler.getInstance().getCar().setColour(colore);
        CarHandler.getInstance().getCar().setWheels(cerchi);
        CarHandler.getInstance().getCar().setInside(interni);
        CarHandler.getInstance().getCar().setOptional1(optional1);
        CarHandler.getInstance().getCar().setOptional2(optional2);
        CarHandler.getInstance().getCar().setOptional3(optional3);
        CarHandler.getInstance().getCar().setPrice(price);
        SceneHandler.getInstance().createConfScene();
    }
    @FXML
    private void onDeleteClick(int confId) throws SQLException {
        stmt = ConnectorHandler.getInstance().getConnector().getConnection().prepareStatement(Common.DELETE_CONFIGURATION_ID);
        stmt.setString(1, String.valueOf(confId));
        stmt.execute();
        stmt.close();

        loadConfigurations(null);
    }

    private void loadConfigurations(String text) throws SQLException {
        ObservableList<Configuration> data = FXCollections.observableArrayList();
        Configurations.setItems(data);

        if (text == null) {
            stmt = ConnectorHandler.getInstance().getConnector().getConnection().prepareStatement(Common.USER_CONFIGURATIONS);
        }
        else{
            stmt = ConnectorHandler.getInstance().getConnector().getConnection().prepareStatement(Common.USER_CONFIGURATIONS_NAME);
            stmt.setString(2, "%"+text+"%");
        }
        stmt.setString(1, String.valueOf(UserHandler.getInstance().getU().getId()));
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {

            int i1 = rs.getInt(1);
            String nome = rs.getString(2);
            String marca = rs.getString(3);
            String modello = rs.getString(4);
            String colore = rs.getString(5);
            String cerchi = rs.getString(6);
            String interni = rs.getString(7);
            boolean optional1 = rs.getBoolean(8);
            boolean optional2 = rs.getBoolean(9);
            boolean optional3 = rs.getBoolean(10);
            float price = rs.getFloat(11);

            Configuration c = new Configuration(i1, nome, marca, modello, colore, cerchi, interni, optional1, optional2, optional3, price);
            data.add(c);
        }
        stmt.close();
    }


    @FXML
    private void changeTheme() {
        SceneHandler.getInstance().changeTheme();
        initThemeButton();
        SceneHandler.getInstance().createScaleAnimation(0.2, this.back, 1, 0.8, 2, true);
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
    private void onActionClick() throws IOException {
        SceneHandler.getInstance().createCustomizeScene();
    }
    @FXML
    private void onAboutClick() throws IOException {
        SceneHandler.getInstance().createInfoMessage(Common.NAME + "\t\tAbout", Common.ABOUT_PROCONF, Alert.AlertType.INFORMATION, false);
    }
    @FXML
    private void onEdit() throws IOException, SQLException {
        CarHandler.getInstance().setNewConfiguration(false);

        stmt = ConnectorHandler.getInstance().getConnector().getConnection().prepareStatement(Common.EDIT_CONFIGURATION);
        stmt.setString(1, String.valueOf(idC));
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            nome = rs.getString(2);
            marca = rs.getString(3);
            modello = rs.getString(4);
            colore = rs.getString(5);
            cerchi = rs.getString(6);
            interni = rs.getString(7);
            optional1 = rs.getBoolean(8);
            optional2 = rs.getBoolean(9);
            optional3 = rs.getBoolean(10);
            prezzo = rs.getFloat(11);
        }
        stmt.close();

        onEditClick(idC, marca, modello, colore, cerchi, interni, optional1, optional2, optional3, prezzo);
    }
    @FXML
    private void onDelete() throws SQLException {
        onDeleteClick(idC);
    }
    @FXML
    private void onSearchClick() throws SQLException {
        if (searchText.getText().length() != 0){
            loadConfigurations(searchText.getText());
        }
    }

    @FXML
    private void gameStart() throws IOException {
        SceneHandler.getInstance().createGameScene();
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
