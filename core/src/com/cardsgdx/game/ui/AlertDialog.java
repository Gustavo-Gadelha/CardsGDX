package com.cardsgdx.game.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AlertDialog extends Dialog implements DialogUI {
    private final Label title;
    private final Label message;

    public AlertDialog(Skin skin) {
        super("Placeholder", skin);
        this.configure();

        this.title = this.getTitleLabel();
        this.message = new Label("Placeholder text", skin);

        this.text(this.message);
        this.button("Ok", true);
    }

    @Override
    public void configure() {
        this.getContentTable().pad(10);
        this.getButtonTable().defaults().growX();
        this.setModal(true);
        this.setMovable(true);
        this.setResizable(false);
        this.setSize(DialogUI.WIDTH, DialogUI.HEIGHT);
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
        this.show(stage);
        this.setHeight(DialogUI.HEIGHT);
    }
}
