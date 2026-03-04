package com.mygdx.game.entities.components.behaviour.augments.augmentInstances.shotBehaviour;

import com.mygdx.game.entities.ComponentName;
import com.mygdx.game.entities.components.behaviour.augments.ProjectileModifierAugmentInstance;
import com.mygdx.game.entities.components.behaviour.augments.projectileModifiers.Guided;

public class HomingShotAugment extends ProjectileModifierAugmentInstance {
    public HomingShotAugment() {
        super("homing bullets", new Guided(ComponentName.ENEMY));
    }
}
