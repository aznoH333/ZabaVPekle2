package com.mygdx.game.entities.components.behaviour.augments.augmentInstances;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.components.behaviour.augments.projectileModifiers.Boomerang;
import com.mygdx.game.entities.fields.FieldName;

import java.util.ArrayList;

public class BoomerangShotAugment extends EntityComponent {
    public BoomerangShotAugment() {
        super.effectDescription = "boomerang shot";
    }

    @Override
    public void onFirstAttached(Entity owner) {
        ((ArrayList<EntityComponent>) owner.getField(FieldName.ProjectileComponents))
                .add(new Boomerang());
    }

    @Override
    public EntityComponent copy() {
        return new BoomerangShotAugment();
    }
}
