package com.mygdx.game.world;

public enum WorldTileType {

    BRICK_HEADER_TOP("world_0001", WorldTileColor.WORLD_TOP),
    BRICK_HEADER_RIGHT("world_0003", WorldTileColor.WORLD_TOP),
    BRICK_HEADER_BOTTOM("world_0005", WorldTileColor.WORLD_TOP),
    BRICK_HEADER_LEFT("world_0007", WorldTileColor.WORLD_TOP),

    BRICK_HEADER_CORNER_LEFT_TOP("world_0008", WorldTileColor.WORLD_TOP),
    BRICK_HEADER_CORNER_RIGHT_TOP("world_0002", WorldTileColor.WORLD_TOP),
    BRICK_HEADER_CORNER_LEFT_BOTTOM("world_0004", WorldTileColor.WORLD_TOP),
    BRICK_HEADER_CORNER_RIGHT_BOTTOM("world_0006", WorldTileColor.WORLD_TOP),


    BRICK_WALL_TOP("world_0009", WorldTileColor.BRICKS),
    BRICK_WALL_RIGHT("world_0010", WorldTileColor.BRICKS),
    BRICK_WALL_BOTTOM("world_0012", WorldTileColor.BRICKS),
    BRICK_WALL_LEFT("world_0011", WorldTileColor.BRICKS),

    BRICK_CORNER_LEFT_TOP("world_0014", WorldTileColor.BRICKS),
    BRICK_CORNER_RIGHT_TOP("world_0013", WorldTileColor.BRICKS),
    BRICK_CORNER_LEFT_BOTTOM("world_0015", WorldTileColor.BRICKS),
    BRICK_CORNER_RIGHT_BOTTOM("world_0016", WorldTileColor.BRICKS),



    FLOOR_TILE("world_0017", WorldTileColor.FLOOR),
    VOID("world_0018", WorldTileColor.WORLD_TOP);


    public final String textureName;
    public final WorldTileColor color;

    WorldTileType(String textureName, WorldTileColor color) {
        this.textureName = textureName;
        this.color = color;
    }
}


