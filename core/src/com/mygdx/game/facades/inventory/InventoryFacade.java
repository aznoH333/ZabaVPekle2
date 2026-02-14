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
import com.mygdx.game.facades.sceen.GUIFacade;
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
            player.addChild(createInventoryEntity(InventoryMenuType.NORMAL));
        }
    }
    
    public static void openInventoryMenu(InventoryMenuType type) {
        Entity player = Managers.playStateManager.playerReference;
        
        if (isInventoryOpen()) {
            return;
        }
        
        player.addChild(createInventoryEntity(type));
    }
    
    private static void closeInventory() {
        getInventory().commitSudoku();
        // TODO : drop items
    }
    
    
    private static Entity createInventoryEntity(InventoryMenuType type) {
        Entity entity = new Entity()
            .setSprite("inventory_0001")
            .setIdentifier(EntityIdentifier.INVENTORY)
            .setDrawingLayer(DrawingLayer.GUI)
            .makeStatic()
            .addComponent(new InventoryGUI(type));
        
        if (type == InventoryMenuType.CRAFTING) {
            entity.addChild(
                GUIFacade.buildButton("craft", 0, -128, (button)->{
                    Managers.playStateManager.inventory.clearInputs();
                    Managers.playStateManager.inventory.addItem(new InventoryItem("inventory_items_0006", "motor", Quality.COMMON, 1, true, InventoryItemType.MOTOR));
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
    
    
    
}
