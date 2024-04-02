package com.cardsgdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class CardGame extends Game {
    SpriteBatch batch;
    BitmapFont font;
    TextureAtlas atlas;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        atlas = new TextureAtlas("card_atlas/Cards.atlas");
        this.setScreen(new GameScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        atlas.dispose();
    }
}
