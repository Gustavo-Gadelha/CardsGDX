package com.cardsgdx.game.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Disposable;
import com.cardsgdx.game.CardGame;

import java.util.HashMap;

public class ScreenManager implements Disposable {

    public enum ScreenType {
        MENU_SCREEN, GAME_SCREEN, END_SCREEN
    }

    private final HashMap<ScreenType, Screen> screens;

    public ScreenManager(CardGame game) {
        this.screens = new HashMap<>(ScreenType.values().length);

        this.screens.put(ScreenType.MENU_SCREEN, new MenuScreen(game));
        this.screens.put(ScreenType.GAME_SCREEN, new GameScreen(game));
        this.screens.put(ScreenType.END_SCREEN, new EndScreen(game));
    }

    public void put(ScreenType screenType, Screen screen) {
        this.screens.put(screenType, screen);
    }

    public Screen get(ScreenType screenType) {
        return this.screens.getOrDefault(screenType, null);
    }

    @Override
    public void dispose() {
        this.screens.forEach((type, screen) -> screen.dispose());
    }
}
