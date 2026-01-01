package com.mygdx.game.entities.fields;

import java.util.HashMap;

public class EntityFields {
    private HashMap<FieldName, Object> fields = new HashMap<>();

    public <T> void setField(FieldName fieldName, T value) {
        this.fields.put(fieldName, value);
    }

    public <T> T getField(FieldName fieldName) {
        return (T) fields.get(fieldName);
    }

    /**
     * Ensures that field is initialized.
     * If the field has no value initializes it with the passed value.
     * Does nothing if field is already initialized.
     *
     * @param fieldName - field specifier
     * @param value     - the value to initialize the field with
     * @param <T>       - type to store
     */
    public <T> void initializeValue(FieldName fieldName, T value) {
        if (this.fields.containsKey(fieldName)) {
            return;
        }

        this.fields.put(fieldName, value);
    }

    public void importValues(EntityFields other) {
        for (FieldName fieldName : other.fields.keySet()) {
            Object field = other.getField(fieldName);

            setField(fieldName, field);
        }
    }

}
