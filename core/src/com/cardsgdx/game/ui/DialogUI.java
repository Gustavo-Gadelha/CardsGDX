package com.cardsgdx.game.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;

public interface DialogUI {
    float WIDTH = 300f;
    float HEIGHT = 150f;

    void configure();

    void setTitle(CharSequence title);

    void setMessage(CharSequence message);

    void show(Stage stage, CharSequence title, CharSequence message);
}
