package com.mygdx.game.world.places;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.entities.items.Quality;

public enum WorldPlaceDefinition {

    TEST(
            "test",
            new Color(0.2f, 0.2f, 0.2f, 1f),
            new Color(0.95f, 0.25f, 0.1f, 1f),
            new Color(0.95f, 0.25f, 0.1f, 1f),
            new Color(0.8f, 0.8f, 0.8f, 1f),
            Quality.POOR,
            Quality.POOR,
            Quality.COMMON);


    public final Color floorColor;
    public final Color brickColor;
    public final Color worldTopColor;
    public final Color doorColor;
    public final String placeName;
    public final Quality lootRoomBoxQuality;
    public final Quality combatRoomDropQuality;
    public final Quality bossRoomDropQuality;


    WorldPlaceDefinition(
            String placeName,
            Color floorColor,
            Color brickColor,
            Color worldTopColor,
            Color doorColor,
            Quality lootRoomBoxQuality,
            Quality combatRoomDropQuality,
            Quality bossRoomDropQuality
    ) {
        this.placeName = placeName;
        this.floorColor = floorColor;
        this.doorColor = doorColor;
        this.brickColor = brickColor;
        this.worldTopColor = worldTopColor;
        this.lootRoomBoxQuality = lootRoomBoxQuality;
        this.combatRoomDropQuality = combatRoomDropQuality;
        this.bossRoomDropQuality = bossRoomDropQuality;
    }

    public Place generatePlace() {
        Place place = new Place(
                this
        );

        for (int i = 0; i < 2; i++) {
            place.rooms.add(new PlaceRoom(RoomType.FILLER));
            place.rooms.add(new PlaceRoom(RoomType.FILLER));
            place.rooms.add(new PlaceRoom(RoomType.MAJOR_COMBAT));
            place.rooms.add(new PlaceRoom(RoomType.FILLER));
            place.rooms.add(new PlaceRoom(RoomType.LOOT));
        }
        place.rooms.add(new PlaceRoom(RoomType.BOSS));

        return place;
    }
}
