package it.informatica.configuratore.threads;

import it.informatica.configuratore.model.Game;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.Random;

public class OpponentThread extends Thread {

    Game game;
    AnchorPane ap;
    ImageView[] opponents = new ImageView[4];
    double[] layouts = new double[4];

    public OpponentThread(AnchorPane ap, Game game) {
        this.ap = ap;
        for (int i = 0; i < 4; i++) {
            opponents[i] = (ImageView) ap.getChildren().get(i+3);
            layouts[i] = opponents[i].getLayoutX();
        }
        this.game = game;
    }

    @Override
    public void run() {
        Random random = new Random();
        while(game.isPlaying()){
            for (int i = 0; i < 4; i++) {

                int speed = random.nextInt(2, 8);
                if(opponents[i].getLayoutX() > -94) {
                    opponents[i].setLayoutX(opponents[i].getLayoutX() - speed);
                }
                else {
                    opponents[i].setLayoutX(layouts[i]);
                }
            }
            try {
                sleep(15);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
