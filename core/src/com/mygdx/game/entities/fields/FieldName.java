package com.mygdx.game.entities.fields;

public enum FieldName {

    // combat


    /**
     * Movement speed of the entity
     */
    Speed(0f, null),

    SpeedMultiplier(1f, null),

    /**
     * Damage the entity deals to entities of the opposing team on contact
     */
    Damage(0f, null),
    DamageMultiplier(1f, null),

    /**
     * Health of the entity -- TODO : rewrite max values to be built into entity stat and remove maxHealth
     */
    Health(1f, null),
    MaxHealth(1f, Health),

    /**
     * Amount of time (in frames) that the projectile will live for
     */
    ProjectileLifeTime(120f, null),
    /**
     * The number of times the projectile bounced
     */
    BounceCount(1f, null);


    public final float defaultValue;
    public final FieldName pairedWith;

    FieldName(float defaultValue, FieldName pairedWith) {
        this.defaultValue = defaultValue;
        this.pairedWith = pairedWith;
    }
}
