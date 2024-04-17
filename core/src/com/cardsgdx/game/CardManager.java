package com.cardsgdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Array.ArrayIterator;

public class CardManager {
    private final Array<Card> playingCards;
    private final int rows;
    private final int cols;
    private Card previousCard;
    private Card currentCard;

    public CardManager(TextureAtlas atlas, int screenWidth, int screenHeight) {
        Array<Sprite> frontSprites = new Array<>();
        Sprite backSprite = atlas.createSprite("cardBack");

        String[] regions = {"Clubs", "Diamonds", "Hearts", "Spades"};
        for (String region : regions) frontSprites.addAll(atlas.createSprites(region));
        frontSprites.shuffle();

        this.rows = MathUtils.floor(screenWidth / Card.WIDTH);
        this.cols = MathUtils.floor(screenHeight / Card.HEIGHT);
        this.playingCards = new Array<>(rows * cols);

        int total = (rows * cols) / 2;
        for (int i = 0; i < total; i++) {
            Card card = new Card(frontSprites.pop(), new Sprite(backSprite));
            this.playingCards.add(card, card.createMatch());
        }

        this.playingCards.shuffle();
        this.setCards();
    }

    public void setCards() {
        // Since playingCards size is rows * cols, there's no need to check if (iter.hasNext());
        ArrayIterator<Card> cards = this.playingCards.iterator();
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                Card card = cards.next();
                card.setPosition(i * Card.WIDTH, j * Card.HEIGHT);
            }
        }
    }

    public void processMouseInput(float mouseX, float mouseY) {
        this.currentCard = this.getOverlapingCard(mouseX, mouseY);
        // Guard clause, only allows (not null) && (not Matched) && (not equal to previousCard) to pass through
        if (this.currentCard == null || this.currentCard.isMatched || this.previousCard == this.currentCard) return;

        this.currentCard.turn();
        if (this.previousCard == null) {
            this.previousCard = this.currentCard;
            return;
        }

        if (Card.match(this.previousCard, this.currentCard)) {
            this.previousCard.isMatched = true;
            this.currentCard.isMatched = true;
        } else {
            this.previousCard.turn();
            this.currentCard.turn();
        }

        this.previousCard = null;
    }

    public Card getOverlapingCard(float mouseX, float mouseY) {
        // Goes through every card and returns the first one to overlap
        for (Card card : this.playingCards) {
            if (card.overlaps(mouseX, mouseY)) return card;
        }

        return null;
    }

    public void drawAllCards(SpriteBatch batch) {
        this.playingCards.forEach(card -> card.draw(batch));
    }
}