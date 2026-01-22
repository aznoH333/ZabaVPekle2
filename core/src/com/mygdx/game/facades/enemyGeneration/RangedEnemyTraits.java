package com.mygdx.game.facades.enemyGeneration;

import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.StatBoostAugment;
import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.gunBehaviourModifiers.MachineGunAugment;
import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.gunBehaviourModifiers.ShotGunAugment;
import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.handModifiers.DoubleHank;
import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.handModifiers.OctoHank;
import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.handModifiers.TripleHank;
import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.shotBehaviour.BoomerangShotAugment;
import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.shotBehaviour.ShrapnelShotAugment;
import com.mygdx.game.entities.components.gui.EntityRunnable;
import com.mygdx.game.entities.fields.FieldName;
import com.mygdx.game.entities.items.Quality;
import com.mygdx.game.utils.Trait;

import java.util.ArrayList;

public class RangedEnemyTraits {
    public static ArrayList<Trait<EntityRunnable>> traits = new ArrayList<>();

    static {

        // shot modifiers
        traits.add(new Trait<>(0.3f, 10f, (owner) -> {
            owner.addComponent(new ShrapnelShotAugment(Quality.COMMON));
        }));
        traits.add(new Trait<>(0.3f, 30f, (owner) -> {
            owner.addComponent(new ShrapnelShotAugment(Quality.ELITE));
        }));
        traits.add(new Trait<>(0.3f, 10f, (owner) -> {
            owner.addComponent(new BoomerangShotAugment());
        }));


        // hanks
        traits.add(new Trait<>(0.25f, 10f, (owner -> {
            owner.addComponent(new TripleHank());
        })));
        traits.add(new Trait<>(0.25f, 10f, (owner -> {
            owner.addComponent(new DoubleHank());
        })));
        traits.add(new Trait<>(0.25f, 20f, (owner -> {
            owner.addComponent(new OctoHank());
        })));

        // attack modifiers
        traits.add(new Trait<>(0.25f, 5f, (owner -> {
            owner.addComponent(new ShotGunAugment(Quality.COMMON));
        })));
        traits.add(new Trait<>(0.25f, 15f, (owner -> {
            owner.addComponent(new ShotGunAugment(Quality.ELITE));
        })));
        traits.add(new Trait<>(0.25f, 5f, (owner -> {
            owner.addComponent(new MachineGunAugment(Quality.COMMON));
        })));
        traits.add(new Trait<>(0.25f, 15f, (owner -> {
            owner.addComponent(new MachineGunAugment(Quality.ELITE));
        })));


        // stat modifiers
        traits.add(new Trait<>(0.25f, 1f, (owner -> {
            owner.addComponent(new StatBoostAugment(FieldName.FireRate, -15f));
        })));
        traits.add(new Trait<>(0.25f, 1f, (owner -> {
            owner.addComponent(new StatBoostAugment(FieldName.ProjectileSpeed, 0.1f));
        })));
        traits.add(new Trait<>(0.25f, 0.5f, (owner -> {
            owner.addComponent(new StatBoostAugment(FieldName.ProjectileLifeTime, 15f));
        })));


    }
}
