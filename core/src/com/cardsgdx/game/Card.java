package com.cardsgdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Card {
    public static final float WIDTH = 128f;
    public static final float HEIGHT = WIDTH * 1.484375f;

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
        this.setSize(WIDTH, HEIGHT); // 128x190
    }

    public Card(Card card) {
        this.match = card;
        this.front = new Sprite(card.front);
        this.back = new Sprite(card.back);
        this.isTurned = card.isTurned;
        this.isMatched = card.isMatched;
        this.setSize(WIDTH, HEIGHT); // 128x190
        card.match = this;
    }

    public void setSize(float width, float height) {
        this.front.setSize(width, height);
        this.back.setSize(width, height);
    }

    public void setPosition(float x, float y) {
        this.front.setPosition(x, y);
        this.back.setPosition(x, y);
    }

    public void draw(SpriteBatch batch) {
        if (this.isTurned) {
            this.front.draw(batch);
        } else {
            this.back.draw(batch);
        }
    }

    public static boolean match(Card firstCard, Card secondCard) {
        return firstCard.match == secondCard && secondCard.match == firstCard;
    }

    public void turn() {
        this.isTurned = !this.isTurned;
    }
}
