package it.informatica.configuratore.threads;

import it.informatica.configuratore.model.Game;
import it.informatica.configuratore.others.Common;
import it.informatica.configuratore.view.SceneHandler;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CarThread extends Thread {

    Game game;
    ImageView mainCar, crash;
    ImageView[] opponents = new ImageView[4];

    float points = 0.0F;


    public CarThread(Scene scene, AnchorPane ap, Game game) {
        mainCar = (ImageView) ap.getChildren().get(2);
        for (int i = 0; i < 4; i++) {
            opponents[i] = (ImageView) ap.getChildren().get(i + 3);
        }
        crash = (ImageView) ap.getChildren().get(7);
        this.game = game;

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN){       //muovi sotto
                if (mainCar.getLayoutY() + Common.CAR_MOVE_UP < Common.DOWN_LIMIT) {
                    mainCar.setLayoutY(mainCar.getLayoutY() + Common.CAR_MOVE_UP);
                }
            }
            else if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP){        //muovi sopra
                if (mainCar.getLayoutY() + Common.CAR_MOVE_UP > Common.UP_LIMIT) {
                    mainCar.setLayoutY(mainCar.getLayoutY() - Common.CAR_MOVE_DOWN);
                }
            }
            else if (event.getCode() == KeyCode.Q || event.getCode() == KeyCode.ESCAPE) {     // chiudo il gioco
                try {
                    savePoints();
                    SceneHandler.getInstance().createPersonalScene();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (event.getCode() == KeyCode.N) {
                savePoints();
                Platform.runLater(() -> {
                    try {
                        SceneHandler.getInstance().createGameScene();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });
    }

    @Override
    public void run() {
        while (game.isPlaying()){
            for (int i = 0; i < opponents.length; i++) {
                double opponentLayoutX = opponents[i].getLayoutX();
                double opponentLayoutY = opponents[i].getLayoutY();
                double mainLayoutX = mainCar.getLayoutX();
                double mainLayoutY = mainCar.getLayoutY();

                if ((opponentLayoutY >= mainLayoutY && opponentLayoutY <= mainLayoutY+46) || (opponentLayoutY+46 >= mainLayoutY && opponentLayoutY+46 <= mainLayoutY+46)){
                    if (mainLayoutX+87 >= opponentLayoutX && !(mainLayoutX >= opponentLayoutX+87)){
                        game.setPlaying(false);
                    }
                } else if (opponentLayoutX < mainLayoutX) {
                    points += 0.1;
                }
            }
            try {
                sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        savePoints();
        crash.setLayoutY(mainCar.getLayoutY() - 30);
        crash.setLayoutX(mainCar.getLayoutX() + 50);

        Platform.runLater(() -> SceneHandler.getInstance().createErrorMessage("Crash ! \n Premi N per una nuova partita \n oppure Q (in alternativa ESC) per uscire", Alert.AlertType.ERROR));
    }

    private void savePoints() {
        game.setNewPoint(String.valueOf(this.points));
    }
}
