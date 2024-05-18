package com.cardsgdx.game;

public class Player {
    private int id;
    private String name;
    private int score;

    public Player(int id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public void addPoints(int value) {
        if (value < 0) throw new RuntimeException("Adding negative values to score");
        this.score += value;
    }

    public void deductPoints(int value) {
        if (value < 0) throw new RuntimeException("Deducting negative values from score");
        this.score -= value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }
}
