package com.game.a1520.model;

import java.io.Serializable;

public class Opponent implements Serializable {
    private int id;
    private String name;
    private String country;

    public Opponent() {
    }

    public Opponent(int ID, String opponentName, String opponentCountry) {
        this.id = ID;
        this.name = opponentName;
        this.country = opponentCountry;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public void setID(int ID) {
        this.id = ID;
    }

    public void setName(String opponentName) {
        this.name = opponentName;
    }

    public void setCountry(String opponentCountry) {
        this.country = opponentCountry;
    }
}
