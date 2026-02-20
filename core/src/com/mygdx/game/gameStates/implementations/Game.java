package com.mygdx.game.gameStates.implementations;

import com.mygdx.game.Managers;
import com.mygdx.game.facades.world.WorldInteractableFacade;
import com.mygdx.game.gameStates.GameState;

public class Game extends GameState {


    public Game() {
        super("game");
        super.drawWorld = true;
    }

    @Override
    public void initializeState() {

        // Managers.entityManager.addEntity(PlayerFacade.createNewPlayer(0f, 0f));

        
        
        Managers.entityManager.addEntity(
            WorldInteractableFacade.createItemBox(32f, 32f)
        );
        
        Managers.entityManager.addEntity(
            WorldInteractableFacade.createCraftingStation(-32f, -32f)
        );
        
        
        
    }

    @Override
    public void cleanUpState() {

    }
    
    @Override
    public void update() {
        Managers.playStateManager.gameTime++;
    }
}
