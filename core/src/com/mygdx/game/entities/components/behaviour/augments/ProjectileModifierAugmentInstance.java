package com.mygdx.game.entities.components.behaviour.augments;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.fields.FieldName;

import java.util.ArrayList;

/**
 * A wrapper for entity components. Adds a component to owners bullets when first attached
 */
public class ProjectileModifierAugmentInstance extends EntityComponent {

    private final EntityComponent componentToAttach;

    public ProjectileModifierAugmentInstance(String effectDescription, EntityComponent componentToAttach) {
        super.effectDescription = effectDescription;
        this.componentToAttach = componentToAttach;
    }


    @Override
    public void onFirstAttached(Entity owner) {
        ((ArrayList<EntityComponent>) owner.getField(FieldName.ProjectileComponents))
            .add(componentToAttach.copy());
    }

    @Override
    public EntityComponent copy() {
        return new ProjectileModifierAugmentInstance(super.effectDescription, componentToAttach);
    }
}
