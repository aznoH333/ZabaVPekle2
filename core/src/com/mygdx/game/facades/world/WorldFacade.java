package com.mygdx.game.facades.world;

import com.mygdx.game.Managers;
import com.mygdx.game.facades.entities.PlayerFacade;
import com.mygdx.game.playState.ZoneCoordinates;

public class WorldFacade {
    
    /**
     * Teleports the player to a specific room in a specific zone with specific coordinates
     * @param zoneName - name of the place
     * @param coordinates - coordinates of the room to teleport to
     * @param x - in room x
     * @param y - in room y
     */
    public static void teleportPlayerToZone(String zoneName, ZoneCoordinates coordinates, float x, float y) {
        Managers.playStateManager.goToZone(zoneName);
        Managers.playStateManager.setPlayerZoneCoordinates(coordinates.x, coordinates.y);
        Managers.playStateManager.playerReference.setX(x).setY(y);
    }
    
    public static void initializeNewGame() {
        Managers.playStateManager.playerReference = PlayerFacade.createNewPlayer(0f, 0f);
        teleportPlayerToZone("start", new ZoneCoordinates(0, 0), 0f, 0f);
    }
    
}
