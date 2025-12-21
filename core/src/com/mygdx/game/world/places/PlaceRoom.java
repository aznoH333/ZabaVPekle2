package com.mygdx.game.world.places;

import com.mygdx.game.utils.NumberUtils;

public class PlaceRoom {
    public final int roomSize;
    public final int enemiesToSpawn;
    public final int enemySpawnSpeed;
    public final RoomType type;


    public PlaceRoom(RoomType roomType) {
        this.type = roomType;
        this.roomSize = type.roomSize;
        this.enemiesToSpawn = NumberUtils.randomInt(roomType.minEnemies, roomType.maxEnemies);
        this.enemySpawnSpeed = roomType.spawnSpeed;
    }
}
