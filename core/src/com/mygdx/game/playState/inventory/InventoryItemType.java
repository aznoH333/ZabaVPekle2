package com.mygdx.game.playState.inventory;

public enum InventoryItemType {
    PLATE("inventory_items_0001", "plate", true),
    BATTERY("inventory_items_0002", "battery", true),
    WIRING("inventory_items_0003", "wiring", true),
    PROCESSOR("inventory_items_0004", "processor", true),
    GEAR("inventory_items_0005", "gear", true),
    MOTOR("inventory_items_0006", "motor", true);


    public final String sprite;
    public final String name;
    public final boolean stackable;

    InventoryItemType(String sprite, String name, boolean stackable) {
        this.sprite = sprite;
        this.name = name;
        this.stackable = stackable;
    }
}
