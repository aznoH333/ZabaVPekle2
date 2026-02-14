package com.mygdx.game.playState.inventory;

public enum InventoryMenuType {
    NORMAL(false),
    CRAFTING(true);
    
    
    
    public final boolean hasInput;
    
    InventoryMenuType(boolean hasInput) {
        this.hasInput = hasInput;
    }
}
