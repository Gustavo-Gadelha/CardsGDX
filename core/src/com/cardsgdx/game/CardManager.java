package com.cardsgdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import java.util.Iterator;

public class CardManager {
    public static final int ROWS = 4;
    public static final int COLS = 10;
    public static final float PADDING = 2f;
    public static final float DELAY = 0.25f;

    private final Array<Card> playingCards;
    private Card previousCard;
    private Card currentCard;
    private final Timer timer;
    private final Task turnCards;

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

        this.turnCards = new Task() {
            @Override
            public void run() {
                CardManager.this.playingCards.forEach(card -> {
                    if (card.isTurned && !card.isMatched) card.turn();
                });
                CardManager.this.previousCard = null;
            }
        };
    }

    public void setCardsPosition() {
        Iterator<Card> cards = this.playingCards.iterator();
        for (int i = 0; i < CardManager.ROWS; i++) {
            for (int j = 0; j < CardManager.COLS; j++) {
                if (!cards.hasNext()) return;
                Card card = cards.next();
                card.setPosition(j * (Card.WIDTH + CardManager.PADDING), i * (Card.HEIGHT + CardManager.PADDING));
            }
        }
    }

    public void processMouseInput(float mouseX, float mouseY) {
        // Skips processing if there's a schedule task
        // This means you can't select another card while 2 cards are turned up
        if (!this.timer.isEmpty()) return;

        // Get the first card at the mouse coordinates and filters it before moving on
        this.currentCard = this.getCardAt(mouseX, mouseY);
        if (this.currentCard == null || this.currentCard.isTurned || this.currentCard.isMatched) return;

        this.currentCard.turn();
        if (this.previousCard == null) {
            this.previousCard = this.currentCard;
            return;
        }

        if (Card.match(this.previousCard, this.currentCard)) {
            this.previousCard.isMatched = true;
            this.currentCard.isMatched = true;
            this.previousCard = null;
        } else {
            this.timer.scheduleTask(this.turnCards, CardManager.DELAY);
        }
    }

    public Card getCardAt(float mouseX, float mouseY) {
        // Goes through every card and returns the first one to overlap
        for (Card card : this.playingCards) {
            if (card.contains(mouseX, mouseY)) return card;
        }

        return null;
    }

    public void drawAllCards(SpriteBatch batch) {
        this.playingCards.forEach(card -> card.draw(batch));
    }
}