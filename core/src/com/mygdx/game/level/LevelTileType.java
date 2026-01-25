package com.mygdx.game.level;

public enum LevelTileType {

    BRICK_HEADER_TOP("world_0001", LevelTileColorGroup.WORLD_TOP, true),
    BRICK_HEADER_RIGHT("world_0003", LevelTileColorGroup.WORLD_TOP, true),
    BRICK_HEADER_BOTTOM("world_0005", LevelTileColorGroup.WORLD_TOP, true),
    BRICK_HEADER_LEFT("world_0007", LevelTileColorGroup.WORLD_TOP, true),

    BRICK_HEADER_CORNER_LEFT_TOP("world_0008", LevelTileColorGroup.WORLD_TOP, true),
    BRICK_HEADER_CORNER_RIGHT_TOP("world_0002", LevelTileColorGroup.WORLD_TOP, true),
    BRICK_HEADER_CORNER_LEFT_BOTTOM("world_0004", LevelTileColorGroup.WORLD_TOP, true),
    BRICK_HEADER_CORNER_RIGHT_BOTTOM("world_0006", LevelTileColorGroup.WORLD_TOP, true),

    DOOR_TOP_CLOSED("world_0009", LevelTileColorGroup.BRICKS, "world_0019", LevelTileColorGroup.DOORS, true),
    DOOR_TOP_OPEN("world_0009", LevelTileColorGroup.BRICKS, "world_0020", LevelTileColorGroup.DOORS, true),
    DOOR_BOTTOM_CLOSED("world_0012", LevelTileColorGroup.BRICKS, "world_0021", LevelTileColorGroup.DOORS, true),
    DOOR_BOTTOM_OPEN("world_0012", LevelTileColorGroup.BRICKS, "world_0022", LevelTileColorGroup.DOORS, true),
    DOOR_LEFT_CLOSED("world_0010", LevelTileColorGroup.BRICKS, "world_0023", LevelTileColorGroup.DOORS, true),
    DOOR_LEFT_OPEN("world_0010", LevelTileColorGroup.BRICKS, "world_0024", LevelTileColorGroup.DOORS, true),
    DOOR_RIGHT_CLOSED("world_0011", LevelTileColorGroup.BRICKS, "world_0025", LevelTileColorGroup.DOORS, true),
    DOOR_RIGHT_OPEN("world_0011", LevelTileColorGroup.BRICKS, "world_0026", LevelTileColorGroup.DOORS, true),

    
    BRICK_WALL_TOP("world_0009", LevelTileColorGroup.BRICKS, true),
    BRICK_WALL_RIGHT("world_0010", LevelTileColorGroup.BRICKS, true),
    BRICK_WALL_BOTTOM("world_0012", LevelTileColorGroup.BRICKS, true),
    BRICK_WALL_LEFT("world_0011", LevelTileColorGroup.BRICKS, true),

    BRICK_CORNER_LEFT_TOP("world_0014", LevelTileColorGroup.BRICKS, true),
    BRICK_CORNER_RIGHT_TOP("world_0013", LevelTileColorGroup.BRICKS, true),
    BRICK_CORNER_LEFT_BOTTOM("world_0015", LevelTileColorGroup.BRICKS, true),
    BRICK_CORNER_RIGHT_BOTTOM("world_0016", LevelTileColorGroup.BRICKS, true),


    FLOOR_TILE("world_0017", LevelTileColorGroup.FLOOR, false),
    VOID("world_0018", LevelTileColorGroup.WORLD_TOP, true);


    public final String textureName;
    public final String decorationTextureName;
    public final LevelTileColorGroup color;
    public final LevelTileColorGroup decorationColor;
    public final boolean isSolid;

    LevelTileType(String textureName, LevelTileColorGroup color, boolean isSolid) {
        this.textureName = textureName;
        this.color = color;
        this.isSolid = isSolid;
        this.decorationTextureName = null;
        this.decorationColor = null;
    }

    LevelTileType(String textureName, LevelTileColorGroup color, String decorationTextureName, LevelTileColorGroup decorationColor, boolean isSolid) {
        this.textureName = textureName;
        this.decorationTextureName = decorationTextureName;
        this.color = color;
        this.decorationColor = decorationColor;
        this.isSolid = isSolid;
    }
}


