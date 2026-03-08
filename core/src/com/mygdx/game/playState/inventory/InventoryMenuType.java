package com.mygdx.game.playState.inventory;

import com.mygdx.game.entities.components.gui.EntityRunnable;
import com.mygdx.game.entities.fields.FieldName;

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

    TEMP(
            true,
            true,
            "Placeholder",
            null,
            null
    ),



    REPAIR_STATION(
            true,
            true,
            "repair station",
            Arrays.asList(InventoryItemType.PLATE, InventoryItemType.PLATE, InventoryItemType.PLATE, InventoryItemType.GEAR, InventoryItemType.WIRING),
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
