package com.mygdx.game.entities.components.behaviour.augments.augmentInstances.shotBehaviour;

import com.mygdx.game.entities.components.behaviour.augments.ProjectileModifierAugmentInstance;
import com.mygdx.game.entities.components.behaviour.augments.projectileModifiers.Shrapnel;
import com.mygdx.game.entities.items.Quality;

public class ShrapnelShotAugment extends ProjectileModifierAugmentInstance {

    public ShrapnelShotAugment(Quality quality) {
        super(quality.textName + " shrapnel shot", new Shrapnel(quality.numericValue + 4));
    }
}
