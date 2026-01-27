package com.mygdx.game.entities.components.control;

import com.mygdx.game.entities.ComponentName;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.facades.world.WorldFacade;
import com.mygdx.game.level.LevelExitDirection;
import com.mygdx.game.playState.ZoneCoordinates;

public class Door extends EntityComponent {

    private final String zoneDestination;
    private final ZoneCoordinates destination;
    private final LevelExitDirection direction;

    public Door(String zoneDestination, ZoneCoordinates destination, LevelExitDirection direction) {
        this.zoneDestination = zoneDestination;
        this.destination = destination;
        this.direction = direction;
    }
    
    @Override
    public void onCollide(Entity owner, Entity other) {

        if (other.hasComponent(ComponentName.PLAYER)) {
            WorldFacade.enterARoomThroughADoor(zoneDestination, destination, direction);
        }
    }
}
