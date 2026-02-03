package com.mygdx.game.playState.world.level;

public enum LevelType {
    SPAWN(4, 0, 0, 0, false, "hud_map_tiles_0002"),
    MAJOR_COMBAT(7, 7, 12, 10, true, "hud_map_tiles_0002"),
    FILLER(5, 3, 7, 20, true, "hud_map_tiles_0002"),
    LOOT(3, 0, 0, 1, false, "hud_map_tiles_0003"),
    BOSS(7, 0, 0, 0, true, "hud_map_tiles_0002");

    public final int roomSize;
    public final int minEnemies;
    public final int maxEnemies;
    public final int spawnSpeed;
    public final boolean locksWhenEntered;
    public final String minimapSprite;

    LevelType(int size, int minEnemies, int maxEnemies, int spawnSpeed, boolean locksWhenEntered, String minimapSprite) {
        this.roomSize = size;
        this.minEnemies = minEnemies;
        this.maxEnemies = maxEnemies;
        this.spawnSpeed = spawnSpeed;
        this.locksWhenEntered = locksWhenEntered;
        this.minimapSprite = minimapSprite;
    }
}
