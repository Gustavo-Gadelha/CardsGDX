package com.cardsgdx.game.dialogBoxes;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class AlertDialog {
    private static final float WIDTH = 300f;
    private static final float HEIGHT = 150f;

    private final Dialog dialog;
    private final Label textLabel;
    private final TextButton confirmButton;

    public AlertDialog(Skin skin) {
        this.dialog = new Dialog("Alert", skin);
        this.dialog.getButtonTable().defaults().growX();
        this.dialog.setModal(true);
        this.dialog.setMovable(true);
        this.dialog.setResizable(false);

        this.textLabel = new Label("Click the ok button to continue", skin);
        this.confirmButton = new TextButton("Ok", skin);

        this.dialog.text(this.textLabel);
        this.dialog.button(this.confirmButton, true);
    }

    public void setText(CharSequence newText) {
        this.textLabel.setText(newText);
    }

    public void show(Stage stage) {
        this.dialog.show(stage);
        this.dialog.setSize(AlertDialog.WIDTH, AlertDialog.HEIGHT);
    }

    public void show(String text, Stage stage) {
        this.setText(text);
        this.show(stage);
    }
}
