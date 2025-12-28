package com.mygdx.game.entities.components.behaviour.augments;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.fields.FieldName;

import java.util.HashMap;
import java.util.Map;

/**
 * Used to group stat modifier together.
 * Example machine guns, shotguns
 */
public class StatModifierAugmentInstance extends EntityComponent {

    protected HashMap<FieldName, Float> augmentMap;

    public StatModifierAugmentInstance(
            String effectDescription
    ) {
        super.effectDescription = effectDescription;
        augmentMap = new HashMap<>();
    }

    private StatModifierAugmentInstance(
            String effectDescription,
            HashMap<FieldName, Float> augmentMap
    ) {
        super.effectDescription = effectDescription;
        this.augmentMap = augmentMap;
    }

    @Override
    public void onFirstAttached(Entity owner) {
        if (augmentMap.isEmpty()) {
            throw new NullPointerException("Stat modifier augment instance needs to modify at least one stat");
        }

        for (Map.Entry<FieldName, Float> entry : augmentMap.entrySet()) {
            owner.addNumericStat(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public EntityComponent copy() {
        return new StatModifierAugmentInstance(
                effectDescription,
                augmentMap
        );
    }
}
