package com.mygdx.game.entities.fields;

import com.mygdx.game.utils.NumberUtils;

public enum FieldName {
    // speed
    Speed,
    SpeedMultiplier(0f, 0f, null),

    // damage
    Damage(0f, 0f, null),
    DamageMultiplier(1f, 0.01f, null),

    // health
    Health(1f, null, null),
    MaxHealth(null, 1f, null),

    // projectile stuff
    ProjectileLifeTime,
    /** number of times the projectile can bounce */
    BounceCount(0f, 0f, null),
    ProjectileDamage,
    ProjectileSpeed(0.1f, 0f, 10f),
    FireRate(1f, 1f, 999f),
    ProjectileSpread(0f, 0f, NumberUtils.PI),
    ProjectilesPerShot(1f, 1f, null),
    ProjectileSprite;



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
