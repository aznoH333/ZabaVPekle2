package com.mygdx.game.entities.components.behaviour.augments.augmentInstances.shotBehaviour;

import com.mygdx.game.entities.components.behaviour.augments.ProjectileModifierAugmentInstance;
import com.mygdx.game.entities.components.behaviour.augments.projectileModifiers.SineTravel;

public class SineTravelShotAugment extends ProjectileModifierAugmentInstance {
    public SineTravelShotAugment() {
        super("wave shot", new SineTravel());
    }
}
