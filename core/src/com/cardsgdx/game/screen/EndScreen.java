package com.cardsgdx.game.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.cardsgdx.game.CardGame;

public class EndScreen implements Screen {
    private final CardGame game;
    private final ScreenViewport viewport;

    public EndScreen(CardGame game) {
        this.game = game;
        this.viewport = new ScreenViewport();
    }

    @Override
    public void show() {
        // TODO: Start playing some music here
    }

    @Override
    public void render(float delta) {
        // TODO: Draw the top 10 on the leaderboard here
    }

    @Override
    public void resize(int width, int height) {
        this.viewport.update(width, height, true);
    }

    @Override
    public void pause() {
        // TODO: if playing some music here, pause it here
    }

    @Override
    public void resume() {
        // TODO: if music if paused, resume it here
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
