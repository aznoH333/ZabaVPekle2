package com.mygdx.game.playState.inventory;

import com.mygdx.game.entities.items.Quality;

public class InventoryItem {
    public String sprite;
    public String name;
    public Quality quality;
    public int quantity;
    public boolean stackable;
    public InventoryItemType type;
    
    public InventoryItem(String sprite, String name, Quality quality, int quantity, boolean stackable, InventoryItemType type) {
        this.sprite = sprite;
        this.name = name;
        this.quality = quality;
        this.quantity = quantity;
        this.stackable = stackable;// TODO
        this.type = type;
    }
    
    public boolean canStackWith(InventoryItem other) {
        return this.quality == other.quality && this.type == other.type && this.stackable && other.stackable;
    }
}
