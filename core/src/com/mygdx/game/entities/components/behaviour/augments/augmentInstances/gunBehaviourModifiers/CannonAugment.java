package com.mygdx.game.entities.components.behaviour.augments.augmentInstances.gunBehaviourModifiers;

import com.mygdx.game.entities.components.behaviour.augments.StatModifierAugmentInstance;
import com.mygdx.game.entities.fields.FieldName;
import com.mygdx.game.entities.items.Quality;

public class CannonAugment extends StatModifierAugmentInstance {
    public CannonAugment(Quality quality) {
        super(quality.textName + " cannon");

        super.augmentMap.put(FieldName.ProjectileSpread, -0.5f);
        super.augmentMap.put(FieldName.ProjectileSpreadMultiplier, -0.1f);
        super.augmentMap.put(FieldName.DamageMultiplier, 0.5f + (0.05f * quality.numericValue));
        super.augmentMap.put(FieldName.FireRateMultiplier, 0.4f);
        super.augmentMap.put(FieldName.FireRate, 2f + (6 - quality.numericValue));
        super.augmentMap.put(FieldName.ProjectileSpeed, 0.10f);


    }
}
