package com.cardsgdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class GameScreen implements Screen {
    private final CardGame game;
    private CardManager cardManager;
    private OrthographicCamera camera;
    private ExtendViewport viewport;

    // declared once and reassigned as needed to save performance on the garbage collector
    private static final Vector3 touchPoint = new Vector3();

    public GameScreen(CardGame game) {
        this.game = game;

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false);
        this.viewport = new ExtendViewport(1280, 800, camera);

        this.cardManager = new CardManager(game.atlas, 1280, 800);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.4f, 1);

        this.camera.update();
        this.game.batch.setProjectionMatrix(camera.combined);

        if (Gdx.input.justTouched()) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.input.getY();
            Vector3 mousePos = camera.unproject(touchPoint.set(mouseX, mouseY, 0));
            this.cardManager.processMouseInput(mousePos.x, mousePos.y);
        }

        this.game.batch.begin();
        this.cardManager.drawAllCards(this.game.batch);
        this.game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
}
