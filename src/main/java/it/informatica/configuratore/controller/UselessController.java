package it.informatica.configuratore.controller;

import it.informatica.configuratore.view.SceneHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class UselessController {

    @FXML
    private AnchorPane back;

    @FXML
    private void initialize() {
        SceneHandler.getInstance().createScaleAnimation(0.9, back, 0.3, 1, 1, false);
        SceneHandler.getInstance().createRotateAnimation(0.3, back, 360, 3);
    }

    @FXML
    private void onBackClick() {
        SceneHandler.getInstance().close2();
    }
}
