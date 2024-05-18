package com.cardsgdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.cardsgdx.game.CardGame;
import com.cardsgdx.game.Player;

import java.util.List;

import static com.cardsgdx.game.screen.ScreenManager.Type.GAME_SCREEN;

public class EndScreen implements Screen {
    private final CardGame game;
    private final ScreenViewport viewport;
    private final Stage stage;
    private final Dialog leaderboard;

    public EndScreen(CardGame game) {
        this.game = game;

        // Instantiates the viewport and the stage
        this.viewport = new ScreenViewport();
        this.stage = new Stage(this.viewport, this.game.batch);

        // Queries 10 players with the highest score and creates the table
        List<Player> players = this.game.playerDao.getTop(10);
        this.leaderboard = this.createLeaderboard(players);

        // Adding table to stage
        this.stage.addActor(leaderboard);
    }

    private Dialog createLeaderboard(List<Player> players) {
        // Instantiating the dialog
        Dialog dialog = new Dialog("Leaderboard", this.game.skin);
        dialog.setModal(true);
        dialog.setMovable(true);
        dialog.setResizable(false);

        // Creating leaderboard and setting pad and width of the leaderboard
        Table leaderboard = dialog.getContentTable();
        leaderboard.defaults().pad(10);
        leaderboard.defaults().width(100);

        // TODO: add current player score to the top of the leaderboard
        players.forEach(player -> {
            Label playerName = new Label(player.getName(), this.game.skin);
            Label playerScore = new Label(String.valueOf(player.getScore()), this.game.skin);

            leaderboard.add(playerName);
            leaderboard.add(playerScore);
            leaderboard.row();
        });

        // Adding 'exit' and 'play again' buttons
        Table leaderboardButtons = dialog.getButtonTable();
        TextButton exitButton = new TextButton("Exit", this.game.skin);
        TextButton playAgainButton= new TextButton("Play Again", this.game.skin);

        leaderboardButtons.add(exitButton).grow();
        leaderboardButtons.add(playAgainButton).grow();

        // Exits the game
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        // Creates a new GameScreen and keeps the same Player
        playAgainButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                EndScreen.this.game.setScreen(ScreenManager.getNew(GAME_SCREEN, new GameScreen(EndScreen.this.game)));
            }
        });

        dialog.pack();
        return dialog;
    }

    @Override
    public void show() {
        // TODO: Start playing some music here
        // TODO: update the leaderboard each time the game enter this screen
        // Sets the stage as the input processor when this screen becomes the current screen
        Gdx.input.setInputProcessor(this.stage);
        this.leaderboard.show(this.stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.4f, 1);
        // TODO: Draw the top 10 on the leaderboard here
        // No need to update the camera or set projection matrix, stage already does it
        this.viewport.apply(true);
        this.stage.act();
        this.stage.draw();
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
        // Won't dispose the SpriteBatch since it doesn't "own" it
        // This way it can be reused on the next screen
        this.stage.dispose();
    }
}
