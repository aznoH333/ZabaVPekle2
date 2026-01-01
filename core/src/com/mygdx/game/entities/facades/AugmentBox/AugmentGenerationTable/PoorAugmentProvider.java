package com.mygdx.game.entities.facades.AugmentBox.AugmentGenerationTable;

import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.StatBoostAugment;
import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.gunBehaviourModifiers.MachineGunAugment;
import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.gunBehaviourModifiers.ShotGunAugment;
import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.handModifiers.DoubleHank;
import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.handModifiers.OctoHank;
import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.handModifiers.SideHank;
import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.handModifiers.TripleHank;
import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.shotBehaviour.BoomerangShotAugment;
import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.shotBehaviour.ShrapnelShotAugment;
import com.mygdx.game.entities.facades.AugmentBox.AugmentGenerationSpecifier;
import com.mygdx.game.entities.fields.FieldName;
import com.mygdx.game.entities.items.Quality;

import java.util.ArrayList;

public class PoorAugmentProvider implements AugmentGenerationProvider {

    @Override
    public void fillWithAugments(ArrayList<AugmentGenerationSpecifier> target) {

        // generic stats
        target.add(new AugmentGenerationSpecifier(0.75f, new StatBoostAugment(FieldName.ProjectileDamage, 0.5f)));
        target.add(new AugmentGenerationSpecifier(0.75f, new StatBoostAugment(FieldName.FireRate, -3f)));
        target.add(new AugmentGenerationSpecifier(0.75f, new StatBoostAugment(FieldName.ProjectileSpeed, 0.15f)));
        target.add(new AugmentGenerationSpecifier(0.75f, new StatBoostAugment(FieldName.ProjectileLifeTime, 12f)));
        target.add(new AugmentGenerationSpecifier(0.75f, new StatBoostAugment(FieldName.ProjectileSpread, -0.02f)));


        // weapon types
        target.add(new AugmentGenerationSpecifier(0.25f, new MachineGunAugment(Quality.POOR)));
        target.add(new AugmentGenerationSpecifier(0.25f, new ShotGunAugment(Quality.POOR)));

        // projectile modifiers
        target.add(new AugmentGenerationSpecifier(0.25f, new BoomerangShotAugment()));
        target.add(new AugmentGenerationSpecifier(0.25f, new ShrapnelShotAugment(Quality.POOR)));

        // hand modifiers
        target.add(new AugmentGenerationSpecifier(0.15f, new DoubleHank()));
        target.add(new AugmentGenerationSpecifier(0.15f, new TripleHank()));
        target.add(new AugmentGenerationSpecifier(0.15f, new OctoHank()));
        target.add(new AugmentGenerationSpecifier(0.15f, new SideHank()));


    }
}
