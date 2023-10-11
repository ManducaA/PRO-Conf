package it.informatica.configuratore.view;

import it.informatica.configuratore.model.Game;
import it.informatica.configuratore.model.UserHandler;
import it.informatica.configuratore.others.Common;
import it.informatica.configuratore.threads.CarThread;
import it.informatica.configuratore.threads.OpponentThread;
import it.informatica.configuratore.threads.RoadThread;
import javafx.animation.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SceneHandler {

    private SceneHandler() {
    }

    private static final SceneHandler instance = new SceneHandler();
    public static SceneHandler getInstance() {
        return instance;
    }


    private String selectedColor = "Default", color = "Default", theme = "light", font = "Default";



    private Stage stage, stage1 = new Stage(StageStyle.TRANSPARENT);

    private Scene scene, scene1;


    private Alert alert = new Alert(Alert.AlertType.NONE);
    Dimension screenize = Toolkit.getDefaultToolkit().getScreenSize();
    public void init(Stage stage) throws IOException {
        alert.initStyle(StageStyle.UNDECORATED);
        alert.initStyle(StageStyle.TRANSPARENT);
        if (this.stage == null) {
            this.stage = stage;
            this.stage.setTitle(Common.NAME);
            createHomeScene();
            loadFonts();
            changedTheme();
            scene.setFill(new Color(0,0,0,0));
            this.stage.setScene(scene);
            this.stage.show();
        }
    }


    public void close() {
        System.exit(0);
    }

    public void close2() {
        stage1.close();
    }

    public String getTheme() {
        return theme;
    }

    public String getSelectedColor() {
        return selectedColor;
    }

    public String getColor() {
        return color;
    }

    public String getFont() {
        return font;
    }

    private <T> T loadRootFromFXML(String resourceName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resourceName));
        return fxmlLoader.load();
    }

    public void createHomeScene() throws IOException {
        if(scene == null) {
            scene = new Scene(loadRootFromFXML(Common.HOME_VIEW));
            scene.setFill(new Color(0,0,0,0));
        }
        else
            scene.setRoot(loadRootFromFXML(Common.HOME_VIEW));
        stage.setWidth(400);
        stage.setHeight(342);
//        stage.setX((screenize.getWidth() - stage.getWidth())/2);
//        stage.setY((screenize.getHeight() - stage.getHeight())/2);
//        stage.setResizable(false);
    }
    public void createLoginScene() throws IOException {
        if(scene == null) {
            scene = new Scene(loadRootFromFXML(Common.LOGIN_VIEW));
            scene.setFill(new Color(0, 0, 0, 0));
        }
        else
            scene.setRoot(loadRootFromFXML(Common.LOGIN_VIEW));
        stage.setWidth(350);
        stage.setHeight(339);
//        stage.setX((screenize.getWidth() - stage.getWidth())/2);        //per centrare
//        stage.setY((screenize.getHeight() - stage.getHeight())/2);
//        stage.setResizable(false);
    }
    public void createPersonalScene() throws IOException {
        if(scene == null) {
            scene = new Scene(loadRootFromFXML(Common.PERSONAL_VIEW));
            scene.setFill(new Color(0,0,0,0));
        }
        else
            scene.setRoot(loadRootFromFXML(Common.PERSONAL_VIEW));
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE){
                System.exit(0);
            }
        });

        if (UserHandler.getInstance().isLogged()) {
            stage.setTitle(Common.NAME + " | " + UserHandler.getInstance().getU().getUsername());
        }
        stage.setWidth(879);
        stage.setHeight(629);
//        stage.setX((screenize.getWidth() - stage.getWidth())/2);        //per centrare
//        stage.setY((screenize.getHeight() - stage.getHeight())/2);
//        stage.setResizable(false);
    }
    public void createChooseScene() throws IOException {
        if (scene == null) {
            scene = new Scene(loadRootFromFXML(Common.CHOOSING_VIEW));
            scene.setFill(new Color(0, 0, 0, 0));
        } else
            scene.setRoot(loadRootFromFXML(Common.CHOOSING_VIEW));
        stage.setWidth(1050);
        stage.setHeight(850);
//        stage.setX((screenize.getWidth() - stage.getWidth())/2);        //per centrare
//        stage.setY((screenize.getHeight() - stage.getHeight())/2);
//        stage.setResizable(false);

    }
    public void createConfScene() throws IOException {
        if(scene == null) {
            scene = new Scene(loadRootFromFXML(Common.CONFIGURATOR_VIEW));
            scene.setFill(new Color(0,0,0,0));
        }
        else
            scene.setRoot(loadRootFromFXML(Common.CONFIGURATOR_VIEW));

        stage.setWidth(1050);
        stage.setHeight(850);
//        stage.setX((screenize.getWidth() - stage.getWidth())/2);        //per centrare
//        stage.setY((screenize.getHeight() - stage.getHeight())/2);
//        stage.setResizable(false);
    }
    private void createConfirmScene() throws IOException {
        if(scene == null) {
            scene = new Scene(loadRootFromFXML(Common.CONFIRM_VIEW));
            scene.setFill(new Color(0,0,0,0));
        }
        else
            scene.setRoot(loadRootFromFXML(Common.CONFIRM_VIEW));
        stage.setWidth(1050);
        stage.setHeight(727);
//        stage.setX((screenize.getWidth() - stage.getWidth())/2);        //per centrare
//        stage.setY((screenize.getHeight() - stage.getHeight())/2);
//        stage.setResizable(false);
    }
    public void createComingScene() throws IOException {
        scene1 = new Scene(loadRootFromFXML(Common.COMING_VIEW));
        setCSSForScene(scene1);
        scene1.setFill(new Color(0,0,0,0));

        stage1.setScene(scene1);
        stage1.setWidth(565);
        stage1.setHeight(277);
        stage1.setX((screenize.getWidth() - stage1.getWidth())/2);      //per centrare
        stage1.setY((screenize.getHeight() - stage1.getHeight())/2);
        stage1.setResizable(false);
        stage1.show();
    }
    public void createCustomizeScene() throws IOException {
        scene1 = new Scene(loadRootFromFXML(Common.CUSTOM_VIEW));
        setCSSForScene(scene1);
        scene1.setFill(new Color(0,0,0,0));

        stage1.setScene(scene1);
        stage1.setWidth(560);
        stage1.setHeight(270);
        stage1.setX((screenize.getWidth() - stage1.getWidth())/2);      //per centrare
        stage1.setY((screenize.getHeight() - stage1.getHeight())/2);
        stage1.setResizable(false);
        stage1.show();
    }

    public void createGameScene() throws IOException {

        scene.setRoot(loadRootFromFXML("game-view.fxml"));

        stage.setWidth(500);
        stage.setHeight(500);

        AnchorPane ap = (AnchorPane) scene.getRoot();

        Game game = new Game();

        new CarThread(scene, ap, game).start();

        new RoadThread(ap, game).start();

        new OpponentThread(ap, game).start();
    }


    public void createErrorMessage(String message, Alert.AlertType type) {
        alert.setAlertType(type);
        alert.setHeaderText("Attenzione");
        alert.setContentText(message);
        alert.setTitle("Errore");

        alert.show();
    }
    public boolean createInfoMessage(String title, String message, Alert.AlertType type, boolean func) throws IOException {
        alert.setAlertType(type);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.setResizable(false);
        Optional<ButtonType> confirm = alert.showAndWait();
        if(func){
            if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
                SceneHandler.getInstance().createConfirmScene();
                return true;
            }
        }
        return false;
    }

    public void createScaleAnimation(double duration, Node node, double from, double to, int cycle, boolean autoreverse) {
        ScaleTransition st = new ScaleTransition(Duration.seconds(duration), node);
        st.setFromX(from);
        st.setFromY(from);
        st.setToX(to);
        st.setToY(to);
        st.setCycleCount(cycle);
        st.setAutoReverse(autoreverse);
        st.play();
    }
    public void createRotateAnimation(double duration, Node node, double angle, int cycle) {
        RotateTransition rt = new RotateTransition(Duration.seconds(duration), node);
        rt.setDuration(Duration.seconds(duration));
        rt.setByAngle(angle);
        rt.setCycleCount(cycle);
        rt.play();
    }
    public void createScrollAnimation(ScrollPane Pane, double duration, double end, int cycle, boolean autoreverse) {
        Animation animation = new Timeline(
                new KeyFrame(Duration.seconds(duration),
                        new KeyValue(Pane.hvalueProperty(), end)));
        animation.setAutoReverse(autoreverse);
        animation.setCycleCount(cycle);
        animation.play();
    }

    private void loadFonts() {
        for (String font : List.of(Common.RESOURCE_PATH + "fonts/" + "Roboto/Roboto-Regular.ttf", Common.RESOURCE_PATH + "fonts/" + "Roboto/Roboto-Bold.ttf", Common.RESOURCE_PATH + "fonts/" + "Alegreya_Sans_SC/AlegreyaSansSC-Medium.ttf", Common.RESOURCE_PATH + "fonts/" + "Alegreya_Sans_SC/AlegreyaSansSC-Bold.ttf", Common.RESOURCE_PATH + "fonts/" + "OpenDyslexic/OpenDyslexic-Regular.otf", Common.RESOURCE_PATH + "fonts/" + "OpenDyslexic/OpenDyslexic-Bold.otf")) {
            Font.loadFont(Objects.requireNonNull(SceneHandler.class.getResource(font)).toExternalForm(), 10);
        }
    }
    private List<String> loadCSS() {
        List<String> resources = new ArrayList<>();
        for (String style : List.of(Common.RESOURCE_PATH + "css/" + selectedColor + theme + ".css", Common.RESOURCE_PATH + "css/" + font + ".css", Common.RESOURCE_PATH + "css/" + "style.css")) {
            String resource = Objects.requireNonNull(SceneHandler.class.getResource(style)).toExternalForm();
            resources.add(resource);
        }
        return resources;
    }
    private void setCSSForScene(Scene scene) {
        Objects.requireNonNull(scene, "Scene cannot be null");
        List<String> resources = loadCSS();
        scene.getStylesheets().clear();
        for(String resource : resources)
            scene.getStylesheets().add(resource);
    }
    private void setCSSForAlert(Alert alert) {
        Objects.requireNonNull(alert, "Scene cannot be null");
        List<String> resources = loadCSS();
        alert.getDialogPane().getStylesheets().clear();
        for(String resource : resources)
            alert.getDialogPane().getStylesheets().add(resource);
    }
    public void changeTheme() {
        if("dark".equals(theme))
            theme = "light";
        else
            theme = "dark";
        changedTheme();
    }
    private void changedTheme() {
        setCSSForScene(scene);
        setCSSForAlert(alert);
    }
    public void changeMode(String text, String selectedColor, String color) {
        font = text;
        this.selectedColor = selectedColor;
        this.color = color;
        changedTheme();
    }
    public boolean isLightTheme() {
        return theme.equals("light");
    }
}
