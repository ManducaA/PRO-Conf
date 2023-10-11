package it.informatica.configuratore.model;

import it.informatica.configuratore.others.Common;
import it.informatica.configuratore.others.ConnectorHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Game {
    private float oldPoint;
    boolean playing = true;

    private PreparedStatement stmt;

    public boolean isPlaying() {
        return playing;
    }
    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public Game(){
        try {
            stmt = ConnectorHandler.getInstance().getConnector().getConnection().prepareStatement(Common.USER_POINTS);
            stmt.setString(1, String.valueOf(UserHandler.getInstance().getU().getId()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                oldPoint = rs.getFloat(1);
            }
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void setNewPoint(String point) {
        try {
            stmt = ConnectorHandler.getInstance().getConnector().getConnection().prepareStatement(Common.UPDATE_POINTS);
            stmt.setString(1, String.valueOf(UserHandler.getInstance().getU().getId()));
            stmt.setString(2, String.valueOf(oldPoint + Float.parseFloat(point)));
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
