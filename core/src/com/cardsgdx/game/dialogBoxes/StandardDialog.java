package com.cardsgdx.game.dialogBoxes;

import com.badlogic.gdx.scenes.scene2d.Stage;

public interface StandardDialog {
    float WIDTH = 300f;
    float HEIGHT = 120f;

    void setTitle(CharSequence title);

    void setMessage(CharSequence message);

    void show(Stage stage, CharSequence title, CharSequence message);

    boolean getResponse();
}
