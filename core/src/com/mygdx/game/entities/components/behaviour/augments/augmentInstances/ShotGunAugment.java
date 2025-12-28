package com.mygdx.game.entities.components.behaviour.augments.augmentInstances;

import com.mygdx.game.entities.components.behaviour.augments.StatModifierAugmentInstance;
import com.mygdx.game.entities.fields.FieldName;
import com.mygdx.game.entities.items.Quality;

public class ShotGunAugment extends StatModifierAugmentInstance {
    public ShotGunAugment(Quality quality) {
        super(quality.textName + " shotgun");

        super.augmentMap.put(FieldName.FireRateMultiplier, 0.60f - (0.15f * quality.numericValue));
        super.augmentMap.put(FieldName.FireRate, 30f);
        super.augmentMap.put(FieldName.DamageMultiplier, -0.25f);
        super.augmentMap.put(FieldName.Damage, -(0.75f - (0.075f * quality.numericValue)));
        super.augmentMap.put(FieldName.ProjectilesPerShot, 3f + quality.numericValue);
        super.augmentMap.put(FieldName.ProjectileLifeTime, -60f);
        super.augmentMap.put(FieldName.ProjectileSpread, 0.24f);
        super.augmentMap.put(FieldName.ProjectileSpreadMultiplier, 0.05f);
    }
}
