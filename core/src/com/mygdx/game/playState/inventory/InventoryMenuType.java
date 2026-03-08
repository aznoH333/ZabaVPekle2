package com.mygdx.game.playState.inventory;

public enum InventoryMenuType {
    NORMAL(false, null),
    CRAFTING(true, "Placeholder");
    
    
    
    public final boolean hasInput;
    public final String hintText;
    
    InventoryMenuType(boolean hasInput, String hintText) {
        this.hasInput = hasInput;
        this.hintText = hintText;
    }
}
