package com.mygdx.game.entities.facades.AugmentBox.AugmentGenerationTable;

import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.BoomerangShotAugment;
import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.ScrapyMachineGunAugment;
import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.ScrapyShotGunAugment;
import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.StatBoostAugment;
import com.mygdx.game.entities.facades.AugmentBox.AugmentGenerationSpecifier;
import com.mygdx.game.entities.fields.FieldName;

import java.util.ArrayList;

public class PoorAugmentProvider implements AugmentGenerationProvider{

    @Override
    public void fillWithAugments(ArrayList<AugmentGenerationSpecifier> target) {

        // generic stats
        target.add(new AugmentGenerationSpecifier(0.75f, new StatBoostAugment(FieldName.ProjectileDamage, 0.5f)));
        target.add(new AugmentGenerationSpecifier(0.75f, new StatBoostAugment(FieldName.FireRate, -3f)));
        target.add(new AugmentGenerationSpecifier(0.75f, new StatBoostAugment(FieldName.ProjectileSpeed, 0.15f)));
        target.add(new AugmentGenerationSpecifier(0.75f, new StatBoostAugment(FieldName.ProjectileLifeTime, 12f)));
        target.add(new AugmentGenerationSpecifier(0.75f, new StatBoostAugment(FieldName.ProjectileSpread, -0.02f)));


        // weapon types
        target.add(new AugmentGenerationSpecifier(0.25f, new ScrapyMachineGunAugment()));
        target.add(new AugmentGenerationSpecifier(0.25f, new ScrapyShotGunAugment()));

        // projectile modifiers
        target.add(new AugmentGenerationSpecifier(0.25f, new BoomerangShotAugment()));

    }
}
