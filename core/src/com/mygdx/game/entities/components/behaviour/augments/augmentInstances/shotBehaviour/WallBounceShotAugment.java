package com.mygdx.game.entities.components.behaviour.augments.augmentInstances.shotBehaviour;

import com.mygdx.game.entities.components.behaviour.augments.ProjectileModifierAugmentInstance;
import com.mygdx.game.entities.components.behaviour.augments.projectileModifiers.WallBounce;

public class WallBounceShotAugment extends ProjectileModifierAugmentInstance {
    public WallBounceShotAugment() {
        super("ricochet bullets", new WallBounce());
    }
}
