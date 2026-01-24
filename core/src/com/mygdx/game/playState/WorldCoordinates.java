package com.mygdx.game.playState;


public class WorldCoordinates {
    public final ZoneCoordinates zoneCoordinates;
    public final String zoneName;
    
    public WorldCoordinates(ZoneCoordinates coordinates, String zoneName) {
        this.zoneCoordinates = coordinates;
        this.zoneName = zoneName;
    }
    
}
