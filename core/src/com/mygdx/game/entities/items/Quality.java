package com.mygdx.game.entities.items;

public enum Quality {
    POOR("poor", "augments_0001", 0, 1),
    COMMON("common", "augments_0002", 1, 1),
    REFINED("refined", "augments_0003", 2, 2),
    ELITE("elite", "augments_0004", 3, 3),
    DIVINE("divine", "augments_0005", 4, 4);

    public final String textName;
    public final String augmentSprite;
    public final int numericValue;
    public final int averageAugmentCount;

    Quality(String textName, String augmentSprite, int numericValue, int averageAugmentCount) {
        this.textName = textName;
        this.augmentSprite = augmentSprite;
        this.numericValue = numericValue;
        this.averageAugmentCount = averageAugmentCount;
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
