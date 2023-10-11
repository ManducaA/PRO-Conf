package it.informatica.configuratore.threads;

import it.informatica.configuratore.model.Game;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class RoadThread extends Thread{

    Game game;
    AnchorPane ap;
    ImageView[] roads;
    public RoadThread(AnchorPane ap, Game game) {
        this.ap = ap;
        this.roads = new ImageView[]{(ImageView) ap.getChildren().get(0), (ImageView) ap.getChildren().get(1)};
        this.game = game;
    }

    @Override
    public void run() {
        while(game.isPlaying()){
            if (roads[0].getLayoutX() > -500 && roads[1].getLayoutX() > -500) {
                roads[0].setLayoutX(roads[0].getLayoutX() - 1);
                roads[1].setLayoutX(roads[1].getLayoutX() - 1);
            }
            else if (roads[0].getLayoutX() <= -500){
                roads[0].setLayoutX(500);
                roads[1].setLayoutX(roads[1].getLayoutX() - 1);
            }
            if (roads[1].getLayoutX()<=-500){
                roads[0].setLayoutX(roads[0].getLayoutX() - 1);
                roads[1].setLayoutX(500);
            }

            try {
                sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}