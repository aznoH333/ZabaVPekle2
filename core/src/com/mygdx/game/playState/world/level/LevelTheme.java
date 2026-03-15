package com.mygdx.game.playState.world.level;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.utils.types.NumberUtils;

/**
 * Visual appearance of a theme
 */
public class LevelTheme {

    public final static LevelTheme SPECIAL_PLACEHOLDER_THEME = new LevelTheme(
            new Color(0.2f, 0.2f, 0.2f, 1f),
            new Color(0.35f, 0.35f, 0.35f, 1f),
            new Color(0.25f, 0.25f, 0.25f, 1f),
            new Color(0.4f, 0.4f, 0.4f, 1f)
    );
    
    
    
    public final Color floorColor;
    public final Color brickColor;
    public final Color worldTopColor;
    public final Color doorColor;

    
    public LevelTheme(Color floorColor, Color brickColor, Color worldTopColor, Color doorColor) {
        this.floorColor = floorColor;
        this.brickColor = brickColor;
        this.worldTopColor = worldTopColor;
        this.doorColor = doorColor;
    }

    public static LevelTheme generateRandomLevelTheme() {
        Color baseColor = generateRandomColor(0.30f, 0.20f);
        Color topColor = darkenColor(baseColor, 0.75f);
        Color floorColor = generateRandomColor(0.10f, 0.15f);

        if (NumberUtils.randomChance(0.65f)) {
            floorColor = darkenColor(baseColor, 0.25f);
        }


        return new LevelTheme(
                floorColor,
                baseColor,
                topColor,
                new Color(
                        0.666f,
                        0.666f,
                        0.666f,
                        1f
                )
        );
    }

    private static Color generateRandomColor(float scale, float offset) {
        float totalValue = 1f;

        float r = NumberUtils.randomFloat(-1f, 1f);
        float g = NumberUtils.randomFloat(-1f, 1f);
        float b = NumberUtils.randomFloat(-1f, 1f);

        // map to colorSpace
        r = (r * scale * 0.5f) + offset;
        g = (g * scale * 0.5f) + offset;
        b = (b * scale * 0.5f) + offset;

        return new Color(r,g,b, 1f);
    }


    private static Color darkenColor(Color color, float percentage) {
        return new Color(
                color.r * percentage,
                color.g * percentage,
                color.b * percentage,
                1f
        );
    }
}
