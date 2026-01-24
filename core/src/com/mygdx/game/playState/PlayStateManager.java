package com.mygdx.game.playState;


import com.mygdx.game.entities.Entity;
import com.mygdx.game.playState.world.World;
import com.mygdx.game.playState.world.WorldZone;
import com.mygdx.game.playState.world.level.ZoneLevel;

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
    public World world = new World();
    public WorldZone currentZone = null;
    public ZoneCoordinates playerZoneCoordinates = null;
    
    
    
    public void goToZone(String zoneName) {
        this.currentZone = world.zones.get(zoneName);
    }
    
    
    public ZoneCoordinates getPlayerZoneCoordinates() {
        return this.playerZoneCoordinates;
    }
    
    public void setPlayerZoneCoordinates(int x, int y) {
        this.playerZoneCoordinates = new ZoneCoordinates(x, y);
    }
    
    public void restartGame() {
        this.world = new World();
        this.playerReference = null;
        this.currentZone = null;
        this.playerZoneCoordinates = null;
    }
    
    public WorldZone getZoneByName(String zoneName) {
        return this.world.zones.get(zoneName);
    }
}
