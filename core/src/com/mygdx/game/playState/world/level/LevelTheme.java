package com.mygdx.game.playState.world.level;

import com.badlogic.gdx.graphics.Color;

/**
 * Visual appearance of a theme
 */
public enum LevelTheme {
    HANGAR_PLATING(
        new Color(0.1f, 0.1f, 0.1f, 1f),
        new Color(0.20f, 0.20f, 0.20f, 1f),
        new Color(0.4f, 0.4f, 0.4f, 1f),
        new Color(0.8f, 0.8f, 0.8f, 1f)
    ),
    BLUE_DUNGEON(
        new Color(0.05f, 0.05f, 0.1f, 1f),
        new Color(0.1f, 0.25f, 0.666f, 1f),
        new Color(0.1f, 0.25f, 0.666f, 1f),
        new Color(0.8f, 0.8f, 0.8f, 1f)
    ),
    RED_PLACEHOLDER(
        new Color(0.2f, 0.2f, 0.2f, 1f),
        new Color(0.666f, 0.0f, 0.0f, 1f),
        new Color(0.666f, 0.0f, 0.0f, 1f),
        new Color(0.4f, 0.4f, 0.4f, 1f)
    ),
    SPECIAL_PLACEHOLDER(
        new Color(0.2f, 0.2f, 0.2f, 1f),
        new Color(0.666f, 0.555f, 0.0f, 1f),
        new Color(0.666f, 0.555f, 0.0f, 1f),
        new Color(0.4f, 0.4f, 0.4f, 1f)
    );
    
    
    
    public final Color floorColor;
    public final Color brickColor;
    public final Color worldTopColor;
    public final Color doorColor;
    
    
    LevelTheme(Color floorColor, Color brickColor, Color worldTopColor, Color doorColor) {
        this.floorColor = floorColor;
        this.brickColor = brickColor;
        this.worldTopColor = worldTopColor;
        this.doorColor = doorColor;
    }
}
