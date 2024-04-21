package com.cardsgdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.cardsgdx.game.dialogBoxes.AlertDialog;

public class MenuScreen implements Screen {
    private final CardGame game;
    private final ScreenViewport viewport;
    private final Stage stage;
    private final AlertDialog alertDialog;
    private String playerName;

    public MenuScreen(CardGame game) {
        this.game = game;

        // Instantiates the viewport and the stage, also passes the stage to the input processor
        this.viewport = new ScreenViewport();
        this.stage = new Stage(this.viewport, this.game.batch);
        Gdx.input.setInputProcessor(stage);

        // Alert dialog
        this.alertDialog = new AlertDialog(this.game.skin);

        // Instantiates the skin and configures the table
        Table table = new Table(this.game.skin);
        table.setFillParent(true);
        table.defaults().pad(10);
        table.defaults().width(300);

        // Creation of buttons and the name input
        TextField nameInput = new TextField("Your name here", this.game.skin);
        TextButton startButton = new TextButton("Start Game", this.game.skin);
        TextButton exitButton = new TextButton("Exit", this.game.skin);

        // Adding buttons and input to table, use rows to separate the buttons
        table.add(nameInput);
        table.row();
        table.add(startButton).colspan(2).fillX();
        table.row();
        table.add(exitButton).colspan(2).fillX();

        // TODO: check input and pass this to the game, limit to 50 characters
        nameInput.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                MenuScreen.this.playerName = nameInput.getText().strip();
                System.out.println(MenuScreen.this.playerName);
            }
        });

        // Clears the input when you click on the input
        nameInput.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                nameInput.setText("");
            }
        });

        // sets the game screen to GameScreen
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (MenuScreen.this.playerName != null && !MenuScreen.this.playerName.isEmpty()) {
                    MenuScreen.this.dispose();
                    MenuScreen.this.game.setScreen(new GameScreen(game));
                } else {
                    MenuScreen.this.alertDialog.show("Please input a valid name", MenuScreen.this.stage);
                }
            }
        });

        // Exits the game
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        // Adding table to stage
        this.stage.addActor(table);
    }

    @Override
    public void show() {
        // TODO: Start playing some music here
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.4f, 1);
        // No need to update the camera or set projection matrix, stage alredy does it
        this.viewport.apply(true);
        stage.act();
        stage.draw();
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
