package com.mygdx.game.entities.items;

public enum Quality {
    POOR("poor", "augments_0001", 0, 1, "boxes_0001"),
    COMMON("common", "augments_0002", 1, 1, "boxes_0002"),
    REFINED("refined", "augments_0003", 2, 2, "boxes_0003"),
    ELITE("elite", "augments_0004", 3, 3, "boxes_0004"),
    DIVINE("divine", "augments_0005", 4, 4, "boxes_0005");

    public final String textName;
    public final String augmentSprite;
    public final int numericValue;
    public final int averageAugmentCount;
    public final String boxSprite;

    Quality(String textName, String augmentSprite, int numericValue, int averageAugmentCount, String boxSprite) {
        this.textName = textName;
        this.augmentSprite = augmentSprite;
        this.numericValue = numericValue;
        this.averageAugmentCount = averageAugmentCount;
        this.boxSprite = boxSprite;
    }

    public static Quality getFromNumeric(int numericValue) {
        if (numericValue < 0) {
            return POOR;
        }else if (numericValue > 4) {
            return DIVINE;
        }
        return Quality.values()[numericValue];
    }



}
