package com.mygdx.game.facades.world;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Managers;
import com.mygdx.game.entities.items.Quality;
import com.mygdx.game.facades.entities.GameEntityCreatorFacade;
import com.mygdx.game.level.LevelExitDirection;
import com.mygdx.game.playState.MapCoordinates;
import com.mygdx.game.playState.world.WorldZone;
import com.mygdx.game.playState.world.WorldZoneDefinition;
import com.mygdx.game.playState.world.level.LevelTheme;
import com.mygdx.game.playState.world.level.ZoneLevel;

import java.util.HashMap;

/**
 * TODO : this is just random junk that needs to be reorganized
 */
public class WorldFacade {

    /**
     * Start a new game.
     * Wipes the current run.
     */
    public static void initializeNewGame() {
        Managers.playStateManager.restartGame();
        Managers.playStateManager.goToNextZone();
        Managers.playStateManager.playerReference = GameEntityCreatorFacade.createNewPlayer(0f, 0f);
        WorldMovementFacade.teleportPlayerToZone(new MapCoordinates(0,0), 0f, 0f);
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


    public static WorldZoneDefinition generateWorldZone(int zoneIndex) {

        float difficulty = zoneIndex;


        if (zoneIndex >= 3) {
            difficulty += (zoneIndex - 2);
        }

        if (zoneIndex >= 6) {
            difficulty += difficulty - 6;
        }

        if (zoneIndex >= 10) {
            difficulty += (float) Math.pow(zoneIndex - 9, 2);
        }

        if (zoneIndex >= 20) {
            difficulty += (float) Math.pow(2, difficulty);
        }

        if (zoneIndex >= 30) {
            difficulty = (float) Math.pow(difficulty, difficulty);
        }
        System.out.println("difficulty : " + difficulty);

        return new WorldZoneDefinition(
                LevelTheme.generateRandomLevelTheme(),
                Quality.COMMON,
                Quality.COMMON,
                Quality.COMMON,
                difficulty,
                new Color(0.25f, 0.25f, 0.25f, 1f)
        );
    }
}
