package com.mygdx.game.entities.components.control;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.world.WorldManager;

public class Door extends EntityComponent {
    private static final WorldManager world = WorldManager.getInstance();


    @Override
    public void onCollide(Entity owner, Entity other) {

        if (other.hasComponent("soul")) {
            world.moveToNewLevel(other);
        }
    }
}
