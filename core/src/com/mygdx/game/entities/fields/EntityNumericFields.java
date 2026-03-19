package com.mygdx.game.entities.fields;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class EntityNumericFields {

    private final HashMap<FieldName, Float> fields = new HashMap<>();

    public EntityNumericFields() {
        List<FieldName> fieldsToInitialize = new ArrayList<>();
        for (FieldName it : FieldName.values()) {
            if (it.defaultValue != null) {
                fieldsToInitialize.add(it);
            }
        }

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

    /**
     * Ensures that field is initialized.
     * If the field has no value initializes it with the passed value.
     * Does nothing if field is already initialized.
     *
     * @param fieldName - field specifier
     * @param value     - the value to initialize the field with
     */
    public void initializeValue(FieldName fieldName, float value) {
        if (fields.containsKey(fieldName)) {
            return;
        }

        fields.put(fieldName, value);
    }
}
