package com.mygdx.game.playState.world;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.entities.items.Quality;
import com.mygdx.game.playState.world.level.LevelTheme;

public class WorldZoneDefinition {

    public final LevelTheme levelTheme;
    public final Quality lootRoomBoxQuality;
    public final Quality combatRoomDropQuality;
    public final Quality bossRoomDropQuality;
    public final float placeDifficulty;
    public final Color ambientLight;
    public final int zoneIndex;

    public WorldZoneDefinition(
            LevelTheme levelTheme,
            Quality lootRoomBoxQuality,
            Quality combatRoomDropQuality,
            Quality bossRoomDropQuality,
            float placeDifficulty,
            Color ambientLight,
            int zoneIndex
    ) {
        this.levelTheme = levelTheme;
        this.lootRoomBoxQuality = lootRoomBoxQuality;
        this.combatRoomDropQuality = combatRoomDropQuality;
        this.bossRoomDropQuality = bossRoomDropQuality;
        this.placeDifficulty = placeDifficulty;
        this.ambientLight = ambientLight;
        this.zoneIndex = zoneIndex;
    }

}
