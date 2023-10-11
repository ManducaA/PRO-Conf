package it.informatica.configuratore.model;

public class User {
    private String username;
    private int id;

    public User(String username){
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
