package com.mygdx.game.gameStates.implementations;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityManager;
import com.mygdx.game.entities.components.gui.Button;
import com.mygdx.game.entities.components.gui.Text;
import com.mygdx.game.gameStates.GameState;
import com.mygdx.game.gameStates.GameStateManager;

public class MainMenu extends GameState {
    private static final EntityManager entityManager = EntityManager.getInstance();
    private static final GameStateManager gameStateManager = GameStateManager.getInstance();


    public MainMenu() {
        super("main menu");
    }

    @Override
    public void initializeState() {
        entityManager.addEntity(
                new Entity()
                        .setSprite("player_1")
                        .makeStatic()
                        .addComponent(
                                new Text("Play game")
                        ).addComponent(new Button(()->{
                            gameStateManager.switchState("game");
                        }))
        );
    }

    @Override
    public void cleanUpState() {

    }
}
