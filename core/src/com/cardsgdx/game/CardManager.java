package com.cardsgdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class CardManager {
    public static final int ROWS = 4;
    public static final int COLS = 10;
    public static final float PADDING = 2f;
    public static final float DELAY = 0.25f;


    private final Array<Card> playingCards;
    private int numberOfMatches;
    private Card previousCard;
    private final Timer timer;

    public CardManager(TextureAtlas atlas) {
        this.timer = new Timer();
        this.numberOfMatches = 0;

        final Array<Sprite> frontSprites = new Array<>(true, 52, Sprite.class);
        final Sprite backSprite = atlas.createSprite("cardBack");

        final String[] regions = new String[]{"Clubs", "Diamonds", "Hearts", "Spades"};
        for (String region : regions) frontSprites.addAll(atlas.createSprites(region));
        frontSprites.shuffle();
        frontSprites.shrink();

        this.playingCards = new Array<>(true, ROWS * COLS, Card.class);

        final int total = (ROWS * COLS) / 2;
        for (int i = 0; i < total; i++) {
            Card card = new Card(frontSprites.pop(), new Sprite(backSprite));
            this.playingCards.add(card, new Card(card));
        }

        this.playingCards.shuffle();
        this.playingCards.shrink();
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

    public void processMouseInput(Player player, Vector2 coordinates) {
        // Get the first card at the mouse coordinates and filters it before moving on
        Card currentCard = this.getCardAt(coordinates.x, coordinates.y);
        if (currentCard == null || currentCard.isTurned || currentCard.isMatched) return;

        // if there's no previous card, passes the current one to it and skips the rest
        currentCard.turn();
        if (this.previousCard == null) {
            this.previousCard = currentCard;
            return;
        }

        if (Card.match(this.previousCard, currentCard)) {
            this.previousCard.isMatched = true;
            currentCard.isMatched = true;
            this.numberOfMatches += 2;
            player.addPoints(80);
        } else {
            this.turnCards(this.previousCard, currentCard);
            player.deductPoints(10);
        }

        this.previousCard = null;
    }

    public Card getCardAt(float mouseX, float mouseY) {
        int cardRow = MathUtils.floor(mouseY / (Card.HEIGHT + CardManager.PADDING));
        int cardCol = MathUtils.floor(mouseX / (Card.WIDTH + CardManager.PADDING));

        if (CardManager.withinRows(cardRow) && CardManager.withinCols(cardCol)) {
            return this.playingCards.get(cardRow * CardManager.COLS + cardCol);
        } else {
            return null;
        }
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

    public boolean isAllMatched() {
        return (this.numberOfMatches >= this.playingCards.size);
    }

    public void drawAllCards(SpriteBatch batch) {
        this.playingCards.forEach(card -> card.draw(batch));
    }

    public static boolean withinRows(int row) {
        return (row >= 0 && row < CardManager.ROWS);
    }

    public static boolean withinCols(int col) {
        return (col >= 0 && col < CardManager.COLS);
    }
}