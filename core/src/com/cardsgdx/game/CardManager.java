package com.cardsgdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.ObjectSet.ObjectSetIterator;

public class CardManager {
    private final ObjectSet<Card> referenceCards;
    private ObjectSet<Card> playingCards;

    public CardManager(TextureAtlas atlas) {
        // TODO: look into optimizing card creation with atlas.createSprites().
        this.referenceCards = new ObjectSet<>();
        String[] regions = {"Clubs", "Diamonds", "Hearts", "Spades"};
        Sprite backSprite = atlas.createSprite("cardBack");

        for (String region : regions) {
            for (int i = 1; i < 14; i++) {
                Sprite cardFront = atlas.createSprite(region, i);
                Sprite cardBack = new Sprite(backSprite);

                Card card = new Card(cardFront, cardBack);
                this.referenceCards.add(card);
            }
        }
    }

    public void setCardsPosition(int screenWidth, int screenHeight) {
        // TODO: change referenceCards to playingCards once logic is implemented.
        ObjectSetIterator<Card> cards = new ObjectSetIterator<>(this.referenceCards);
        float posX = 0;
        float posY = 0;

        // Sets cards side by side with no padding, starts from the bottom left.
        for (Card card : cards) {
            card.setPosition(posX, posY);

            // Advances to the next card position and check if all the sprite will be on screen.
            posX += Card.WIDTH;
            if (posX + Card.WIDTH > screenWidth) {
                posX = 0;
                posY += Card.HEIGHT;

                // Checks if the card Sprite will go above the screen
                if (posY + Card.HEIGHT > screenHeight) return;
            }
        }
    }

    public void drawCards(SpriteBatch batch) {
        // TODO: change referenceCards to playingCards once logic is implemented.
        this.referenceCards.forEach(card -> card.draw(batch));
    }

    public void processMouseInput(float mouseX, float mouseY) {
        // TODO: change referenceCards to playingCards once logic is implemented.
        // TODO: abstract a input Processing into another class
        // checks overlap for each card.
        this.referenceCards.forEach(card -> {
            if (card.overlaps(mouseX, mouseY) && !card.isMatched) card.turn();
        });
    }

    public ObjectSet<Card> getReferenceCards() {
        return this.referenceCards;
    }

    public ObjectSet<Card> getPlayingCards() {
        return this.playingCards;
    }
}
