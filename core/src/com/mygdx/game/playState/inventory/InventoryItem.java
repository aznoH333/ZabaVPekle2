package com.mygdx.game.playState.inventory;

import com.mygdx.game.entities.items.Quality;

public class InventoryItem {
    public String sprite;
    public String name;
    public Quality quality;
    public int quantity;
    
    public InventoryItem(String sprite, String name, Quality quality, int quantity) {
        this.sprite = sprite;
        this.name = name;
        this.quality = quality;
        this.quantity = quantity;
    }
}
