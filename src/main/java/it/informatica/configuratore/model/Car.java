package it.informatica.configuratore.model;

import it.informatica.configuratore.others.Common;

public class Car {

    private final String path, optionalPath, globalImgPath, marca, modello;
    private String colour = "colore-1", wheels = "cerchio-1", inside = "interno-1", optional1Text, optional2Text, optional3Text;
    private float price;
    private int confId;
    private boolean optional1 = false, optional2 = false, optional3 = false;

    public Car(String marca, String modello){
        this.marca = marca;
        this.modello = modello;

        path = Common.IMG_PATH + getMarca();
        optionalPath = Common.OPTIONAL_PATH + getMarca();
        globalImgPath = Common.GLOBAL_IMG_PATH + getMarca();
    }

    public String getMarca() {
        return marca;
    }

    public String getPath() {
        return path;
    }

    public String getOptionalPath() {
        return optionalPath;
    }

    public String getGlobalImgPath() {
        return globalImgPath;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getWheels(){
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

    public void increasePrice(int p) {
        this.price += p;
    }

    public void setPrice(float p) {
        this.price = p;
    }

    public float getPrice() {
        return price;
    }

    public void decreasePrice(int p) {
        this.price -= p;
    }

    public String getModello() {
        return this.modello;
    }

    public void setConfID(int id) {
        this.confId = id;
    }

    public int getConfId() {
        return confId;
    }


    public void setOptional1(boolean b) {
        optional1 = b;
    }
    public void setOptional2(boolean b) {
        optional2 = b;
    }
    public void setOptional3(boolean b) {
        optional3 = b;
    }
    public boolean hasOptional1() {
        return optional1;
    }
    public boolean hasOptional2() {
        return optional2;
    }
    public boolean hasOptional3() {
        return optional3;
    }


    public void setOptional1Text(String t) {
        optional1Text = t;
    }
    public void setOptional2Text(String t) {
        optional2Text = t;
    }
    public void setOptional3Text(String t) {
        optional3Text = t;
    }
    public String getOptional1Text() {
        return optional1Text;
    }
    public String getOptional2Text() {
        return optional2Text;
    }
    public String getOptional3Text() {
        return optional3Text;
    }
}
