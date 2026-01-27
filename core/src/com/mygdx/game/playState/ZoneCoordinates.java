package com.mygdx.game.playState;

import java.util.Objects;

public class ZoneCoordinates {
    public final int x;
    public final int y;
    private final int hashCode;
    
    public ZoneCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
        this.hashCode = Objects.hash(x, y);
    }
    
    @Override
    public boolean equals(Object other) {
        if (other.getClass() != this.getClass()) {
            throw new ClassCastException();
        }
        
        return this.x == ((ZoneCoordinates)other).x && this.y == ((ZoneCoordinates)other).y;
    }
    
    
    @Override
    public int hashCode() {
        return hashCode;
    }
    
    
    @Override
    public String toString() {
        return "[ " + x + ", " + y + " ]";
    }
}