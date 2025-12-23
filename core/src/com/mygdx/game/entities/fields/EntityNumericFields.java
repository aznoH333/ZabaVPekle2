package com.mygdx.game.entities.fields;

import com.mygdx.game.utils.NumberUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class EntityNumericFields {

    private final HashMap<FieldName, Float> fields = new HashMap<>();

    public EntityNumericFields() {
        List<FieldName> fieldsToInitialize = Arrays
                .stream(FieldName.values())
                .filter((it)->it.defaultValue != null)
                .toList();

        for (FieldName fieldName : fieldsToInitialize) {
            setField(fieldName, fieldName.defaultValue);
        }
    }

    public void setField(FieldName fieldName, float value) {
        fields.put(fieldName, value);
    }

    public float getField(FieldName fieldName) {

        return fields.get(fieldName);
    }

    public void addToField(FieldName fieldName, float value) {

        float finalValue = fields.get(fieldName) + value;

        if (fieldName.minValue != null) {
            finalValue = Math.max(finalValue, fieldName.minValue);
        }

        if (fieldName.maxValue != null) {
            finalValue = Math.min(finalValue, fieldName.maxValue);
        }

        fields.put(fieldName, finalValue);
    }

    public void importValues(EntityNumericFields other) {
        for (FieldName fieldName : other.fields.keySet()) {
            setField(fieldName, other.getField(fieldName));
        }
    }
}
