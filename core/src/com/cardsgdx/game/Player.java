package com.cardsgdx.game;

public class Player {
    private Long id;
    private String name;
    private int score;

    public Player(Long id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public Player(String name) {
        this(null, name, 0);
    }

    public Player() {
    }

    public void addPoints(int value) {
        if (value < 0) throw new RuntimeException("Adding negative values to score");
        this.score += value;
    }

    public void deductPoints(int value) {
        if (value < 0) throw new RuntimeException("Deducting negative values from score");
        this.score -= value;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
