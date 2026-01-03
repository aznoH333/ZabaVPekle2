package com.mygdx.game.entities.components.behaviour.ememy;

import java.util.Objects;

public final class Coordinate {
    private final float x;
    private final float y;

    public Coordinate(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Coordinate that = (Coordinate) obj;
        return Float.floatToIntBits(this.x) == Float.floatToIntBits(that.x) &&
            Float.floatToIntBits(this.y) == Float.floatToIntBits(that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coordinate[" +
            "x=" + x + ", " +
            "y=" + y + ']';
    }

}
