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

        // ~135x190 (135x189.44)
        this.setSize(WIDTH, HEIGHT);
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

    public Card createMatch(Card card) {
        Sprite cardFront = new Sprite(card.front);
        Sprite cardBack = new Sprite(card.back);
        Card match = new Card(cardFront, cardBack);
        match.setMatch(card);
        card.setMatch(match);

        return match;
    }

    public void setMatch(Card match) {
        this.match = match;
    }

    public boolean match(Card card) {
        // Tries to match a card and return if the card is matched
        if (this.match == card) this.isMatched = true;
        return this.isMatched;
    }

    public void turn() {
        this.isTurned = !this.isTurned;
    }

    public boolean isTurned() {
        return this.isTurned;
    }

    public boolean isMatched() {
        return this.isMatched;
    }
}
