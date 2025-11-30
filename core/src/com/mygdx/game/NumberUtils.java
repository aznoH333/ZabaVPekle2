package com.mygdx.game;

public class NumberUtils {
    public final static float HALF_PI = (float) (Math.PI / 2f);
    public final static float TWO_PI = (float) (Math.PI * 2f);


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

    public static int randomInt(int min, int max) {
        int range = max - min + 1;

        return (int) Math.floor(Math.random() * range) + min;
    }

    public static float randomFloat(float min, float max) {
        float range = max - min + 1f;

        return (float) (Math.random() * range + min);
    }

    public static boolean randomChance(float chance) {
        return randomFloat(0, 1) < chance;
    }

    public String padNumberWithZeros(int number) {
        StringBuilder output = new StringBuilder(number);

        while (output.length() < 4) {
            output.insert(0, '0');
        }
        return output.toString();

    }
}
