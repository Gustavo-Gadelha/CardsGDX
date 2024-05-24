package com.cardsgdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.cardsgdx.game.dao.PlayerDao;
import com.cardsgdx.game.screen.ScreenManager;

import static com.cardsgdx.game.screen.ScreenManager.Type.MENU_SCREEN;

public class CardGame extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public TextureAtlas atlas;
    public Skin skin;
    public PlayerDao playerDao;

    private Player player;

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.atlas = new TextureAtlas("card_atlas/cards.atlas");
        this.skin = new Skin(Gdx.files.internal("ui/default-gdx/uiskin.json"));

        this.player = new Player();
        this.playerDao = new PlayerDao();

        ScreenManager.createFrom(this);
        this.setScreen(ScreenManager.get(MENU_SCREEN));
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
        this.playerDao.dispose();
        ScreenManager.disposeAll();
    }

    public void createPlayer(String name) {
        this.player = this.playerDao.insert(new Player(name));
    }

    public Player getPlayer() {
        return this.player;
    }
}
