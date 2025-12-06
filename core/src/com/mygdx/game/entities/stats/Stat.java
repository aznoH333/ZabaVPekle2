package com.mygdx.game.entities.stats;

public enum Stat {

    // combat
    Speed(0f, null),
    Damage(0f, null),
    Health(1f, null),
    MaxHealth(0f, Health);


    final float defaultValue;
    final Stat pairedWith;

    Stat(float defaultValue, Stat pairedWith) {
        this.defaultValue = defaultValue;
        this.pairedWith = pairedWith;
    }
}
