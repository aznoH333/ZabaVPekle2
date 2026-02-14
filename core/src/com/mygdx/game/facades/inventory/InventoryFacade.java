package com.mygdx.game.facades.inventory;

import com.mygdx.game.Managers;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.entities.ComponentName;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityIdentifier;
import com.mygdx.game.entities.EntityTeam;
import com.mygdx.game.entities.components.behaviour.ItemDrop;
import com.mygdx.game.entities.components.control.Interactable;
import com.mygdx.game.entities.components.gui.hudElements.InventoryGUI;
import com.mygdx.game.entities.components.visual.particles.FadeParticle;
import com.mygdx.game.entities.fields.FieldName;
import com.mygdx.game.entities.items.Quality;
import com.mygdx.game.playState.inventory.InventoryItem;
import com.mygdx.game.playState.inventory.InventoryItemType;

import java.util.Optional;

public class InventoryFacade {
    public static void toggleInventory() {
        Entity player = Managers.playStateManager.playerReference;
        
        
        if (isInventoryOpen()) {
            getInventory().commitSudoku();
        } else {
            player.addChild(createInventoryEntity());
        }
    }
    
    private static Entity createInventoryEntity() {
        return new Entity()
            .setSprite("inventory_0001")
            .setIdentifier(EntityIdentifier.INVENTORY)
            .setDrawingLayer(DrawingLayer.GUI)
            .makeStatic()
            .addComponent(new InventoryGUI())
            ;
    }
    
    private static boolean isInventoryOpen() {
        Entity player = Managers.playStateManager.playerReference;
        
        return player.children.stream().anyMatch((it)->it.identifier == EntityIdentifier.INVENTORY);
    }
    
    private static Entity getInventory() {
        Entity player = Managers.playStateManager.playerReference;
    
        Optional<Entity> inventory = player.children.stream().filter((it)->it.identifier == EntityIdentifier.INVENTORY).findFirst();
        
        return inventory.orElse(null);
    }
    
    
    
}
