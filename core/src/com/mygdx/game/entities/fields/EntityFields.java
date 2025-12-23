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

    public void importValues(EntityFields other) {
        for (FieldName fieldName : other.fields.keySet()) {
            Object field = other.getField(fieldName);

            setField(fieldName, field);
        }
    }
}
