package com.mygdx.game.entities.components.behaviour.augments.augmentInstances;

import com.mygdx.game.entities.components.behaviour.augments.ProjectileModifierAugmentInstance;
import com.mygdx.game.entities.components.behaviour.augments.projectileModifiers.Boomerang;


public class BoomerangShotAugment extends ProjectileModifierAugmentInstance {

    public BoomerangShotAugment() {
        super("boomerang shots", new Boomerang());
    }
}
