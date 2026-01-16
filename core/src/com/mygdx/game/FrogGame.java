package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.gameStates.implementations.Game;
import com.mygdx.game.gameStates.implementations.MainMenu;

public class FrogGame extends ApplicationAdapter {


    @Override
    public void create() {

        Managers.drawingManager.loadSpritesInDirectory("assets/sprites");


        // Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayModes()[0]);


        // game states
        Managers.gameStateManager.addGameState(new Game());
        Managers.gameStateManager.addGameState(new MainMenu());
        Managers.gameStateManager.switchState("main menu");

    }

    @Override
    public void render() {
        Managers.gameStateManager.update();
    }

    @Override
    public void dispose() {
        Managers.drawingManager.dispose();
        Managers.soundManager.dispose();
    }

    @Override
    public void resize(int width, int height) {
        Managers.drawingManager.resizedWindow(width, height);
    }

}
