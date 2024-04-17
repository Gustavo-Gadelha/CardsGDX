package com.cardsgdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Card {
    public static final float WIDTH = 128f;
    public static final float HEIGHT = WIDTH * 1.48f;

    private Card match;
    private final Sprite front;
    private final Sprite back;
    public boolean isTurned;
    public boolean isMatched;

    public Card(Sprite front, Sprite back) {
        this.match = null;
        this.front = front;
        this.back = back;
        this.isTurned = false;
        this.isMatched = false;

        // ~128x190 (128x189.44)
        this.setSize(WIDTH, HEIGHT);
    }

    public Card createMatch() {
        Card match = new Card(new Sprite(this.front), new Sprite(this.back));
        match.setMatch(this);
        this.setMatch(match);
        return match;
    }

    public void setSize(float width, float height) {
        this.front.setSize(width, height);
        this.back.setSize(width, height);
    }

    public void setPosition(float x, float y) {
        this.front.setPosition(x, y);
        this.back.setPosition(x, y);
    }

    public boolean overlaps(float x, float y) {
        if (this.isTurned) {
            return this.front.getBoundingRectangle().contains(x, y);
        } else {
            return this.back.getBoundingRectangle().contains(x, y);
        }
    }

    public void draw(SpriteBatch batch) {
        if (this.isTurned) {
            this.front.draw(batch);
        } else {
            this.back.draw(batch);
        }
    }

    public void setMatch(Card card) {
        this.match = card;
    }

    public static boolean match(Card card1, Card card2) {
        return card1.match == card2 && card2.match == card1;
    }

    public void turn() {
        this.isTurned = !this.isTurned;
    }
}
