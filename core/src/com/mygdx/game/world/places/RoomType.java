package com.mygdx.game.world.places;

public enum RoomType {
    SPAWN(5, 0, 0, 0, false),
    MAJOR_COMBAT(10, 10, 20, 10, true),
    FILLER(7, 3, 7, 20, true),
    LOOT(5, 0, 0, 1, false),
    BOSS(20, 0, 0, 0, true);

    public final int roomSize;
    public final int minEnemies;
    public final int maxEnemies;
    public final int spawnSpeed;
    public final boolean locksWhenEntered;

    RoomType(int size, int minEnemies, int maxEnemies, int spawnSpeed, boolean locksWhenEntered){
        this.roomSize = size;
        this.minEnemies = minEnemies;
        this.maxEnemies = maxEnemies;
        this.spawnSpeed = spawnSpeed;
        this.locksWhenEntered = locksWhenEntered;
    }
}
