package com.cardsgdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.cardsgdx.game.Card;
import com.cardsgdx.game.CardGame;
import com.cardsgdx.game.CardManager;


public class GameScreen implements Screen {
    private final CardGame game;
    private final CardManager cardManager;
    private final ExtendViewport viewport;
    private final Vector2 touchPoint;
    private final Vector2 worldOffset;

    public GameScreen(CardGame game) {
        this.game = game;

        // Card manager keeps track of card array
        this.cardManager = new CardManager(game.atlas);

        // Minimum size to garante all Cards are on the screen
        float worldWidth = CardManager.COLS * Card.WIDTH + CardManager.PADDING * (CardManager.COLS - 1);
        float worldHeight = CardManager.ROWS * Card.HEIGHT + CardManager.PADDING * (CardManager.ROWS - 1);
        this.viewport = new ExtendViewport(worldWidth, worldHeight);

        // Vectors declared once and reassigned as needed to save performance on the garbage collector
        this.touchPoint = new Vector2();
        this.worldOffset = new Vector2();
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
            this.touchPoint.set(Gdx.input.getX(), Gdx.input.getY());
            Vector2 mousePos = this.viewport.unproject(this.touchPoint);
            this.cardManager.processMouseInput(this.game.player, mousePos.x, mousePos.y);
            Gdx.app.log("Score", String.valueOf(this.game.player.getScore())); // TODO: Delete this once project is done
        }

        this.game.batch.begin();
        this.cardManager.drawAllCards(this.game.batch);
        this.game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        this.viewport.update(width, height, true);

        // Calculate the x and y offset in world coordinates
        float offsetX = (this.viewport.getWorldWidth() - this.viewport.getMinWorldWidth()) / 2;
        float offsetY = (this.viewport.getWorldHeight() - this.viewport.getMinWorldHeight()) / 2;

        // Pass offsets to a vector and project to turn into screen coordinates
        this.worldOffset.set(offsetX, offsetY);
        Vector2 screenOffset = this.viewport.project(worldOffset);

        // Set viewport offset with screen coordinates
        this.viewport.setScreenPosition(MathUtils.round(screenOffset.x), MathUtils.round(screenOffset.y));
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
