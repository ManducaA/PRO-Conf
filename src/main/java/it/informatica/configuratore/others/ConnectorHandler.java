package it.informatica.configuratore.others;

public class ConnectorHandler {

    private static final ConnectorHandler instance = new ConnectorHandler();
    DatabaseConnector connector;


    private ConnectorHandler() {
    }

    public static ConnectorHandler getInstance() {
        return instance;
    }

    public DatabaseConnector getConnector() {
        return connector;
    }

    public void setConnector() {
        this.connector = new DatabaseConnector();
    }
}
