package com.mygdx.game.entities.stats;

public enum Stat {

    // combat


    /** Movement speed of the entity */
    Speed(0f, null),

    /** Damage the entity deals to entities of the opposing team on contact */
    Damage(0f, null),

    /** Health of the entity -- TODO : rewrite max values to be built into entity stat and remove maxHealth*/
    Health(1f, null),
    MaxHealth(1f, Health),



    /** Amount of time (in frames) that the projectile will live for */
    ProjectileLifeTime(120f, null),
    /** The number of times the projectile bounced*/
    BounceCount(1f, null);


    public final float defaultValue;
    public final Stat pairedWith;

    Stat(float defaultValue, Stat pairedWith) {
        this.defaultValue = defaultValue;
        this.pairedWith = pairedWith;
    }
}
