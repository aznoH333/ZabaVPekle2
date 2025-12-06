package com.mygdx.game.entities.stats;

public class EntityStat {

    private float value;
    private float defaultValue;
    private float multiplier;
    private float overridePriority;
    private final Stat stat;

    public EntityStat(Stat stat) {
        this.defaultValue = stat.defaultValue;
        value = 0f;
        multiplier = 1f;
        overridePriority = 0f;
        this.stat = stat;
    }

    public float getValue() {
        return (defaultValue + value) * multiplier;
    }

    public void add(float value) {
        this.value += value;
    }

    public void multiply(float value) {
        multiplier += value;
    }

    public boolean overrideDefault(float value, float overridePriority) {
        if (overridePriority > this.overridePriority) {
            this.defaultValue = value;
            this.overridePriority = overridePriority;
            return true;
        }
        return false;

    }

    public void set(float value) {
        this.value = value;
    }

    public void resetToDefault() {
        defaultValue = stat.defaultValue;
        overridePriority = 0f;
        value = 0f;
        multiplier = 1f;
    }



}
