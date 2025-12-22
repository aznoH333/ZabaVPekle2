package com.mygdx.game.world.places;

import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;

public class Place {

    public final WorldPlaceDefinition type;
    public final Color floorColor;
    public final Color brickColor;
    public final Color worldTopColor;
    public final Color doorColor;
    public final String placeName;

    public final ArrayList<PlaceRoom> rooms = new ArrayList<>();

    private int currentProgress = 0;


    public Place(WorldPlaceDefinition type) {
        this.type = type;

        this.floorColor = type.floorColor;
        this.brickColor = type.brickColor;
        this.worldTopColor = type.worldTopColor;
        this.doorColor = type.doorColor;
        this.placeName = type.placeName;


        rooms.add(
                new PlaceRoom(RoomType.SPAWN)
        );
    }

    public void completedRoom() {
        currentProgress++;
    }

    public PlaceRoom getCurrentRoom() {
        return rooms.get(currentProgress);
    }

    public boolean isComplete() {
        return currentProgress >= rooms.size();
    }
}
