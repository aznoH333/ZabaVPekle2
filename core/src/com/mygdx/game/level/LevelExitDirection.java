package com.mygdx.game.level;

public enum LevelExitDirection {
    TOP(0, -1, LevelTileType.DOOR_BOTTOM_OPEN, LevelTileType.DOOR_BOTTOM_CLOSED),
    BOTTOM(0, 1, LevelTileType.DOOR_TOP_OPEN, LevelTileType.DOOR_TOP_CLOSED),
    LEFT(-1, 0, LevelTileType.DOOR_RIGHT_OPEN, LevelTileType.DOOR_RIGHT_CLOSED),
    RIGHT(1, 0, LevelTileType.DOOR_LEFT_OPEN, LevelTileType.DOOR_LEFT_CLOSED);
    
    public final int x;
    public final int y;
    public final LevelTileType openDoorTile;
    public final LevelTileType closedDoorTile;
    
    
    LevelExitDirection(int x, int y, LevelTileType openDoorTile, LevelTileType closedDoorTile) {
        this.x = x;
        this.y = y;
        this.openDoorTile = openDoorTile;
        this.closedDoorTile = closedDoorTile;
    }
}
