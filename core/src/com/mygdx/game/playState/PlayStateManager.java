package com.mygdx.game.playState;


import com.mygdx.game.Managers;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.facades.world.WorldFacade;
import com.mygdx.game.playState.inventory.Inventory;
import com.mygdx.game.playState.world.WorldZone;

/**
 * Responsible for holding information relating to a SINGLE playthrough.
 * Like map layout. visited zones and player info.
 * SHOULDN'T hold data that persists across playthroughs
 */
public class PlayStateManager {
    private static PlayStateManager instance;
    /// Number of ticks since game start
    public long gameTime = 0;
    public int currentZoneIndex = 1;
    
    public static PlayStateManager getInstance() {
        if (instance == null) {
            instance = new PlayStateManager();
        }
        return instance;
    }
    
    
    public Entity playerReference = null;
    public WorldZone currentZone = null;
    public MapCoordinates playerMapCoordinates = null;
    public Inventory inventory = new Inventory();
    
    
    
    public void goToNextZone() {
        currentZoneIndex++;
        this.currentZone = new WorldZone(WorldFacade.generateWorldZone(currentZoneIndex));

        Managers.drawingManager.lightingShaderHandler.setAmbientLight(currentZone.ambientLight);
    }
    
    
    public MapCoordinates getPlayerZoneCoordinates() {
        return this.playerMapCoordinates;
    }
    
    public void setPlayerZoneCoordinates(int x, int y) {
        this.playerMapCoordinates = new MapCoordinates(x, y);
    }
    
    public void restartGame() {
        this.inventory = new Inventory();
        this.playerReference = null;
        this.currentZone = null;
        this.playerMapCoordinates = null;
        this.currentZoneIndex = 1;
    }

}
