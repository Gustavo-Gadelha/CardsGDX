package com.cardsgdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class GameScreen implements Screen {
    // declared once and reassigned as needed to save performance on the garbage collector
    private static final Vector2 touchPoint = new Vector2();

    private final CardGame game;
    private final CardManager cardManager;
    private final ExtendViewport viewport;

    public GameScreen(CardGame game) {
        this.game = game;

        // Minimum size to garante all Cards are on the screen at all time
        float minWidth = CardManager.COLS * Card.WIDTH;
        float minHeight = CardManager.ROWS * Card.HEIGHT;
        this.viewport = new ExtendViewport(minWidth, minHeight);
        this.cardManager = new CardManager(game.atlas);
    }

    @Override
    public void show() {
        // TODO: Start playing some music here
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.4f, 1);

        this.viewport.apply(true);
        this.game.batch.setProjectionMatrix(this.viewport.getCamera().combined);

        if (Gdx.input.justTouched()) {
            GameScreen.touchPoint.set(Gdx.input.getX(), Gdx.input.getY());
            Vector2 mousePos = this.viewport.unproject(GameScreen.touchPoint);
            this.cardManager.processMouseInput(mousePos.x, mousePos.y);
        }

        this.game.batch.begin();
        this.cardManager.drawAllCards(this.game.batch);
        this.game.batch.end();
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
