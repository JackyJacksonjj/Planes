package com.gdx.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

/**
 * Main purpose is to hold {@link Game} reference
 */
public class PurpleGameScreen implements Screen {

    private Game game;

    public PurpleGameScreen(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
