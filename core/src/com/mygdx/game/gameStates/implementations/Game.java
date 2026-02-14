package com.mygdx.game.gameStates.implementations;

import com.mygdx.game.Managers;
import com.mygdx.game.facades.augmentBox.AugmentBoxFacade;
import com.mygdx.game.entities.items.Quality;
import com.mygdx.game.facades.inventory.InventoryFacade;
import com.mygdx.game.gameStates.GameState;
import com.mygdx.game.playState.inventory.InventoryItem;
import com.mygdx.game.playState.inventory.InventoryItemType;

public class Game extends GameState {


    public Game() {
        super("game");
        super.drawWorld = true;
    }

    @Override
    public void initializeState() {

        // Managers.entityManager.addEntity(PlayerFacade.createNewPlayer(0f, 0f));

        AugmentBoxFacade.createNewBox(0f, 64f, Quality.POOR);
        
        
        Managers.entityManager.addEntity(
            InventoryFacade.createItemBox(32f, 32f)
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
