package com.mygdx.game.entities.components.control;

import com.mygdx.game.entities.ComponentName;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.components.gui.EntityRunnable;

public class Interactable extends EntityComponent {
    private final EntityRunnable interactAction;
    public Interactable(EntityRunnable interactAction) {
        this.interactAction = interactAction;
        super.name = ComponentName.INTERACTABLE;
    }
    
    public void interact() {
        this.interactAction.run(this.owner);
    }
}
