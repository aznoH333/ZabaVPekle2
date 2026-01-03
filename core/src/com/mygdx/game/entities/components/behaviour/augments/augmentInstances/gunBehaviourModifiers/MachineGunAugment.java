package com.mygdx.game.entities.components.behaviour.augments.augmentInstances.gunBehaviourModifiers;

import com.mygdx.game.entities.components.behaviour.augments.StatModifierAugmentInstance;
import com.mygdx.game.entities.fields.FieldName;
import com.mygdx.game.entities.items.Quality;

public class MachineGunAugment extends StatModifierAugmentInstance {
    public MachineGunAugment(Quality quality) {
        super(quality.textName + " machine gun");

        super.augmentMap.put(FieldName.FireRateMultiplier, -0.1f);
        super.augmentMap.put(FieldName.FireRate, -7f * quality.rarityAdjustedNumericValue);
        super.augmentMap.put(FieldName.DamageMultiplier, -(0.20f - (0.07f * quality.numericValue)));
        super.augmentMap.put(FieldName.ProjectileSpread, 0.24f - (0.02f * quality.numericValue));
        super.augmentMap.put(FieldName.ProjectileSpreadMultiplier, 0.05f);
    }
}
