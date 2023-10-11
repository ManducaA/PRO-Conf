package it.informatica.configuratore.controller;

import it.informatica.configuratore.others.Common;
import it.informatica.configuratore.others.ConnectorHandler;
import it.informatica.configuratore.view.SceneHandler;
import it.informatica.configuratore.model.UserHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class LoginController {

    @FXML
    private Button Registrati, log;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private boolean registration = false;


    @FXML
    private void initialize() {
        passwordField.setOnKeyPressed(event -> {
            if (Objects.requireNonNull(event.getCode()) == KeyCode.ENTER) {
                login();
            }
        });
    }

    @FXML
    private void registrati() throws SQLException, IOException {
        if(usernameField.getText().length() != 0 && passwordField.getText().length() != 0) {
            String username = String.valueOf(usernameField.getText());
            String password = String.valueOf(passwordField.getText());

            if (!UserHandler.getInstance().checkUsername(username)) {       //se non trova l'username nel DB
                UserHandler.getInstance().createUser(username, password);                                       //registra nuovo utente
                registration=true;
                SceneHandler.getInstance().createInfoMessage("Registrazione", "Utente registrato con successo", Alert.AlertType.CONFIRMATION, false);
            }
            else {
                SceneHandler.getInstance().createErrorMessage("Username già utilizzato da altri!", Alert.AlertType.ERROR);
            }
        }
        else {
            SceneHandler.getInstance().createErrorMessage("Non puoi lasciare i campi vuoti!", Alert.AlertType.WARNING);
        }
    }
    @FXML
    private void login() {
        if(usernameField.getText().length() != 0 && passwordField.getText().length() != 0) {
            String username = String.valueOf(usernameField.getText());
            String password = String.valueOf(passwordField.getText());

            try {
                if (!UserHandler.getInstance().checkUsername(username)) {            //se non trova l'username nel DB
                    SceneHandler.getInstance().createErrorMessage("Nessuno registrato con quel nome", Alert.AlertType.WARNING);
                }
                else {
                    if (UserHandler.getInstance().checkPassword(username, password)){
                        UserHandler.getInstance().setU(username);

                        PreparedStatement stmt = ConnectorHandler.getInstance().getConnector().getConnection().prepareStatement(Common.USER_ID);
                        stmt.setString(1, UserHandler.getInstance().getU().getUsername());
                        ResultSet rs = stmt.executeQuery();
                        int result = rs.getInt(1);
                        if (!registration){
                            UserHandler.getInstance().setU(username);
                        }
                        stmt.close();

                        UserHandler.getInstance().getU().setId(result);

                        SceneHandler.getInstance().createPersonalScene();
                    }
                    else {
                        SceneHandler.getInstance().createErrorMessage("Password errata!", Alert.AlertType.ERROR);
                    }
                }
            } catch (SQLException | IOException e) {
                e.printStackTrace();
                SceneHandler.getInstance().createErrorMessage("DATABASE OR FXML ERROR!", Alert.AlertType.ERROR);
            }
        }
        else {
            SceneHandler.getInstance().createErrorMessage("Non puoi lasciare i campi vuoti!", Alert.AlertType.WARNING);
        }
    }


    @FXML
    private void onIdeaClick() throws IOException {
        SceneHandler.getInstance().createHomeScene();
    }
    @FXML
    private void onCloseClick() {
        SceneHandler.getInstance().close();;
    }
}
