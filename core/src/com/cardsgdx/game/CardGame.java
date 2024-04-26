package com.cardsgdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.cardsgdx.game.screen.MenuScreen;

public class CardGame extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public TextureAtlas atlas;
    public Skin skin;

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.atlas = new TextureAtlas("card_atlas/cards.atlas");
        this.skin = new Skin(Gdx.files.internal("ui/cloud-form-ui.json"));
        this.setScreen(new MenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        this.batch.dispose();
        this.font.dispose();
        this.atlas.dispose();
        this.skin.dispose();
    }
}
