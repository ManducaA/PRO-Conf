package it.informatica.configuratore;

import it.informatica.configuratore.others.Common;
import it.informatica.configuratore.others.ConnectorHandler;
import it.informatica.configuratore.view.SceneHandler;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;


public class ProConf extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        ConnectorHandler.getInstance().setConnector();
        ConnectorHandler.getInstance().getConnector().startConnection();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource(Common.IMG_PATH + "PRO-CONFlogo" + Common.IMAGE_EXTENSION)).openStream()));
        SceneHandler.getInstance().init(stage);
        SceneHandler.getInstance().createHomeScene();
    }
    public static void main(String[] args) {
        launch();
    }
}