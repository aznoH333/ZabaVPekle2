package com.mygdx.game.entities.components.behaviour.augments.statChangers;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.fields.FieldName;

public class StatBoost extends EntityComponent {


    private final FieldName fieldName;
    private final float value;
    public StatBoost(FieldName fieldName, float value) {
        super.effectDescription = fieldName.name() + " boost";
        this.fieldName = fieldName;
        this.value = value;
    }

    @Override
    public void onFirstAttached(Entity owner) {
        owner.addNumericStat(fieldName, value);
    }

    @Override
    public EntityComponent copy() {
        return new StatBoost(fieldName, value);
    }
}
