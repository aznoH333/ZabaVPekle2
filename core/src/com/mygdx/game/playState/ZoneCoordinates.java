package com.mygdx.game.playState;

public class ZoneCoordinates {
    public final int x;
    public final int y;
    
    public ZoneCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public boolean equals(Object other) {
        if (other.getClass() != this.getClass()) {
            throw new ClassCastException();
        }
        
        return this.x == ((ZoneCoordinates)other).x && this.y == ((ZoneCoordinates)other).y;
    }
    
}