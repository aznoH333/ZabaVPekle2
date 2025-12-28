package com.mygdx.game.entities.components.behaviour.augments;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.components.gui.EntityRunnable;

public class SimpleOnApplyModifierAugmentInstance extends EntityComponent {

    private final EntityRunnable applyFunction;

    public SimpleOnApplyModifierAugmentInstance(
            String effectDescription,
            EntityRunnable applyFunction
    ) {
        super.effectDescription = effectDescription;
        this.applyFunction = applyFunction;
    }

    @Override
    public void onFirstAttached(Entity owner) {
        applyFunction.run(owner);
    }

    @Override
    public EntityComponent copy() {
        return new SimpleOnApplyModifierAugmentInstance(
                super.effectDescription,
                applyFunction
        );
    }
}
