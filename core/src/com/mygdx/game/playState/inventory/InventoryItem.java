package com.mygdx.game.playState.inventory;

import com.mygdx.game.entities.items.Quality;

public class InventoryItem {
    public String sprite;
    public String name;
    public Quality quality;
    public int quantity;
    public boolean stackable;
    public InventoryItemType type;
    
    public InventoryItem(InventoryItemType type, Quality quality, int quantity) {
        this.sprite = type.sprite;
        this.name = type.name;
        this.quality = quality;
        this.quantity = quantity;
        this.stackable = type.stackable;// TODO
        this.type = type;
    }
    
    
    
    public boolean canStackWith(InventoryItem other) {
        return this.quality == other.quality && this.type == other.type && this.stackable && other.stackable;
    }
    
    public InventoryItem copy() {
        return new InventoryItem(
            type,
            quality,
            quantity
        );
    }
}
