package com.game.a1520.model;

import java.io.Serializable;

public class OpponentHands implements Serializable {
    //"name": "May", "left": 5, "right": 5, "guess": 10
    private String name;
    private int left;
    private int right;
    private int guess;

    public OpponentHands() {
    }

    public OpponentHands(String name, int left, int right, int guess) {
        this.name = name;
        this.left = left;
        this.right = right;
        this.guess = guess;
    }

    public String getName() {
        return name;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getGuess() {
        return guess;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public void setGuess(int guess) {
        this.guess = guess;
    }
}
