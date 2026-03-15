package com.mygdx.game.gameStates.implementations;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.Managers;
import com.mygdx.game.drawing.FontSize;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.components.visual.AttachedLight;
import com.mygdx.game.facades.sceen.GUIFacade;
import com.mygdx.game.facades.world.WorldFacade;
import com.mygdx.game.gameStates.GameState;

public class MainMenu extends GameState {


    public MainMenu() {
        super("main menu");
    }

    @Override
    public void initializeState() {


        Managers.entityManager.addEntity(
                GUIFacade.buildFloatingText(
                        "Robot rougelike game",
                        0f,
                        96f,
                        FontSize.DISPLAY
                )
        );

        GUIFacade.createButton(
            "Play game",
            0f,
            25f,
            owner -> {
                WorldFacade.initializeNewGame();
                Managers.gameStateManager.switchState("game");
            }
        );

        GUIFacade.createButton(
            "Quit game",
            0f,
            -25f,
            owner -> Gdx.app.exit()
        );


        // hint text
        Managers.entityManager.addEntity(
                GUIFacade.buildFloatingText(
                        "Controls\nWASD: move\nE: interact\nTab: Inventory",
                        0f,
                        -96f,
                        FontSize.MEDIUM
                )
        );

    }

    @Override
    public void cleanUpState() {

    }
}
