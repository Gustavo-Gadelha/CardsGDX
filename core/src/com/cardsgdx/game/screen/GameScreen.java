package com.cardsgdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.cardsgdx.game.Card;
import com.cardsgdx.game.CardGame;
import com.cardsgdx.game.CardManager;

import static com.cardsgdx.game.screen.ScreenManager.Type.END_SCREEN;

public class GameScreen extends InputAdapter implements Screen {
    public static final float WORLD_WIDTH = CardManager.COLS * Card.WIDTH + CardManager.PADDING * (CardManager.COLS - 1);
    public static final float WORLD_HEIGHT = CardManager.ROWS * Card.HEIGHT + CardManager.PADDING * (CardManager.ROWS - 1);

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
        this.viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT);

        // Vectors declared once and reassigned as needed to save performance on the garbage collector
        this.touchPoint = new Vector2();
        this.worldOffset = new Vector2();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.4f, 1);

        this.viewport.apply(true);
        this.game.batch.setProjectionMatrix(this.viewport.getCamera().combined);

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

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        this.touchPoint.set(screenX, screenY);
        this.cardManager.processMouseInput(this.game.getPlayer(), this.viewport.unproject(this.touchPoint));

        if (this.cardManager.isAllMatched()) {
            this.game.playerDao.insert(this.game.getPlayer());
            this.game.setScreen(ScreenManager.get(END_SCREEN));
        }

        return true;
    }
}
