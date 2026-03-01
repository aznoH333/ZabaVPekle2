package com.mygdx.game.entities.fields;

import com.mygdx.game.utils.types.NumberUtils;

public enum FieldName {
    // speed
    Speed(0f, 0f, 10f),
    SpeedMultiplier(1f, 0f, null),

    // damage
    Damage(0f, 0f, null),
    DamageMultiplier(1f, 0.01f, null),

    // health
    Health(1f, null, null),
    MaxHealth(null, 1f, null),

    // projectile stuff
    /**
     * How many frames will the projectile live for.
     */
    RemainingProjectileLifeTime,
    /**
     * Used as default RemainingProjectileLifeTime when spawning a new projectile
     */
    ProjectileLifeTime(120f, 30f, 1000f),

    /**
     * number of times the projectile can bounce
     */
    BounceCount(1f, null, null),
    ProjectileDamage,
    ProjectileSpeed(0.1f, 0f, 10f),

    FireRate(25f, 1f, 120f),
    FireRateMultiplier(1f, 0.05f, 4f),

    ProjectileSpread(0.25f, 0.01f, NumberUtils.PI),
    ProjectileSpreadMultiplier(1f, 0f, 2f),
    ProjectilesPerShot(1f, 1f, null),

    /** Projectile visuals */
    ProjectileSprite,
    ProjectileColor,


    /**
     * Used to prevent some entities from moving
     */
    SuspendMovement,
    /**
     * Instance of the entities gun.
     */
    Gun,
    /**
     * an arraylist containing a list of projectile origins
     */
    Guns,
    /**
     * an arraylist containing a list of components assigned to each projectile
     */
    ProjectileComponents,


    /**
     * The entity's target entity. (The thing that enemies chase. Will most often be a reference to the player)
     */
    Target;

    public final Float defaultValue;
    public final Float minValue;
    public final Float maxValue;

    FieldName() {
        this.defaultValue = null;
        this.maxValue = null;
        this.minValue = null;
    }

    FieldName(Float defaultValue, Float minValue, Float maxValue) {
        this.defaultValue = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }
}
