package com.mygdx.game.entities.components.visual;

public enum LegsWithHatType {
    DEBUG("legs", 1, 2, 8, 9),
    PLAYER("player", 1, 2, 7, 1),
    ENEMY_MEDIUM("enemy_body", 1, 2, 7, 1);


    public final String bodyBaseSprite;
    public final int idleIndex;
    public final int walkStartIndex;
    public final int walkEndIndex;
    public final int hurtIndex;


    LegsWithHatType(String bodyBaseSprite, int idleIndex, int walkStartIndex, int walkEndIndex, int hurtIndex) {
        this.bodyBaseSprite = bodyBaseSprite;
        this.idleIndex = idleIndex;
        this.walkStartIndex = walkStartIndex;
        this.walkEndIndex = walkEndIndex;
        this.hurtIndex = hurtIndex;
    }
}
