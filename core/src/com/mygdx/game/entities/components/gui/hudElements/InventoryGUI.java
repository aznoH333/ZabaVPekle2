package com.mygdx.game.entities.components.gui.hudElements;

import com.mygdx.game.Managers;
import com.mygdx.game.drawing.DrawingCommand;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.drawing.TextDrawingCommand;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.playState.inventory.Inventory;
import com.mygdx.game.playState.inventory.InventoryItem;

public class InventoryGUI extends EntityComponent {
    
    
    public InventoryGUI() {
    
    }
    
    private static final int SPACE_BETWEEN_SLOTS = 5;
    private static final int SLOT_SIZE = 24;
    private static final float OFFSET_X = (-SLOT_SIZE * 2) + (-1.5f * SPACE_BETWEEN_SLOTS);
    private static final float OFFSET_Y = (-SLOT_SIZE * 2) + (-1.5f * SPACE_BETWEEN_SLOTS) - 12f;
    private static final float QUANTITY_OFFSET_X = 6f;
    private static final float QUANTITY_OFFSET_Y = -2f;
    
    @Override
    public void onDraw(Entity owner) {
        
        Inventory inventory = Managers.playStateManager.inventory;
        
        for (int i = 0; i < Inventory.INVENTORY_ROWS; i++) {
            for (int j = 0; j < Inventory.ITEMS_PER_INVENTORY_ROW; j++) {
                drawInventorySlot(
                    j * (SLOT_SIZE + SPACE_BETWEEN_SLOTS),
                    (Inventory.INVENTORY_ROWS - i - 1) * (SLOT_SIZE + SPACE_BETWEEN_SLOTS),
                    inventory.getItem((i*Inventory.ITEMS_PER_INVENTORY_ROW)+j));
            }
        }
    }
    
    private void drawInventorySlot(float x, float y, InventoryItem item) {
        
        String slotSprite = "inventory_slot_0001";
        
        if (item == null) {
            slotSprite = "inventory_slot_0004";
        }
        
        
        Managers.drawingManager.drawSpriteStatic(
            new DrawingCommand(
                slotSprite,
                x + OFFSET_X,
                y + OFFSET_Y
            ),
            DrawingLayer.GUI
        );
        
        
        if (item == null) {
            return;
        }
        
        // item sprite
        Managers.drawingManager.drawSpriteStatic(
            new DrawingCommand(
                item.sprite,
                x + OFFSET_X,
                y + OFFSET_Y
            ),
            DrawingLayer.GUI
        );
        
        // item number
        if (item.stackable) {
            Managers.drawingManager.drawText(
                new TextDrawingCommand(
                    item.quantity + "",
                    x + OFFSET_X + QUANTITY_OFFSET_X,
                    y + OFFSET_Y + QUANTITY_OFFSET_Y // TODO : font sizes
                )
            );
        }
    }
    
}
