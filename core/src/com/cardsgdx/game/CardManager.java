package com.cardsgdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class CardManager {
    public static final int ROWS = 4;
    public static final int COLS = 10;
    public static final float PADDING = 2f;
    public static final float DELAY = 0.25f;

    private final Array<Card> playingCards;
    private Card previousCard;
    private Card currentCard;
    private final Timer timer;

    public CardManager(TextureAtlas atlas) {
        this.timer = new Timer();

        Array<Sprite> frontSprites = new Array<>();
        Sprite backSprite = atlas.createSprite("cardBack");

        String[] regions = {"Clubs", "Diamonds", "Hearts", "Spades"};
        for (String region : regions) frontSprites.addAll(atlas.createSprites(region));
        frontSprites.shuffle();

        this.playingCards = new Array<>(CardManager.ROWS * CardManager.COLS);

        int total = (CardManager.ROWS * CardManager.COLS) / 2;
        for (int i = 0; i < total; i++) {
            Card card = new Card(frontSprites.pop(), new Sprite(backSprite));
            this.playingCards.add(card, card.createMatch());
        }

        this.playingCards.shuffle();
        this.setCardsPosition();
    }

    public void setCardsPosition() {
        for (int i = 0; i < CardManager.ROWS; i++) {
            for (int j = 0; j < CardManager.COLS; j++) {
                float x = j * (Card.WIDTH + CardManager.PADDING);
                float y = i * (Card.HEIGHT + CardManager.PADDING);
                // 1D index through rows and cols, always works since array size is rows * cols
                this.playingCards.get(i * CardManager.COLS + j).setPosition(x, y);
            }
        }
    }

    public void processMouseInput(float mouseX, float mouseY) {
        // Get the first card at the mouse coordinates and filters it before moving on
        this.currentCard = this.getCardAt(mouseX, mouseY);
        if (this.currentCard == null || this.currentCard.isTurned || this.currentCard.isMatched) return;

        // if there's no previous card, passes the current one to it and skips the rest
        this.currentCard.turn();
        if (this.previousCard == null) {
            this.previousCard = this.currentCard;
            return;
        }

        if (Card.match(this.previousCard, this.currentCard)) {
            this.previousCard.isMatched = true;
            this.currentCard.isMatched = true;
        } else {
            this.turnCards(this.previousCard, this.currentCard);
        }

        this.previousCard = null;
    }

    public Card getCardAt(float mouseX, float mouseY) {
        // Goes through every card and returns the first one that contains the x and y coordinates within its bounds
        for (Card card : this.playingCards) {
            if (card.contains(mouseX, mouseY)) return card;
        }

        return null;
    }

    public void turnCards(Card firstCard, Card secondCard) {
        // Turns the two card parameters in the set delay
        this.timer.scheduleTask(new Task() {
            @Override
            public void run() {
                firstCard.turn();
                secondCard.turn();
            }
        }, CardManager.DELAY);
    }

    public void drawAllCards(SpriteBatch batch) {
        this.playingCards.forEach(card -> card.draw(batch));
    }
}