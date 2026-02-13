package com.mygdx.game.entities.components.behaviour;

import com.mygdx.game.Managers;
import com.mygdx.game.entities.ComponentName;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.playState.inventory.InventoryItem;
import com.mygdx.game.utils.types.NumberUtils;

public class ItemDrop extends EntityComponent {
    private float z = 1f;
    private float zM = NumberUtils.randomFloat(1.5f, 2.5f);
    private boolean grounded = false;
    private final float direction = NumberUtils.randomFloat(0, NumberUtils.TWO_PI);
    private final float speed = NumberUtils.randomFloat(0.5f, 1f);
    private final float hoverOffset = NumberUtils.randomFloat(0f, NumberUtils.TWO_PI);
    public final InventoryItem item;
    
    public ItemDrop(InventoryItem item) {
        super.name = ComponentName.INVENTORY_ITEM;
        this.item = item;
    }
    
    @Override
    public void onUpdate(Entity owner) {
        if (!grounded) {
            // gravity
            z += zM;
            zM -= 0.3f;
            if (z < 0f) {
                z = 0;
                grounded = true;
            }
            
            owner.goInDirection(direction, speed);
            
        } else {
            
            // hover animation
            z += (float) Math.sin(Managers.playStateManager.gameTime / NumberUtils.TWO_PI + hoverOffset) * 0.5f;
            
        }
        
        owner.spriteOffsetY = z * 0.5f;
        
        
        
    }
}
