package com.mygdx.game.world.places;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.facades.EnemyGeneration.EnemyGeneratorFacade;
import com.mygdx.game.utils.Trait;

import java.util.ArrayList;

public class Place {

    public final WorldPlaceDefinition type;
    public final Color floorColor;
    public final Color brickColor;
    public final Color worldTopColor;
    public final Color doorColor;
    public final String placeName;
    
    
    public final Color mapColor;

    public final ArrayList<PlaceRoom> rooms = new ArrayList<>();

    private int currentProgress = 0;

    private ArrayList<Trait<Entity>> enemyRoster;

    public float mapX;
    public float mapY;

    public Place(WorldPlaceDefinition type) {
        this.type = type;

        this.floorColor = type.floorColor;
        this.brickColor = type.brickColor;
        this.worldTopColor = type.worldTopColor;
        this.doorColor = type.doorColor;
        this.placeName = type.placeName;
        this.mapColor = type.worldMapColor;
        
        this.mapX = type.worldMapX;
        this.mapY = type.worldMapY;

        this.enemyRoster = EnemyGeneratorFacade.generateEnemyRoster(2, type.placeDifficulty);

        rooms.add(
            new PlaceRoom(RoomType.SPAWN, enemyRoster)
        );

        for (int i = 0; i < 1; i++) {
            this.rooms.add(new PlaceRoom(RoomType.FILLER, enemyRoster));
            this.rooms.add(new PlaceRoom(RoomType.FILLER, enemyRoster));
            this.rooms.add(new PlaceRoom(RoomType.MAJOR_COMBAT, enemyRoster));
            this.rooms.add(new PlaceRoom(RoomType.FILLER, enemyRoster));
            this.rooms.add(new PlaceRoom(RoomType.LOOT, enemyRoster));
        }
        this.rooms.add(new PlaceRoom(RoomType.BOSS, enemyRoster));

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
