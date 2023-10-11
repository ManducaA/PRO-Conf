package it.informatica.configuratore.model;

public class CarHandler {

    private static final CarHandler instance = new CarHandler();    //Early instantiation
    public static CarHandler getInstance() {
        return instance;
    }

    private boolean newConfiguration = true;

    private Car chosen;
    private CarHandler() {
    }

    public void loadCar(String name, String model){
        chosen = new Car(name, model);
    }
    public Car getCar(){
        return chosen;
    }

    public boolean isNewConfiguration() {
        return newConfiguration;
    }
    public void setNewConfiguration(boolean newConfiguration) {
        this.newConfiguration = newConfiguration;
    }
}
