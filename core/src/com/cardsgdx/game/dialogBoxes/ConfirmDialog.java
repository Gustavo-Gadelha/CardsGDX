package com.cardsgdx.game.dialogBoxes;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class ConfirmDialog implements StandardDialog {
    private final Dialog dialog;
    private final Label title;
    private final Label message;
    private final TextButton yesButton;
    private final TextButton noButton;
    private boolean response;

    public ConfirmDialog(Skin skin) {
        this.dialog = new Dialog("ConfirmDialog", skin) {
            @Override
            protected void result(Object object) {
                ConfirmDialog.this.response = (boolean) object;
            }
        };

        this.dialog.getContentTable().pad(10);
        this.dialog.getButtonTable().defaults().growX();
        this.dialog.setModal(true);
        this.dialog.setMovable(true);
        this.dialog.setResizable(false);

        this.title = this.dialog.getTitleLabel();
        this.message = new Label("Placeholder text", skin);
        this.yesButton = new TextButton("Yes", skin);
        this.noButton = new TextButton("No", skin);

        this.dialog.text(this.message);
        this.dialog.button(this.noButton, false);
        this.dialog.button(this.yesButton, true);

        this.noButton.addListener((event) -> ConfirmDialog.this.response = false);
        this.yesButton.addListener((event) -> ConfirmDialog.this.response = true);

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
        if (!this.message.equals(message)) this.setMessage(message);
        this.dialog.show(stage);
        this.dialog.setHeight(StandardDialog.HEIGHT);
    }

    @Override
    public boolean getResponse() {
        // TODO: the response of this dialog is useless, it doesn't wait for the users input, making it unpredictable
        return this.response;
    }
}
