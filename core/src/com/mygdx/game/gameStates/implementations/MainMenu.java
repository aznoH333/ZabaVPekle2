package com.mygdx.game.gameStates.implementations;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.SoundManager;
import com.mygdx.game.entities.EntityManager;
import com.mygdx.game.entities.facades.GUIFacade;
import com.mygdx.game.gameStates.GameState;
import com.mygdx.game.gameStates.GameStateManager;

public class MainMenu extends GameState {
    private static final EntityManager entityManager = EntityManager.getInstance();
    private static final GameStateManager gameStateManager = GameStateManager.getInstance();
    private static final SoundManager soundManager = SoundManager.getInstance();


    public MainMenu() {
        super("main menu");
    }

    @Override
    public void initializeState() {


        GUIFacade.createButton(
                "Play game",
                0f,
                25f,
                owner -> gameStateManager.switchState("game")
        );

        GUIFacade.createButton(
                "Quit game",
                0f,
                -25f,
                owner -> Gdx.app.exit()
        );

    }

    @Override
    public void cleanUpState() {

    }
}
