package com.mygdx.game.playState.world;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.entities.items.Quality;
import com.mygdx.game.playState.world.level.LevelTheme;

public enum WorldZoneDefinition {

    START(
        "start",
        LevelTheme.HANGAR_PLATING,
        Quality.POOR,
        Quality.POOR,
        Quality.COMMON,
        1f,
        new Color(1f, 1f, 1f, 1f),
        0.0f,
        0.0f
    ),
    BLUE(
        "blue",
        LevelTheme.BLUE_DUNGEON,
        Quality.COMMON,
        Quality.POOR,
        Quality.COMMON,
        3.1f,
        new Color(0f, 0f, 0.302f, 1f),
        128f,
        128f
    ),
    RED("red",
        LevelTheme.RED_PLACEHOLDER,
        Quality.COMMON,
        Quality.COMMON,
        Quality.REFINED,
        16f,
        new Color(0.667f, 0.0f, 0.0f, 1f),
        -128f,
        64f
    );

    
    public final String zoneName;
    public final LevelTheme theme;
    public final Quality lootRoomBoxQuality;
    public final Quality combatRoomDropQuality;
    public final Quality bossRoomDropQuality;
    public final float placeDifficulty;
    public final Color worldMapColor;
    public final float worldMapX;
    public final float worldMapY;


    WorldZoneDefinition(
        String zoneName,
        LevelTheme theme,
        Quality lootRoomBoxQuality,
        Quality combatRoomDropQuality,
        Quality bossRoomDropQuality,
        float placeDifficulty,
        Color worldMapColor,
        float worldMapX,
        float worldMapY
    ) {
        this.zoneName = zoneName;
        this.theme = theme;
        this.lootRoomBoxQuality = lootRoomBoxQuality;
        this.combatRoomDropQuality = combatRoomDropQuality;
        this.bossRoomDropQuality = bossRoomDropQuality;
        this.placeDifficulty = placeDifficulty;
        this.worldMapColor = worldMapColor;
        this.worldMapX = worldMapX;
        this.worldMapY = worldMapY;
    }

}
