package com.mygdx.game.world.places;

public enum RoomType {
    SPAWN(8, 0, 0, 0),
    MAJOR_COMBAT(15, 10, 20, 10),
    FILLER(10, 3, 7, 20),
    LOOT(8, 0, 2, 1),
    BOSS(20, 0, 0, 0);

    public final int roomSize;
    public final int minEnemies;
    public final int maxEnemies;
    public final int spawnSpeed;

    RoomType(int size, int minEnemies, int maxEnemies, int spawnSpeed){
        this.roomSize = size;
        this.minEnemies = minEnemies;
        this.maxEnemies = maxEnemies;
        this.spawnSpeed = spawnSpeed;
    }
}
