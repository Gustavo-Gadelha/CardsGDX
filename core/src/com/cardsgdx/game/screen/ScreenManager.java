package com.cardsgdx.game.screen;

import com.badlogic.gdx.Screen;
import com.cardsgdx.game.CardGame;

import java.util.HashMap;

public class ScreenManager {

    public enum Type {
        MENU_SCREEN, GAME_SCREEN, END_SCREEN
    }

    private static final HashMap <Type, Screen> SCREENS = new HashMap<>(3);

    // Private constructor to avoid class be instantiated
    private ScreenManager() {}

    public static void createFrom(CardGame game) {
        SCREENS.put(Type.MENU_SCREEN, new MenuScreen(game));
        SCREENS.put(Type.GAME_SCREEN, new GameScreen(game));
        SCREENS.put(Type.END_SCREEN, new EndScreen(game));
    }

    public static Screen get(Type type) {
        return SCREENS.getOrDefault(type, null);
    }

    public static Screen getNew(Type type, Screen screen) {
        SCREENS.put(type, screen);
        return SCREENS.getOrDefault(type, null);
    }

    public static void disposeAll() {
        SCREENS.forEach((type, screen) -> screen.dispose());
    }
}
