package com.mygdx.game.playState;


import com.mygdx.game.entities.Entity;
import com.mygdx.game.playState.world.WorldMap;

/**
 * Responsible for holding information relating to a SINGLE playthrough.
 * Like map layout. visited zones and player info.
 * SHOULDN'T hold data that persists across playthroughs
 */
public class PlayStateManager {
    private static PlayStateManager instance;
    
    public static PlayStateManager getInstance() {
        if (instance == null) {
            instance = new PlayStateManager();
        }
        return instance;
    }
    
    
    public Entity playerReference = null;
    public WorldMap worldMap = new WorldMap();
    
}
