package com.mygdx.game.entities.components.control;

import com.mygdx.game.Managers;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;

public class DelayedEvent extends EntityComponent {
    private final long activationTime;
    private final Runnable action;
    public DelayedEvent(Runnable action, long delay) {
        this.activationTime = Managers.playStateManager.gameTime + delay;
        this.action = action;
    }
    
    @Override
    public void onDraw(Entity owner){
        if (Managers.playStateManager.gameTime > this.activationTime) {
            owner.commitSudoku();
        }
    }
    
    @Override
    public void onSudoku(Entity owner) {
        action.run();
    }
}
