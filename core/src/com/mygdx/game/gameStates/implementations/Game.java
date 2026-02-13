package com.mygdx.game.gameStates.implementations;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Managers;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityTeam;
import com.mygdx.game.entities.components.behaviour.ItemDrop;
import com.mygdx.game.entities.components.behaviour.PlayerBehaviour;
import com.mygdx.game.entities.components.behaviour.gun.Gun;
import com.mygdx.game.entities.components.visual.AnimatedLegsWithHat;
import com.mygdx.game.entities.components.visual.AttachedLight;
import com.mygdx.game.entities.components.visual.EyeCursor;
import com.mygdx.game.entities.components.visual.LegsWithHatType;
import com.mygdx.game.facades.augmentBox.AugmentBoxFacade;
import com.mygdx.game.entities.fields.FieldName;
import com.mygdx.game.entities.items.Quality;
import com.mygdx.game.facades.entities.PlayerFacade;
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
        // Managers.drawingManager.lightingShaderHandler.setAmbientLight(new Color(0.25f, 0.25f, 0.25f, 1f));
        Managers.playStateManager.inventory.addItem(
            new InventoryItem("inventory_items_0001", "plate", Quality.COMMON, 3, true, InventoryItemType.PLATE)
        );
        
        
        for (int i = 0; i < 6; i++) {
            Managers.entityManager.addEntity(
                InventoryFacade.createdItemDrop(
                    new InventoryItem("inventory_items_0005", "gear", Quality.COMMON, 5, true, InventoryItemType.GEAR),
                    32f,
                    32f
                )
            );
        }
        
    }

    @Override
    public void cleanUpState() {

    }
    
    @Override
    public void update() {
        Managers.playStateManager.gameTime++;
    }
}
