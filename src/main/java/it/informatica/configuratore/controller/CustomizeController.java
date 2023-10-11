package it.informatica.configuratore.controller;

import it.informatica.configuratore.view.SceneHandler;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CustomizeController {

    @FXML
    private AnchorPane back;

    @FXML
    private MenuButton fontChooser, colorChooser;
    @FXML
    private MenuItem Def, Aleg, Rob, defaultColor, color2Menu, color3Menu;

    @FXML
    private Rectangle color1, color2, color3;

    private String st, color, selectedText, selectedColor;


    @FXML
    private void initialize(){
        initializeChoosers();
        initializeSelected();

        defaultColor.setText("Default");

        if (SceneHandler.getInstance().isLightTheme()){
            color1.setFill(Color.hsb(210,0.4,0.8));
            color2.setFill(Color.ANTIQUEWHITE);
            color2Menu.setText("antiquewhite");
            color3.setFill(new Color(0.8274509803921568, 0.615686274509804, 0.22745098039215686, 1));
            color3Menu.setText("orange");
        }
        else {
            color1.setFill(new Color(0.2, 0.26666666666666666, 0.5215686274509804, 1));
            color2.setFill(new Color(0.09803921568627451, 0.11372549019607843, 0.12941176470588237, 1));
            color2Menu.setText("darkslategrey");
            color3.setFill(Color.DARKRED);
            color3Menu.setText("darkred");
        }
    }

    private void initializeChoosers() {
        st = colorChooser.getStyle();

        fontChooser.setText(SceneHandler.getInstance().getFont());
        colorChooser.setText(SceneHandler.getInstance().getColor());

        if (!"Default".equals(colorChooser.getText())) {
            colorChooser.setStyle(st + " -fx-background-color: " + SceneHandler.getInstance().getColor() + ";");
        }
    }

    private void initializeSelected() {
        selectedText = SceneHandler.getInstance().getFont();
        selectedColor = SceneHandler.getInstance().getSelectedColor();
    }

    @FXML
    private void onDefaultClick() {
        fontChooser.setText(Def.getText());
        selectedText=Def.getText();
    }
    @FXML
    private void onAlegreyaClick() {
        fontChooser.setText(Aleg.getText());
        selectedText=Aleg.getText();
    }
    @FXML
    private void onRobotoClick() {
        fontChooser.setText(Rob.getText());
        selectedText=Rob.getText();
    }



    @FXML
    private void onDefaultColorClick() {
        selectedColor=defaultColor.getText();

        color = "Default";
        colorChooser.setText(selectedColor);
        colorChooser.setStyle(st);
    }

    @FXML
    private void onSecondColorClick() {
        selectedColor="color2";

        color = color2Menu.getText();
        colorChooser.setText(color);
        colorChooser.setStyle(st + " -fx-background-color: " + color);
    }

    @FXML
    private void onThirdColorClick() {
        selectedColor="color3";

        color = color3Menu.getText();
        colorChooser.setText(color);
        colorChooser.setStyle(st + " -fx-background-color: " + color);
    }

    @FXML
    private void onSaveClick() {
        if (color == null){
            color = selectedColor;
        }
        SceneHandler.getInstance().changeMode(selectedText, selectedColor, color);
        onExitClick();
    }

    public void onExitClick() {
        SceneHandler.getInstance().close2();
    }
}
