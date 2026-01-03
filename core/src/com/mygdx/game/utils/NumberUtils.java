package com.mygdx.game.utils;

public class NumberUtils {
    public final static float HALF_PI = (float) (Math.PI / 2f);
    public final static float TWO_PI = (float) (Math.PI * 2f);
    public final static float PI = (float) Math.PI;
    public final static float QUARTER_PI = HALF_PI / 2f;
    public final static float EIGHTH_PI = QUARTER_PI / 2f;
    public final static float THIRD_PI = PI / 3f;


    public static int boolToInt(boolean value) {
        if (value) {
            return 1;
        }
        return 0;
    }


    public static float boolToFloat(boolean value) {
        return boolToInt(value);
    }

    public static float boolToSign(boolean value) {
        return boolToInt(value) * 2 - 1;
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
        float range = max - min;

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

    public static float constrictRotationToRad(float rotation) {
        if (rotation < 0) {
            return (TWO_PI - Math.abs(rotation)) % TWO_PI;
        }


        return rotation % TWO_PI;
    }

    public static boolean checkCollisions(
        float x1,
        float y1,
        float w1,
        float h1,
        float x2,
        float y2,
        float w2,
        float h2
    ) {
        float width = w1 / 2.0f;
        float height = h1 / 2.0f;

        float otherWidth = w2 / 2.0f;
        float otherHeight = h2 / 2.0f;

        return x1 - width < x2 + otherWidth &&
            x1 + width > x2 - otherWidth &&
            y1 - height < y2 + otherHeight &&
            y1 + height > y2 - otherHeight;
    }

    public static float clampValue(float value, float min, float max) {
        return Math.max(Math.min(max, value), min);
    }

    public static float gravitateNumber(float value, float target, float stepSize) {
        if (Math.abs(value - target) <= stepSize + 0.01f) {
            return target;
        }else if (value < target) {
            return value + stepSize;
        }else {
            return value - stepSize;
        }
    }

}
