package com.mygdx.game.entities.components.control;

import com.mygdx.game.Managers;
import com.mygdx.game.entities.ComponentName;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;

public class Door extends EntityComponent {


    @Override
    public void onCollide(Entity owner, Entity other) {

        if (other.hasComponent(ComponentName.PLAYER)) {
            Managers.levelManager.moveToNewLevel(other);
        }
    }
}
