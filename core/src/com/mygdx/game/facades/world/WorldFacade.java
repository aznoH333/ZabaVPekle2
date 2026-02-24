package com.mygdx.game.facades.world;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Managers;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.components.control.DelayedEvent;
import com.mygdx.game.entities.items.Quality;
import com.mygdx.game.facades.entities.PlayerFacade;
import com.mygdx.game.facades.sceen.VisualEffectsFacade;
import com.mygdx.game.level.LevelExitDirection;
import com.mygdx.game.playState.MapCoordinates;
import com.mygdx.game.playState.world.WorldZone;
import com.mygdx.game.playState.world.WorldZoneDefinition;
import com.mygdx.game.playState.world.level.LevelTheme;
import com.mygdx.game.playState.world.level.ZoneLevel;

import java.util.HashMap;

public class WorldFacade {
    
    /**
     * Teleports the player to a specific room in a specific zone with specific coordinates
     * @param coordinates - coordinates of the room to teleport to
     * @param x - in room x
     * @param y - in room y
     */
    public static void teleportPlayerToZone(MapCoordinates coordinates, float x, float y) {
        System.out.println("moving player");
        Managers.levelManager.saveCurrentRoomContents();
        Managers.entityManager.clearAllEntities();
        VisualEffectsFacade.clearAllLights();
        Managers.playStateManager.setPlayerZoneCoordinates(coordinates.x, coordinates.y);
        Managers.playStateManager.playerReference.setX(x).setY(y);
        Managers.entityManager.addEntity(Managers.playStateManager.playerReference);
        Managers.levelManager.loadLevel(getLevelByZoneCoordinates(coordinates));
        
    }
    
    
    public static void enterARoomThroughADoor(MapCoordinates coordinates, LevelExitDirection direction) {
        System.out.println("moving from " + Managers.playStateManager.playerMapCoordinates + " to " + coordinates);
        
        Managers.drawingManager.screenEffectShaderHandler.dimScreen(50);
        Managers.entityManager.freezeEntities(60);
        
        Managers.entityManager.addEntity(
            new Entity()
                .addComponent(new DelayedEvent(
                    ()-> {
                        ZoneLevel targetLevel = getLevelByZoneCoordinates(coordinates);
                        
                        // calculate entry point location
                        float entryX = (((targetLevel.getRoomSize() - 1.2f) * 32f) * -direction.x) - 16f;
                        float entryY = (((targetLevel.getRoomSize() - 1.2f) * 32f) * -direction.y) - 16f;
                        
                        teleportPlayerToZone(coordinates, entryX, entryY);
                    },
                    25
                ))
        );
        
        
    }
    
    /**
     * Start a new game.
     * Wipes the current run.
     */
    public static void initializeNewGame() {
        Managers.playStateManager.restartGame();
        Managers.playStateManager.goToNextZone();
        Managers.playStateManager.playerReference = PlayerFacade.createNewPlayer(0f, 0f);
        teleportPlayerToZone(new MapCoordinates(0,0), 0f, 0f);
    }
    
    
    public static ZoneLevel getLevelByZoneCoordinates(MapCoordinates coordinates) {
        WorldZone zone = Managers.playStateManager.currentZone;
        return zone.rooms.get(coordinates);
    }
    
    
    public static HashMap<LevelExitDirection, MapCoordinates> getLevelExits(ZoneLevel level) {
        // TODO : cross zone travel
        WorldZone zone = Managers.playStateManager.currentZone;
        
        HashMap<LevelExitDirection, MapCoordinates> exits = new HashMap<>();
        
        for (LevelExitDirection direction : LevelExitDirection.values()) {
            MapCoordinates neighborCoordinates = new MapCoordinates(level.coordinates.x + direction.x, level.coordinates.y + direction.y);
            
            if (zone.rooms.containsKey(neighborCoordinates)) {
                exits.put(direction, neighborCoordinates);
            }
        }
        
        return exits;
    }


    public static WorldZoneDefinition generateWorldZone() {
        return new WorldZoneDefinition(
                LevelTheme.generateRandomLevelTheme(),
                Quality.COMMON,
                Quality.COMMON,
                Quality.COMMON,
                1f,
                new Color(0.25f, 0.25f, 0.25f, 1f)
        );
    }
}
