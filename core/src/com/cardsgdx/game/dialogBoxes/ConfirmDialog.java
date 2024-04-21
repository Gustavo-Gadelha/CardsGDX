package com.cardsgdx.game.dialogBoxes;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class ConfirmDialog {
    private static final float WIDTH = 200f;
    private static final float HEIGHT = 150f;

    private final Dialog dialog;
    private final Label textLabel;
    private final TextButton yesButton;
    private final TextButton noButton;
    private boolean response;

    public ConfirmDialog(Skin skin) {
        this.dialog = new Dialog("Confirm option", skin);
        this.dialog.getButtonTable().defaults().growX();
        this.dialog.setModal(true);
        this.dialog.setMovable(false);
        this.dialog.setResizable(false);

        this.textLabel = new Label("Are you sure?", skin);
        this.yesButton = new TextButton("Yes", skin);
        this.noButton = new TextButton("No", skin);

        this.dialog.text(this.textLabel);
        this.dialog.button(this.noButton, false);
        this.dialog.button(this.yesButton, true);

        this.noButton.addListener((event) -> ConfirmDialog.this.response = false);
        this.yesButton.addListener((event) -> ConfirmDialog.this.response = true);

        this.response = false;
    }

    public void setText(CharSequence newText) {
        this.textLabel.setText(newText);
    }

    public void show(Stage stage) {
        this.dialog.show(stage);
        this.dialog.setSize(ConfirmDialog.WIDTH, ConfirmDialog.HEIGHT);
    }

    public void show(String text, Stage stage) {
        this.setText(text);
        this.show(stage);
    }

    public boolean getResponse() {
        return this.response;
    }
}
