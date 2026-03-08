package com.mygdx.game.entities.components.gui.hudElements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Managers;
import com.mygdx.game.drawing.DrawingCommand;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.drawing.TextDrawingCommand;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.facades.inventory.InventoryFacade;
import com.mygdx.game.playState.inventory.Inventory;
import com.mygdx.game.playState.inventory.InventoryItem;
import com.mygdx.game.playState.inventory.InventoryMenuType;
import com.mygdx.game.playState.inventory.InventorySlotType;
import com.mygdx.game.utils.types.NumberUtils;

public class InventoryGUI extends EntityComponent {
    
    
    private final InventoryMenuType type;
    
    /// Refers to the machine/world object that spawned the inventory. Null if inventory has no origin
    private final Entity originEntityReference;
    
    public InventoryGUI(InventoryMenuType type, Entity originEntityReference) {
        this.type = type;
        this.originEntityReference = originEntityReference;
    }
    
    private static final int SPACE_BETWEEN_SLOTS = 5;
    private static final int SLOT_SIZE = 24;
    private static final float OFFSET_X = (-SLOT_SIZE * 2) + (-1.5f * SPACE_BETWEEN_SLOTS);
    private static final float OFFSET_Y = (-SLOT_SIZE * 2) + (-1.5f * SPACE_BETWEEN_SLOTS) - 45f;
    private static final float QUANTITY_OFFSET_X = 6f;
    private static final float QUANTITY_OFFSET_Y = -2f;
    
    private static final float INPUT_ROW_OFFSET_Y = 157f;
    private InventoryItem hoveredItem = null;
    private InventorySlotType hoveredSlotType;
    private int hoveredIndex = -1;
    
    
    @Override
    public void onDraw(Entity owner) {
        
        Inventory inventory = Managers.playStateManager.inventory;
        
        hoveredItem = null;
        hoveredIndex = -1;
        hoveredSlotType = InventorySlotType.NONE;

        drawInventorySlots(inventory);

        drawCraftingInputs(owner, inventory);

        drawHintText();
    }


    private static final float HINT_TEXT_OFFSET_X = 0f;
    private static final float HINT_TEXT_OFFSET_Y = -64f;

    private void drawHintText() {
        String text;

        if (hoveredItem != null) {
            text = hoveredItem.name + " x" + hoveredItem.quantity;
        } else if (type.hintText != null) {
            text = type.hintText;
        } else {
            return;
        }

        Managers.drawingManager.drawText(
                new TextDrawingCommand(
                        text,
                        owner.x + HINT_TEXT_OFFSET_X,
                        owner.y + HINT_TEXT_OFFSET_Y
                )
        );
    }

    private void drawCraftingInputs(Entity owner, Inventory inventory) {
        if (!type.hasInput) {
            return;
        }

        // draw crafting text
        Managers.drawingManager.drawSpriteStatic(
                new DrawingCommand(
                        "inventory_0005",
                        owner.x,
                        owner.y
                ),
                DrawingLayer.GUI
        );


        for (int i = 0; i < Inventory.ITEMS_PER_INVENTORY_ROW; i++) {
            handleInventorySlot(
                i * (SLOT_SIZE + SPACE_BETWEEN_SLOTS),
                owner.y + INPUT_ROW_OFFSET_Y,
                inventory.getInputItem(i),
                InventorySlotType.INPUT,
                i
            );
        }

    }

    private void drawInventorySlots(Inventory inventory) {
        // draw equipment text
        Managers.drawingManager.drawSpriteStatic(
                new DrawingCommand(
                        "inventory_0002",
                        owner.x,
                        owner.y
                ),
                DrawingLayer.GUI
        );

        for (int i = 0; i < Inventory.INVENTORY_ROWS; i++) {
            for (int j = 0; j < Inventory.ITEMS_PER_INVENTORY_ROW; j++) {

                float x = j * (SLOT_SIZE + SPACE_BETWEEN_SLOTS);
                float y = (Inventory.INVENTORY_ROWS + 2 - i - 1) * (SLOT_SIZE + SPACE_BETWEEN_SLOTS);

                int slotIndex = ((i*Inventory.ITEMS_PER_INVENTORY_ROW))+j;

                handleInventorySlot(
                    x,
                    y,
                    inventory.getItem(slotIndex),
                    InventorySlotType.NORMAL,
                    slotIndex
                    );
            }
        }
    }


    @Override
    public void onUpdate(Entity owner) {
        if (!type.hasInput || hoveredSlotType == InventorySlotType.NONE) {
            return;
        }
        
        Inventory inventory = Managers.playStateManager.inventory;
        
        
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            if (hoveredSlotType == InventorySlotType.NORMAL && inventory.canAddInput()) {
                inventory.removeItem(hoveredIndex, 1);
                
                
                InventoryItem itemToAdd = hoveredItem.copy();
                itemToAdd.quantity = 1;
                inventory.addInput(itemToAdd);
            }else if (hoveredSlotType == InventorySlotType.INPUT) {
                InventoryItem item = inventory.getInputItem(hoveredIndex);
                
                inventory.removeInput(hoveredIndex);
                
                InventoryFacade.giveOrDropItem(item);
                
            }
        }
        
        
    }
    
    @Override
    public void onSudoku(Entity owner) {
        if (type.hasInput && originEntityReference != null) {
            // drop items
            Inventory inventory = Managers.playStateManager.inventory;
            
            for (InventoryItem item : inventory.inputSlots) {
                InventoryFacade.giveOrDropItem(item);
            }
            
            inventory.clearInputs();
            
        }
    }
    
    
    private void handleInventorySlot(float x, float y, InventoryItem item, InventorySlotType slotType, int slotIndex) {
        String slotSprite = "inventory_slot_0001";
        
        if (item == null) {
            slotSprite = "inventory_slot_0004";
        }
        
        // hovering
        Vector2 mousePos = Managers.drawingManager.getScreenMousePosition();
        boolean hovered = NumberUtils.checkCollisions(
            x + OFFSET_X, y + OFFSET_Y, SLOT_SIZE, SLOT_SIZE, mousePos.x, mousePos.y, 1f, 1f
        );
        
        
        if (hovered && item != null) {
            slotSprite = "inventory_slot_0002";
            hoveredIndex = slotIndex;
            hoveredSlotType = slotType;
            hoveredItem = item;
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
        if (item.stackable && item.quantity > 1) {
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
