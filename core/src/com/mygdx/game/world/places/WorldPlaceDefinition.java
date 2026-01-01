package com.mygdx.game.world.places;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.entities.items.Quality;

public enum WorldPlaceDefinition {

    START(
        "start",
        new Color(0.2f, 0.2f, 0.2f, 1f),
        new Color(0.95f, 0.25f, 0.1f, 1f),
        new Color(0.95f, 0.25f, 0.1f, 1f),
        new Color(0.8f, 0.8f, 0.8f, 1f),
        Quality.POOR,
        Quality.POOR,
        Quality.COMMON,
        1f
        ),
    BLUE(
        "blue",
        new Color(0.2f, 0.2f, 0.2f, 1f),
        new Color(0.1f, 0.25f, 0.95f, 1f),
        new Color(0.1f, 0.25f, 0.95f, 1f),
        new Color(0.8f, 0.8f, 0.8f, 1f),
        Quality.COMMON,
        Quality.POOR,
        Quality.COMMON,
        3.1f
    ),
    BLACK("black",
        new Color(0.2f, 0.2f, 0.2f, 1f),
        new Color(0.1f, 0.1f, 0.1f, 1f),
        new Color(0.1f, 0.1f, 0.1f, 1f),
        new Color(0.4f, 0.4f, 0.4f, 1f),
        Quality.COMMON,
        Quality.COMMON,
        Quality.REFINED,
        16f);


    public final Color floorColor;
    public final Color brickColor;
    public final Color worldTopColor;
    public final Color doorColor;
    public final String placeName;
    public final Quality lootRoomBoxQuality;
    public final Quality combatRoomDropQuality;
    public final Quality bossRoomDropQuality;
    public final float placeDifficulty;


    WorldPlaceDefinition(
        String placeName,
        Color floorColor,
        Color brickColor,
        Color worldTopColor,
        Color doorColor,
        Quality lootRoomBoxQuality,
        Quality combatRoomDropQuality,
        Quality bossRoomDropQuality,
        float placeDifficulty
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
    }

    public Place generatePlace() {
        Place place = new Place(
            this
        );

        return place;
    }
}
