package com.mygdx.game.world;

public enum WorldTileType {

    BRICK_HEADER_TOP("world_0001", WorldTileColor.WORLD_TOP, true),
    BRICK_HEADER_RIGHT("world_0003", WorldTileColor.WORLD_TOP, true),
    BRICK_HEADER_BOTTOM("world_0005", WorldTileColor.WORLD_TOP, true),
    BRICK_HEADER_LEFT("world_0007", WorldTileColor.WORLD_TOP, true),

    BRICK_HEADER_CORNER_LEFT_TOP("world_0008", WorldTileColor.WORLD_TOP, true),
    BRICK_HEADER_CORNER_RIGHT_TOP("world_0002", WorldTileColor.WORLD_TOP, true),
    BRICK_HEADER_CORNER_LEFT_BOTTOM("world_0004", WorldTileColor.WORLD_TOP, true),
    BRICK_HEADER_CORNER_RIGHT_BOTTOM("world_0006", WorldTileColor.WORLD_TOP, true),

    DOOR_TOP_CLOSED("world_0009", WorldTileColor.BRICKS, "world_0019", WorldTileColor.DOORS, true),
    DOOR_TOP_OPEN("world_0009", WorldTileColor.BRICKS, "world_0020", WorldTileColor.DOORS, true),
    DOOR_BOTTOM_CLOSED("world_0012", WorldTileColor.BRICKS, "world_0021", WorldTileColor.DOORS, true),

    BRICK_WALL_TOP("world_0009", WorldTileColor.BRICKS, true),
    BRICK_WALL_RIGHT("world_0010", WorldTileColor.BRICKS, true),
    BRICK_WALL_BOTTOM("world_0012", WorldTileColor.BRICKS, true),
    BRICK_WALL_LEFT("world_0011", WorldTileColor.BRICKS, true),

    BRICK_CORNER_LEFT_TOP("world_0014", WorldTileColor.BRICKS, true),
    BRICK_CORNER_RIGHT_TOP("world_0013", WorldTileColor.BRICKS, true),
    BRICK_CORNER_LEFT_BOTTOM("world_0015", WorldTileColor.BRICKS, true),
    BRICK_CORNER_RIGHT_BOTTOM("world_0016", WorldTileColor.BRICKS, true),



    FLOOR_TILE("world_0017", WorldTileColor.FLOOR, false),
    VOID("world_0018", WorldTileColor.WORLD_TOP, true);


    public final String textureName;
    public final String decorationTextureName;
    public final WorldTileColor color;
    public final WorldTileColor decorationColor;
    public final boolean isSolid;

    WorldTileType(String textureName, WorldTileColor color, boolean isSolid) {
        this.textureName = textureName;
        this.color = color;
        this.isSolid = isSolid;
        this.decorationTextureName = null;
        this.decorationColor = null;
    }

    WorldTileType(String textureName, WorldTileColor color, String decorationTextureName, WorldTileColor decorationColor, boolean isSolid) {
        this.textureName = textureName;
        this.decorationTextureName = decorationTextureName;
        this.color = color;
        this.decorationColor = decorationColor;
        this.isSolid = isSolid;
    }
}


