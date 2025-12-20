package com.mygdx.game.entities.items;

public enum Quality {
    POOR("poor", "augments_0001"),
    COMMON("common", "augments_0002"),
    REFINED("refined", "augments_0003"),
    ELITE("elite", "augments_0004"),
    DIVINE("divine", "augments_0005");

    public final String textName;
    public final String augmentSprite;

    Quality(String textName, String augmentSprite) {
        this.textName = textName;
        this.augmentSprite = augmentSprite;
    }


}
