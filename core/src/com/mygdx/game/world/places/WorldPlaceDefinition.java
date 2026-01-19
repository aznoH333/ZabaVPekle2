package com.mygdx.game.world.places;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.entities.items.Quality;

public enum WorldPlaceDefinition {

    START(
        "start",
        new Color(0.1f, 0.1f, 0.1f, 1f),
        new Color(0.20f, 0.20f, 0.20f, 1f),
        new Color(0.4f, 0.4f, 0.4f, 1f),
        new Color(0.8f, 0.8f, 0.8f, 1f),
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
        new Color(0.05f, 0.05f, 0.1f, 1f),
        new Color(0.1f, 0.25f, 0.666f, 1f),
        new Color(0.1f, 0.25f, 0.666f, 1f),
        new Color(0.8f, 0.8f, 0.8f, 1f),
        Quality.COMMON,
        Quality.POOR,
        Quality.COMMON,
        3.1f,
        new Color(0f, 0f, 0.302f, 1f),
        128f,
        128f
    ),
    RED("red",
        new Color(0.2f, 0.2f, 0.2f, 1f),
        new Color(0.666f, 0.0f, 0.0f, 1f),
        new Color(0.666f, 0.0f, 0.0f, 1f),
        new Color(0.4f, 0.4f, 0.4f, 1f),
        Quality.COMMON,
        Quality.COMMON,
        Quality.REFINED,
        16f,
        new Color(0.667f, 0.0f, 0.0f, 1f),
        -128f,
        64f
    );


    public final Color floorColor;
    public final Color brickColor;
    public final Color worldTopColor;
    public final Color doorColor;
    public final String placeName;
    public final Quality lootRoomBoxQuality;
    public final Quality combatRoomDropQuality;
    public final Quality bossRoomDropQuality;
    public final float placeDifficulty;
    public final Color worldMapColor;
    public final float worldMapX;
    public final float worldMapY;


    WorldPlaceDefinition(
        String placeName,
        Color floorColor,
        Color brickColor,
        Color worldTopColor,
        Color doorColor,
        Quality lootRoomBoxQuality,
        Quality combatRoomDropQuality,
        Quality bossRoomDropQuality,
        float placeDifficulty,
        Color worldMapColor,
        float worldMapX,
        float worldMapY
    ) {
        this.placeName = placeName;
        this.floorColor = floorColor;
        this.doorColor = doorColor;
        this.brickColor = brickColor;
        this.worldTopColor = worldTopColor;
        this.lootRoomBoxQuality = lootRoomBoxQuality;
        this.combatRoomDropQuality = combatRoomDropQuality;
        this.bossRoomDropQuality = bossRoomDropQuality;
        this.placeDifficulty = placeDifficulty;
        this.worldMapColor = worldMapColor;
        this.worldMapX = worldMapX;
        this.worldMapY = worldMapY;
    }

    public Place generatePlace() {
        Place place = new Place(
            this
        );

        return place;
    }
}
