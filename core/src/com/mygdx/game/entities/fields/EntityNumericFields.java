package com.mygdx.game.entities.fields;

import java.util.HashMap;

public class EntityNumericFields {

    private final HashMap<FieldName, Float> fields = new HashMap<>();


    public void setField(FieldName fieldName, float value) {
        fields.put(fieldName, value);
    }

    public float getField(FieldName fieldName) {

        return fields.get(fieldName);
    }

    public void addToField(FieldName fieldName, float value) {
        fields.put(fieldName, fields.get(fieldName) + value);
    }

    public void importValues(EntityNumericFields other) {
        for (FieldName fieldName : other.fields.keySet()) {
            setField(fieldName, other.getField(fieldName));
        }
    }
}
