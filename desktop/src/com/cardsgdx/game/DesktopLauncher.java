package com.cardsgdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.cardsgdx.game.CardGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("CardsGDX");
        config.setWindowedMode(1280, 800);
        config.setWindowIcon("icon/icon32x32.png");
        new Lwjgl3Application(new CardGame(), config);
    }
}
