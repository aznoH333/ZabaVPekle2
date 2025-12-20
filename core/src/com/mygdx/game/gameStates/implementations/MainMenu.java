package com.mygdx.game.gameStates.implementations;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.SoundManager;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityManager;
import com.mygdx.game.entities.components.gui.Button;
import com.mygdx.game.entities.components.gui.Hover;
import com.mygdx.game.entities.components.gui.Text;
import com.mygdx.game.entities.factories.GUIFactory;
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


        entityManager.addEntity(GUIFactory.createButton(
                "Play game",
                0f,
                25f,
                owner -> gameStateManager.switchState("game")
        ));

        entityManager.addEntity(GUIFactory.createButton(
                "Quit game",
                0f,
                -25f,
                owner -> Gdx.app.exit()
        ));

    }

    @Override
    public void cleanUpState() {

    }
}
