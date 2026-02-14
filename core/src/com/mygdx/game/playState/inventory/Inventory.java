package com.mygdx.game.playState.inventory;

import java.util.ArrayList;

public class Inventory {
    
    
    
    private ArrayList<InventoryItem> equippedItems;
    private ArrayList<InventoryItem> items;
    private ArrayList<InventoryItem> inputSlots;
    public final static int ITEMS_PER_INVENTORY_ROW = 5;
    public final static int MAX_EQUIPMENT = 5;
    public final static int INVENTORY_ROWS = 4;
    public final static int MAX_INVENTORY_CAPACITY = INVENTORY_ROWS * ITEMS_PER_INVENTORY_ROW;
    
    public Inventory(){
        this.items = new ArrayList<>();
        this.equippedItems = new ArrayList<>();
        this.inputSlots = new ArrayList<>();
    }
    
    public void addItem(InventoryItem item) {
        // check if item already present
        if (item.stackable) {
            for (InventoryItem existingItem : items) {
                if (item.canStackWith(existingItem)) {
                    existingItem.quantity += item.quantity;
                    return;
                }
            }
        }
        
        if (items.size() >= MAX_INVENTORY_CAPACITY) {
            return;
        }
        
        items.add(item);
    }
    
    public boolean canStoreItem(InventoryItem item) {
        if (items.size() < MAX_INVENTORY_CAPACITY) {
            return true;
        }
        
        for (InventoryItem existingItem : items) {
            if (item.canStackWith(existingItem)) {
                return true;
            }
        }
        
        return false;
    }
    
    public void removeItem(int itemIndex, int quantity) {
        InventoryItem item = items.get(itemIndex);
        
        if (quantity < item.quantity) {
            item.quantity -= quantity;
        } else {
            items.remove(itemIndex);
        }
    }
    
    public void equipItem(InventoryItem item) {
        if (equippedItems.size() >= MAX_EQUIPMENT) {
            return;
        }
        
        equippedItems.add(item);
    }
    
    public void unequipItem(int equipedIndex) {
        equippedItems.remove(equipedIndex);
    }
    
    public InventoryItem getItem(int itemIndex) {
        if (itemIndex >= items.size()) {
            return null;
        }
        
        return items.get(itemIndex);
    }
    
    public boolean canAddInput() {
        return this.inputSlots.size() < ITEMS_PER_INVENTORY_ROW;
    }
    
    public void addInput(InventoryItem item) {
        this.inputSlots.add(item);
    }
    
    public void removeInput(int index) {
        this.inputSlots.remove(index);
    }
    
    public InventoryItem getInputItem(int index) {
        if (index >= inputSlots.size()) {
            return null;
        }
        
        return inputSlots.get(index);
    }
    
    public void clearInputs() {
        this.inputSlots.clear();
    }
}
