package com.mygdx.game;

public class NumberUtils {
    public final static float HALF_PI = (float) (Math.PI / 2f);


    public static int boolToInt(boolean value) {
        if (value) {
            return 1;
        }
        return 0;
    }


    public static float boolToFloat(boolean value) {
        return boolToInt(value);
    }

    public static float directionToward(float startX, float startY, float endX, float endY) {
        return (float) Math.atan2(endY - startY, endX - startX);
    }

    public static float pythagoras(float x, float y, float x2, float y2) {
        return (float) Math.sqrt(Math.pow(x2 - x, 2) + Math.pow(y2 - y, 2));
    }
}
