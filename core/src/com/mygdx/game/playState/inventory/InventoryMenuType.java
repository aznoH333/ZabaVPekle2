package com.mygdx.game.playState.inventory;

import com.mygdx.game.Managers;
import com.mygdx.game.entities.components.gui.EntityRunnable;
import com.mygdx.game.entities.fields.FieldName;
import com.mygdx.game.entities.items.Quality;
import com.mygdx.game.facades.augmentBox.AugmentBoxFacade;
import com.mygdx.game.facades.inventory.InventoryFacade;

import java.util.Arrays;
import java.util.List;

public enum InventoryMenuType {
    NORMAL(
            false,
            false,
            null,
            null,
            null
    ),
    FORGING_STATION(
            true,
            true,
            "weapon station",
            Arrays.asList(InventoryItemType.PLATE, InventoryItemType.PLATE, InventoryItemType.PLATE, InventoryItemType.WIRING, InventoryItemType.PROCESSOR),
            (entity)->{
                AugmentBoxFacade.openNewBox(Managers.playStateManager.playerReference, Quality.REFINED);
            }
    ),


    PROCESSOR_MANUFACTURING(
            true,
            true,
            "processor manufacturer",
            Arrays.asList(InventoryItemType.PLATE, InventoryItemType.WIRING, InventoryItemType.WIRING, InventoryItemType.WIRING),
            (entity)->{
                InventoryFacade.giveOrDropItem(
                        new InventoryItem(InventoryItemType.PROCESSOR, Quality.POOR, 1)
                );
            }
    ),


    REPAIR_STATION(
            true,
            true,
            "repair station",
            Arrays.asList(InventoryItemType.PLATE, InventoryItemType.PLATE, InventoryItemType.WIRING, InventoryItemType.PROCESSOR, InventoryItemType.PROCESSOR),
            (entity)->{
                entity.setNumericStat(FieldName.Health, entity.getNumericStat(FieldName.MaxHealth));
            }
    );


    
    
    
    public final boolean hasInput;
    public final boolean canCraft;
    public final String hintText;
    public final List<InventoryItemType> requirements;
    public final EntityRunnable craftAction;
    
    InventoryMenuType(
            boolean hasInput,
            boolean canCraft,
            String hintText,
            List<InventoryItemType> requirements,
            EntityRunnable craftAction
    ) {
        this.hasInput = hasInput;
        this.hintText = hintText;
        this.canCraft = canCraft;
        this.requirements = requirements;
        this.craftAction = craftAction;
    }
}
