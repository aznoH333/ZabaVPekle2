package com.mygdx.game.entities.fields;

public enum FieldName {

    /**
     * Movement speed of the entity
     */
    Speed,

    SpeedMultiplier,

    /**
     * Damage the entity deals to entities of the opposing team on contact
     */
    Damage,
    DamageMultiplier,

    /**
     * Health of the entity -- TODO : rewrite max values to be built into entity stat and remove maxHealth
     */
    Health,
    MaxHealth,

    /**
     * Amount of time (in frames) that the projectile will live for
     */
    ProjectileLifeTime,
    /**
     * The number of times the projectile bounced
     */
    BounceCount;


}
