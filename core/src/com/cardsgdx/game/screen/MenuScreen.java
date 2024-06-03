package com.cardsgdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.cardsgdx.game.CardGame;
import com.cardsgdx.game.ui.AlertDialog;
import com.cardsgdx.game.ui.ConfirmDialog;

import static com.cardsgdx.game.screen.ScreenManager.ScreenType.GAME_SCREEN;

public class MenuScreen implements Screen {
    private final CardGame game;
    private final ScreenViewport viewport;
    private final Stage stage;
    private final AlertDialog alertDialog;
    private final ConfirmDialog confirmDialog;
    private String playerName;

    public MenuScreen(CardGame game) {
        this.game = game;

        // Instantiates the viewport and the stage
        this.viewport = new ScreenViewport();
        this.stage = new Stage(this.viewport, this.game.batch);

        // Alert dialog for notifying an empty name
        this.alertDialog = new AlertDialog(this.game.skin);

        // Confim dialog for exiting the game
        this.confirmDialog = new ConfirmDialog(this.game.skin) {
            @Override
            protected void result(Object object) {
                if ((boolean) object) Gdx.app.exit();
            }
        };

        // Creating and adding table to stage
        Table table = this.createTable();
        this.stage.addActor(table);
    }

    public Table createTable() {
        // Creating table and setting padding and width of the table
        Table table = new Table(this.game.skin);
        table.setFillParent(true);
        table.defaults().pad(10);
        table.defaults().width(300);

        // Creating name input field, along with "start" and "exit" buttons
        TextField nameInput = new TextField("Your name here", this.game.skin);
        TextButton startButton = new TextButton("Start Game", this.game.skin);
        TextButton exitButton = new TextButton("Exit", this.game.skin);

        // Adding field and buttons to table, use table.row() to signify end of the current row
        table.add(nameInput);
        table.row();
        table.add(startButton).fillX();
        table.row();
        table.add(exitButton).fillX();

        nameInput.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (nameInput.getText().length() > 50) {
                    MenuScreen.this.playerName = nameInput.getText().substring(0, 50).trim();
                } else {
                    MenuScreen.this.playerName = nameInput.getText().trim();
                }
            }
        });

        // Clears the input when you click on the TextField
        nameInput.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                nameInput.setText("");
            }
        });

        // Sets the game screen to GameScreen
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (MenuScreen.this.playerName != null && !MenuScreen.this.playerName.isEmpty()) {
                    MenuScreen.this.game.createPlayer(MenuScreen.this.playerName);
                    MenuScreen.this.game.setScreen(GAME_SCREEN);
                } else {
                    MenuScreen.this.alertDialog.show(MenuScreen.this.stage, "Invalid name", "Please enter a non-empty and less than 50 character name");
                }
            }
        });

        // Exits the game
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuScreen.this.confirmDialog.show(MenuScreen.this.stage, "Exit the game", "Are you sure you want to exit the game?");
            }
        });

        table.pack();
        return table;
    }

    @Override
    public void show() {
        // Sets the stage as the input processor when this screen becomes the current screen
        Gdx.input.setInputProcessor(this.stage);
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

    }

    @Override
    public void resume() {

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
