package com.mygdx.game.playState.world.level;

public enum LevelType {
    SPAWN(4, 0, 0, 0, false, "hud_map_tiles_0002", LevelTheme.SPECIAL_PLACEHOLDER_THEME),
    MAJOR_COMBAT(7, 7, 12, 10, true, "hud_map_tiles_0002", null),
    FILLER(5, 3, 7, 20, true, "hud_map_tiles_0002", null),
    LOOT(3, 0, 0, 1, false, "hud_map_tiles_0003", LevelTheme.SPECIAL_PLACEHOLDER_THEME),
    BOSS(7, 0, 0, 0, true, "hud_map_tiles_0002", null);

    public final int roomSize;
    public final int minEnemies;
    public final int maxEnemies;
    public final int spawnSpeed;
    public final boolean locksWhenEntered;
    public final String minimapSprite;
    public final LevelTheme roomThemeOverride;

    LevelType(int size, int minEnemies, int maxEnemies, int spawnSpeed, boolean locksWhenEntered, String minimapSprite, LevelTheme roomThemeOverride) {
        this.roomSize = size;
        this.minEnemies = minEnemies;
        this.maxEnemies = maxEnemies;
        this.spawnSpeed = spawnSpeed;
        this.locksWhenEntered = locksWhenEntered;
        this.minimapSprite = minimapSprite;
        this.roomThemeOverride = roomThemeOverride;
    }
}
