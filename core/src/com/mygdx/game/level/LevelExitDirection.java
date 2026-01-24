package com.mygdx.game.level;

public enum LevelExitDirection {
    TOP(0, -1),
    BOTTOM(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);
    
    public final int x;
    public final int y;
    
    
    LevelExitDirection(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
