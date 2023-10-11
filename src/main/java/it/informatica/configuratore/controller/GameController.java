package it.informatica.configuratore.controller;

import it.informatica.configuratore.others.Common;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.awt.event.KeyAdapter;
import java.util.Objects;
import java.util.Random;

public class GameController extends KeyAdapter {

    @FXML
    private AnchorPane root;
    @FXML
    private ImageView mainRoad, secondaryRoad, mainCar, crash, firstOpponent, secondOpponent, thirdOpponent, forthOpponent;

    Image mainCarImage, mainRoadImage, secondaryRoadImage, opponentImage, crashImage;


    public void initialize() {
        mainCarImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Common.GAME_IMG_PATH + "car_self" + Common.IMAGE_EXTENSION)));
        mainRoadImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Common.GAME_IMG_PATH + "st_road" + Common.IMAGE_EXTENSION)));
        secondaryRoadImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Common.GAME_IMG_PATH + "cross_road" + Common.IMAGE_EXTENSION)));


        mainCar.setImage(mainCarImage);
        mainCar.setLayoutX(150);
        mainCar.setLayoutY(300);

        mainRoad.setImage(mainRoadImage);
        secondaryRoad.setImage(secondaryRoadImage);

        Random random = new Random();
        setOpponentImage(String.valueOf(random.nextInt(1, 4)));
        firstOpponent.setImage(opponentImage);

        setOpponentImage(String.valueOf(random.nextInt(1, 4)));
        secondOpponent.setImage(opponentImage);

        setOpponentImage(String.valueOf(random.nextInt(1, 4)));
        thirdOpponent.setImage(opponentImage);

        setOpponentImage(String.valueOf(random.nextInt(1, 4)));
        forthOpponent.setImage(opponentImage);

        crashImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Common.GAME_IMG_PATH + "crash" + Common.IMAGE_EXTENSION)));
        crash.setImage(crashImage);

    }

    private void setOpponentImage(String id) {
        opponentImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Common.GAME_IMG_PATH + "car_left_" + id + Common.IMAGE_EXTENSION)));
    }
}