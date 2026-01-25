package com.mygdx.game.facades.world;

import com.mygdx.game.Managers;
import com.mygdx.game.facades.entities.PlayerFacade;
import com.mygdx.game.level.LevelExitDirection;
import com.mygdx.game.playState.WorldCoordinates;
import com.mygdx.game.playState.ZoneCoordinates;
import com.mygdx.game.playState.world.WorldZone;
import com.mygdx.game.playState.world.level.ZoneLevel;

import java.util.HashMap;

public class WorldFacade {
    
    /**
     * Teleports the player to a specific room in a specific zone with specific coordinates
     * @param zoneName - name of the place
     * @param coordinates - coordinates of the room to teleport to
     * @param x - in room x
     * @param y - in room y
     */
    public static void teleportPlayerToZone(String zoneName, ZoneCoordinates coordinates, float x, float y) {
        Managers.levelManager.saveCurrentRoomContents();
        Managers.entityManager.clearAllEntities();
        Managers.drawingManager.clearAllLights();
        Managers.playStateManager.goToZone(zoneName);
        Managers.playStateManager.setPlayerZoneCoordinates(coordinates.x, coordinates.y);
        Managers.playStateManager.playerReference.setX(x).setY(y);
        Managers.entityManager.addEntity(Managers.playStateManager.playerReference);
        Managers.levelManager.loadLevel(getLevelByZoneCoordinates(zoneName, coordinates));
    }
    
    
    public static void enterARoomThroughADoor(String zoneName, ZoneCoordinates coordinates, LevelExitDirection direction) {
        ZoneLevel targetLevel = getLevelByZoneCoordinates(zoneName, coordinates);
        
        // calculate entry point location
        float entryX = (((targetLevel.getRoomSize() - 1.1f) * 32f) * -direction.x) - 16f;
        float entryY = (((targetLevel.getRoomSize() - 1.1f) * 32f) * -direction.y) - 16f;
        
        teleportPlayerToZone(zoneName, coordinates, entryX, entryY);
    }
    
    /**
     * Start a new game.
     * Wipes the current run.
     */
    public static void initializeNewGame() {
        Managers.playStateManager.restartGame();
        Managers.playStateManager.playerReference = PlayerFacade.createNewPlayer(0f, 0f);
    }
    
    
    public static ZoneLevel getLevelByZoneCoordinates(String zoneName, ZoneCoordinates coordinates) {
        WorldZone zone = Managers.playStateManager.world.zones.get(zoneName);
        return zone.rooms.get(coordinates);
    }
    
    
    public static HashMap<LevelExitDirection, WorldCoordinates> getLevelExits(ZoneLevel level) {
        // TODO : cross zone travel
        WorldZone zone = Managers.playStateManager.getZoneByName(level.zoneName);
        
        HashMap<LevelExitDirection, WorldCoordinates> exits = new HashMap<>();
        
        for (LevelExitDirection direction : LevelExitDirection.values()) {
            ZoneCoordinates neighborCoordinates = new ZoneCoordinates(level.coordinates.x + direction.x, level.coordinates.y + direction.y);
            
            if (zone.rooms.containsKey(neighborCoordinates)) {
                exits.put(direction, new WorldCoordinates(neighborCoordinates, level.zoneName));
            }
        }
        
        return exits;
    }
    
}
