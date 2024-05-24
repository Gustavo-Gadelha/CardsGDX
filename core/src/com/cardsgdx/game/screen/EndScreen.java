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
    public static final int PLAYER_LIMIT = 10;

    private final CardGame game;
    private final ScreenViewport viewport;
    private final Stage stage;
    private final Dialog leaderboard;
    private final Table playersTable;
    private final Table buttonTable;

    public EndScreen(CardGame game) {
        this.game = game;

        // Instantiates the viewport and the stage
        this.viewport = new ScreenViewport();
        this.stage = new Stage(this.viewport, this.game.batch);

        // Creating the dialog
        this.leaderboard = new Dialog("Leaderboard", this.game.skin);
        this.leaderboard.setModal(true);
        this.leaderboard.setMovable(true);
        this.leaderboard.setResizable(false);

        // Setting the table references
        this.playersTable = this.leaderboard.getContentTable();
        this.buttonTable = this.leaderboard.getButtonTable();

        // Creates "exit" and "play again" buttons
        this.createButtons();

        // Adding table to stage
        this.playersTable.debug();
        this.stage.addActor(leaderboard);
    }

    private void createButtons() {
        // Adding 'exit' and 'play again' buttons
        TextButton exitButton = new TextButton("Exit", this.game.skin);
        TextButton playAgainButton = new TextButton("Play Again", this.game.skin);

        this.buttonTable.add(exitButton).grow();
        this.buttonTable.add(playAgainButton).grow();

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
                ScreenManager.put(GAME_SCREEN, new GameScreen(EndScreen.this.game));
                EndScreen.this.game.getPlayer().setScore(0);
                EndScreen.this.game.setScreen(ScreenManager.get(GAME_SCREEN));
            }
        });

        this.leaderboard.pack();
    }

    private void updateLeaderboard() {
        this.playersTable.clear();
        this.playersTable.defaults().pad(10);
        this.playersTable.defaults().prefWidth(150);
        this.playersTable.defaults().uniform();

        // Adds current player to the top of the list
        Label currentScore = new Label("Your Score", this.game.skin);
        this.playersTable.add(currentScore).colspan(2);
        this.playersTable.row();
        this.addPlayerToLeaderboard(this.game.getPlayer());

        // Adds a divider between current player and query results
        Label divider = new Label("Top " + PLAYER_LIMIT + " players", this.game.skin);
        this.playersTable.add(divider).colspan(2);
        this.playersTable.row();

        // Queries players and add to playerTable
        List<Player> players = this.game.playerDao.getTop(PLAYER_LIMIT);
        players.forEach(this::addPlayerToLeaderboard);

        this.leaderboard.pack();
    }

    private void addPlayerToLeaderboard(Player player) {
        Label playerName = new Label("NAME NOT FOUND", this.game.skin);
        Label playerScore = new Label(String.valueOf(player.getScore()), this.game.skin);

        if (player.getName() != null) playerName.setText(player.getName());

        this.playersTable.add(playerName);
        this.playersTable.add(playerScore);
        this.playersTable.row();
    }

    @Override
    public void show() {
        // TODO: Start playing some music here
        // Sets the stage as the input processor when this screen becomes the current screen
        Gdx.input.setInputProcessor(this.stage);
        this.updateLeaderboard();
        this.leaderboard.show(this.stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.4f, 1);
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
