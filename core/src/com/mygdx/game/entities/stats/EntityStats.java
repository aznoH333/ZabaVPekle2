package com.mygdx.game.entities.stats;

import java.util.HashMap;

public class EntityStats {


    private final HashMap<Stat, EntityStat> stats = new HashMap<>();


    public EntityStats() {
        for (Stat stat : Stat.values()) {
            stats.put(stat, new EntityStat(stat));
        }
    }

    public float get(Stat stat) {
        return stats.get(stat).getValue();
    }

    public void add(Stat stat, float value) {
        if (stat.pairedWith != null) {
            add(stat.pairedWith, value);
        }

        stats.get(stat).add(value);
    }

    public void overrideDefault(Stat stat, float value, float overridePriority) {
        boolean succeeded = stats.get(stat).overrideDefault(value, overridePriority);


        if (succeeded && stat.pairedWith != null) {
            stats.get(stat).set(value);
        }

    }

    public void multiply(Stat stat, float value) {
        if (stat.pairedWith != null) {
            multiply(stat.pairedWith, value);
        }

        stats.get(stat).multiply(value);
    }

    public void reset() {
        for (Stat stat : Stat.values()) {
            stats.get(stat).resetToDefault();
        }
    }

    public void importValues(EntityStats other) {
        for (Stat stat : Stat.values()) {
            stats.get(stat).set(other.get(stat));
        }
    }
}
