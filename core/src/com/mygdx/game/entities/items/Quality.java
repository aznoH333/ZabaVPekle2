package com.mygdx.game.entities.items;

public enum Quality {
    POOR("poor"),
    COMMON("common"),
    REFINED("refined"),
    ELITE("elite"),
    DIVINE("divine");

    public final String textName;

    Quality(String textName) {
        this.textName = textName;
    }

}
