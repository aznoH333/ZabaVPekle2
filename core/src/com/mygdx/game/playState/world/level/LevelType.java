package com.mygdx.game.playState.world.level;

public enum LevelType {
    SPAWN(4, 0, 0, LevelTheme.SPECIAL_PLACEHOLDER_THEME),
    MAJOR_COMBAT(7, 7, 12, null),
    FILLER(5, 3, 7, null),
    LOOT(4, 0, 0, LevelTheme.SPECIAL_PLACEHOLDER_THEME),
    BOSS(8, 0, 0, null),
    SCRAP_ROOM(4, 0, 0, LevelTheme.SPECIAL_PLACEHOLDER_THEME),
    MINI_BOSS_ROOM(7, 0, 0, LevelTheme.SPECIAL_PLACEHOLDER_THEME),
    MACHINE_ROOM(4, 0, 0, LevelTheme.SPECIAL_PLACEHOLDER_THEME),
    ZONE_EXIT(5, 0, 0, LevelTheme.SPECIAL_PLACEHOLDER_THEME);

    public final int roomSize;
    public final int minEnemies;
    public final int maxEnemies;
    public final LevelTheme roomThemeOverride;

    LevelType(int size, int minEnemies, int maxEnemies, LevelTheme roomThemeOverride) {
        this.roomSize = size;
        this.minEnemies = minEnemies;
        this.maxEnemies = maxEnemies;
        this.roomThemeOverride = roomThemeOverride;
    }
}
