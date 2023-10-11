package it.informatica.configuratore.model;

import it.informatica.configuratore.others.ConnectorHandler;
import it.informatica.configuratore.others.Common;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserHandler {

    User u;

    private static final UserHandler instance = new UserHandler();
    public static UserHandler getInstance() {
        return instance;
    }


    private UserHandler() {
    }
    private boolean logged = false;
    public void setU(String username) {
        this.u = new User(username);
        this.logged = true;
    }
    public User getU() {
        return u;
    }
    public boolean isLogged() {
        return logged;
    }
    public boolean checkUsername(String username) throws SQLException {
        //controlla la presenza di "username" nel DB quando ci si registra

        PreparedStatement stmt = ConnectorHandler.getInstance().getConnector().getConnection().prepareStatement(Common.USER_EXIST);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        int result = rs.getInt(1); //Notazione con indice

        stmt.close();

        return result != 0;
    }
    public void createUser(String username, String password) {
        String encryptedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        try {
            PreparedStatement stmt = ConnectorHandler.getInstance().getConnector().getConnection().prepareStatement(Common.INSERT_USER);
            stmt.setString(1, username);
            stmt.setString(2, encryptedPassword);
            stmt.execute();
            stmt.close();
            UserHandler.getInstance().setU(username);                 //crea oggetto utente
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean checkPassword(String username, String password) throws SQLException {
        PreparedStatement stmt = ConnectorHandler.getInstance().getConnector().getConnection().prepareStatement(Common.USER_PASSWORD);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        String enpassword;

        enpassword = rs.getString("password");                  //Notazione con nome

        return BCrypt.checkpw(password, enpassword);
    }
}
