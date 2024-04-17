package com.cardsgdx.game;

import com.badlogic.gdx.utils.Array;

public class Player {
    private static final Array<Player> players = new Array<>();

    private final String name;
    private int score;

    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    public void addPoints(int value) {
        if (value < 0) throw new RuntimeException("Adding negative values to score");
        this.score += value;
    }

    public void deductPoints(int value) {
        if (value < 0) throw new RuntimeException("Deducting negative values from score");
        this.score -= value;
    }

    public static Array<Player> getPlayers() {
        return Player.players;
    }

    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }
}
