package com.cardsgdx.game.dialogBoxes;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class AlertDialog implements StandardDialog {
    private final Dialog dialog;
    private final Label title;
    private final Label message;
    private final TextButton confirmButton;
    private boolean response;

    public AlertDialog(Skin skin) {
        this.dialog = new Dialog("AlertDialog", skin) {
            @Override
            protected void result(Object object) {
                AlertDialog.this.response = (boolean) object;
            }
        };

        this.dialog.getContentTable().pad(10);
        this.dialog.getButtonTable().defaults().growX();
        this.dialog.setModal(true);
        this.dialog.setMovable(true);
        this.dialog.setResizable(false);

        this.title = this.dialog.getTitleLabel();
        this.message = new Label("Placeholder text", skin);
        this.confirmButton = new TextButton("Ok", skin);

        this.dialog.text(this.message);
        this.dialog.button(this.confirmButton, true);

        this.response = false;
    }

    @Override
    public void setTitle(CharSequence title) {
        this.title.setText(title);
    }

    @Override
    public void setMessage(CharSequence message) {
        this.message.setText(message);
    }

    @Override
    public void show(Stage stage, CharSequence title, CharSequence message) {
        this.setTitle(title);
        this.setMessage(message);
        this.dialog.show(stage);
        this.dialog.setHeight(StandardDialog.HEIGHT);
    }

    @Override
    public boolean getResponse() {
        // TODO: the response of this dialog is useless, it doesn't wait for the users input, making it unpredictable
        return this.response;
    }
}
