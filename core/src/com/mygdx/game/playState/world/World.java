package com.mygdx.game.playState.world;

import java.util.HashMap;

public class World {
    
    
    /** Map zone name to zone */
    public HashMap<String, WorldZone> zones = new HashMap<>();
    
    public World() {
        for (WorldZoneDefinition zoneDefinition : WorldZoneDefinition.values()) {
            zones.put(zoneDefinition.zoneName,new WorldZone(zoneDefinition));
        }
    }
}
