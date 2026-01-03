package com.mygdx.game.world;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.items.Quality;
import com.mygdx.game.utils.types.NumberUtils;
import com.mygdx.game.world.places.Place;
import com.mygdx.game.world.places.PlaceRoom;
import com.mygdx.game.world.places.WorldPlaceDefinition;

import java.util.ArrayList;

public class WorldProgress {


    public int levelsCompleted = 0;


    public Color floorColor = new Color(0.2f, 0.2f, 0.2f, 1f);
    public Color brickColor = new Color(0.95f, 0.25f, 0.1f, 1f);
    public Color worldTopColor = new Color(0.95f, 0.25f, 0.1f, 1f);
    public Color doorColor = new Color(0.8f, 0.8f, 0.8f, 1f);


    public int outerWorldSize = 25;
    public int innerWorldSize = 10;


    private int currentPlaceIndex = 0;
    private ArrayList<Place> places = new ArrayList<>();
    private Place currentPlace;

    public WorldProgress() {
        // temp place generation
        for (WorldPlaceDefinition definition : WorldPlaceDefinition.values()) {
            places.add(definition.generatePlace());
        }
        goToPlace(0);
    }


    public void completedLevel() {
        levelsCompleted++;

        currentPlace.completedRoom();

        if (currentPlace.isComplete()) {
            currentPlaceIndex++;
            goToPlace(currentPlaceIndex);
        }

        PlaceRoom room = currentPlace.getCurrentRoom();
        innerWorldSize = room.roomSize;
        outerWorldSize = room.roomSize + 5;

    }

    public int howManyEnemiesShouldSpawn() {
        return currentPlace.getCurrentRoom().enemiesToSpawn;
    }


    private void goToPlace(int placeIndex) {
        currentPlace = places.get(placeIndex);
        floorColor = currentPlace.floorColor;
        brickColor = currentPlace.brickColor;
        doorColor = currentPlace.doorColor;
        worldTopColor = currentPlace.worldTopColor;
    }

    public boolean shouldLockDoors() {
        return currentPlace.getCurrentRoom().enemiesToSpawn != 0;
    }

    public Quality shouldSpawnBox() {
        PlaceRoom room = currentPlace.getCurrentRoom();


        switch (room.type) {
            case LOOT:
                return currentPlace.type.lootRoomBoxQuality;
            case BOSS:
                return currentPlace.type.bossRoomDropQuality;
            case MAJOR_COMBAT:
                if (NumberUtils.randomChance(0.25f)) {
                    return currentPlace.type.combatRoomDropQuality;
                } else {
                    return null;
                }
            case FILLER:
                if (NumberUtils.randomChance(0.05f)) {
                    return currentPlace.type.combatRoomDropQuality;
                } else {
                    return null;
                }
            default:
                return null;
        }
    }

    public Entity getReferenceEnemyToSpawn() {
        return currentPlace.getCurrentRoom().getReferenceEnemyFromRoster();
    }
}
