package com.mygdx.game.entities.components.behaviour.augments.augmentInstances.shotBehaviour;

import com.mygdx.game.entities.components.behaviour.augments.ProjectileModifierAugmentInstance;
import com.mygdx.game.entities.components.behaviour.augments.projectileModifiers.SpinObject;

public class SpinShotAugment extends ProjectileModifierAugmentInstance {
    public SpinShotAugment() {
        super("orbital bullets", new SpinObject());
    }
}
