package com.mygdx.game;

public class NumberUtils {
    public static int boolToInt(boolean value) {
        if (value) {
            return 1;
        }
        return 0;
    }


    public static float boolToFloat(boolean value) {
        return boolToInt(value);
    }
}
