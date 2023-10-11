package it.informatica.configuratore.others;

import java.io.File;

public class Common {

    public static final String IMAGE_EXTENSION = ".png";
    public static final String RESOURCE_PATH = "/it/informatica/configuratore/view/";
    public static final String IMG_PATH = RESOURCE_PATH + "img/";
    public static final String GLOBAL_IMG_PATH = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "it" + File.separator + "informatica" + File.separator + "configuratore" + File.separator + "view" + File.separator + "img" + File.separator;
    public static final String OPTIONAL_PATH = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "it" + File.separator + "informatica" + File.separator + "configuratore" + File.separator + "view" + File.separator + "txt" + File.separator;
    public static final String GAME_IMG_PATH = IMG_PATH + "game" + File.separator;


    public static final String INSERT_USER = "insert into Users(username, password) values(?, ?);";
    public static final String USER_EXIST = "select count(ID) from Users where username=?;";
    public static final String USER_ID  = "select ID from Users where username=?;";
    public static final String USER_PASSWORD = "select ID, password from Users where username=?;";
    public static final String USER_CONFIGURATIONS  = "select * from Configurations where userId=?;";
    public static final String USER_CONFIGURATIONS_NAME  = "select * from Configurations where userId=? AND name LIKE ?;";

    public static final String USER_POINTS = "select points from Point where userId=?;";
    public static final String UPDATE_POINTS = "insert or replace into Point(userId, points) values(?, ?);";

    public static final String EDIT_CONFIGURATION  = "select * from Configurations where id=?;";
    public static final String INSERT_CONFIGURATION = "insert into Configurations(name, marca, modello, colour, wheels, interior, optional1, optional2, optional3, price, userId) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    public static final String UPDATE_CONFIGURATION = "update Configurations set marca=?, modello=?, colour=?, wheels=?, interior=?, optional1=?, optional2=?, optional3=?, price=? where id=?;";
    public static final String DELETE_CONFIGURATION_ID = "delete from Configurations where id=?;";

    public static final String CARS = "select * from car;";

    public static final String LIGHT_ICON = "mdi2l-lightbulb-on";
    public static final String DARK_ICON = "mdi2l-lightbulb-off";
    public static final String EDIT_ICON = "mdi2f-file-document-edit-outline";
    public static final String DELETE_ICON = "mdi2t-trash-can";
    public static final String CHECK_ICON = "mdi2c-check-bold";
    public static final String HOME_ICON = "mdi2h-home";
    public static final String CUSTOMIZE_ICON = "fas-pencil-alt";
    public static final String ABOUT_ICON = "fas-question-circle";

    public static final String CURRENCY = " €";

    public static final String NAME = "PRO-CONF";
    public static final String ABOUT_PROCONF = "\n Questo programma offre diverse possibilità:\n" +
            "\n" +
            "1. Creazione di una configurazione da zero e esportazione in formato PDF senza inserire i propri dati personali.\n\n" +
            "2. Caricamento di un PDF di una configurazione esportata in precedenza per effettuare modifiche.\n\n" +
            "3. Creazione di un account per accedere all'area personale, dove sarà possibile creare una nuova configurazione, modificarne o cancellarne una esistente.\n" +
            "\n\n\t\tGrazie da Pro-Conf!\n\n" +
            "Si prega di notare che ogni immagine Porsche è di proprietà di Dr. Ing. h.c. F. Porsche AG, in Germania, e ogni immagine BMW è di proprietà di BMW AG, con sede a Monaco di Baviera, Germania.";

    public static final String HOME_VIEW = "home.fxml";
    public static final String CHOOSING_VIEW = "modelchooser-view.fxml";
    public static final String PERSONAL_VIEW = "personal-view.fxml";
    public static final String CONFIGURATOR_VIEW = "conf-view.fxml";
    public static final String CONFIRM_VIEW = "confirm-view.fxml";
    public static final String LOGIN_VIEW = "login-view.fxml";
    public static final String COMING_VIEW = "coming-view.fxml";
    public static final String CUSTOM_VIEW = "custom-view.fxml";

    public static final String LIGHT_TEXT = "Turn light off";
    public static final String DARK_TEXT = "Turn light on" ;

    public static final double CAR_MOVE_UP = 5;
    public static final double CAR_MOVE_DOWN = 5;
    public static final double DOWN_LIMIT = 314;
    public static final double UP_LIMIT = 130;
}