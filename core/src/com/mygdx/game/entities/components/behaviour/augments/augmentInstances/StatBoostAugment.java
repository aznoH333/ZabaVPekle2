package com.mygdx.game.entities.components.behaviour.augments.augmentInstances;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.fields.FieldName;

public class StatBoostAugment extends EntityComponent {


    private final FieldName fieldName;
    private final float value;

    public StatBoostAugment(FieldName fieldName, float value) {
        super.effectDescription = (value * fieldName.getValueExponent()) + " " +fieldName.getTextName();
        this.fieldName = fieldName;
        this.value = value;
    }

    @Override
    public void onFirstAttached(Entity owner) {
        owner.addNumericStat(fieldName, value);
    }

    @Override
    public EntityComponent copy() {
        return new StatBoostAugment(fieldName, value);
    }
}
