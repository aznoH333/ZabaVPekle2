package com.mygdx.game.facades.inventory;

import com.mygdx.game.Managers;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityIdentifier;
import com.mygdx.game.entities.components.gui.hudElements.InventoryGUI;
import com.mygdx.game.entities.items.Quality;
import com.mygdx.game.facades.sceen.GUIFacade;
import com.mygdx.game.facades.entities.WorldInteractableFacade;
import com.mygdx.game.playState.PlayStateManager;
import com.mygdx.game.playState.inventory.Inventory;
import com.mygdx.game.playState.inventory.InventoryItem;
import com.mygdx.game.playState.inventory.InventoryItemType;
import com.mygdx.game.playState.inventory.InventoryMenuType;

import java.util.Optional;

public class InventoryFacade {
    public static void toggleInventory() {
        Entity player = Managers.playStateManager.playerReference;
        
        
        if (isInventoryOpen()) {
            closeInventory();
        } else {
            player.addChild(createInventoryEntity(InventoryMenuType.NORMAL, null));
        }
    }
    
    public static void openInventoryMenu(InventoryMenuType type, Entity originEntity) {
        Entity player = Managers.playStateManager.playerReference;
        
        if (isInventoryOpen()) {
            return;
        }
        
        player.addChild(createInventoryEntity(type, originEntity));
    }
    
    private static void closeInventory() {
        getInventory().commitSudoku();
    }
    
    
    private static Entity createInventoryEntity(InventoryMenuType type, Entity originEntity) {
        Entity entity = new Entity()
            .setSprite("inventory_0001")
            .setIdentifier(EntityIdentifier.INVENTORY)
            .setDrawingLayer(DrawingLayer.GUI)
            .makeStatic()
            .addComponent(new InventoryGUI(type, originEntity));
        
        if (type.canCraft) {
            entity.addChild(
                GUIFacade.buildButton("craft", 0, -128, (button)->{
                    // check that all requirements are met
                    if (type.requirements.size() != Managers.playStateManager.inventory.inputSlots.size()) {
                        return;
                    }

                    Managers.playStateManager.inventory.clearInputs();
                    type.craftAction.run(Managers.playStateManager.playerReference);
                    closeInventory();
                })
            );
        }
        
        return entity;
    }
    
    public static boolean isInventoryOpen() {
        Entity player = Managers.playStateManager.playerReference;
        
        return player.children.stream().anyMatch((it)->it.identifier == EntityIdentifier.INVENTORY);
    }
    
    private static Entity getInventory() {
        Entity player = Managers.playStateManager.playerReference;
    
        Optional<Entity> inventory = player.children.stream().filter((it)->it.identifier == EntityIdentifier.INVENTORY).findFirst();
        
        return inventory.orElse(null);
    }
    
    public static void giveOrDropItem(InventoryItem item) {
        Inventory inventory = Managers.playStateManager.inventory;
        
        if (inventory.canStoreItem(item)) {
            inventory.addItem(item);
        }else {
            WorldInteractableFacade.createItemDrop(item, Managers.playStateManager.playerReference.x, Managers.playStateManager.playerReference.y);
        }
    }
    
    
}
