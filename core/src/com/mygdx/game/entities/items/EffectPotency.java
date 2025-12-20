package com.mygdx.game.entities.items;

public enum EffectPotency {
    NOT_QUALIFIED(0, ""),
    TINY(1, "tiny"),
    SMALL(2, "small"),
    MODERATE(4, "moderate"),
    SIGNIFICANT(8, "significant"),
    HIGH(14, "high"),
    EXTREME(20, "extreme");

    public final int quantifier;
    public final String textName;

    EffectPotency(int quantifier, String textName) {
        this.quantifier = quantifier;
        this.textName = textName;
    }

    public static EffectPotency getPotencyBasedOnValue(int value) {

        EffectPotency potencyToReturn = NOT_QUALIFIED;
        for (EffectPotency potency : EffectPotency.values()) {
            if (potencyToReturn.quantifier >= value) {
                break;
            }
            potencyToReturn = potency;

        }

        return potencyToReturn;
    }
}
