package it.informatica.configuratore.model;

public class Configuration {

    private int id;
    private String name, make, model, colour, wheels, inside;
    private boolean optional1, optional2, optional3;
    private float price;


    public boolean hasOptional1() {
        return optional1;
    }

    public void setOptional1(boolean optional1) {
        this.optional1 = optional1;
    }

    public boolean hasOptional2() {
        return optional2;
    }

    public void setOptional2(boolean optional2) {
        this.optional2 = optional2;
    }

    public boolean hasOptional3() {
        return optional3;
    }

    public void setOptional3(boolean optional3) {
        this.optional3 = optional3;
    }



    public Configuration(int i1, String nome, String marca, String modello, String colore, String cerchi, String interni, boolean optional1, boolean optional2, boolean optional3, float price) {
        setId(i1);
        setName(nome);
        setMake(marca);
        setModel(modello);
        setColour(colore);
        setWheels(cerchi);
        setInside(interni);
        setPrice(price);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMake() {
        return make;
    }
    public void setMake(String make) {
        this.make = make;
    }


    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    public String getColour() {
        return colour;
    }
    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getWheels() {
        return wheels;
    }
    public void setWheels(String wheels) {
        this.wheels = wheels;
    }

    public String getInside() {
        return inside;
    }
    public void setInside(String inside) {
        this.inside = inside;
    }

    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
}
