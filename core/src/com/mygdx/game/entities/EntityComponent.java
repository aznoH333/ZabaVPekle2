package com.mygdx.game.entities;

import com.mygdx.game.entities.items.EffectPotency;

public class EntityComponent {


    public ComponentName name = ComponentName.NONE;
    public String effectDescription = null;
    public EffectPotency potency = EffectPotency.NOT_QUALIFIED;
    public Entity owner = null;

    public int componentCountLimit = -1;

    public EntityComponent() {

    }

    public void onComponentAttached(Entity owner) {
    }

    public void onUpdate(Entity owner) {
    }

    public void onCollide(Entity owner, Entity other) {
    }

    public void onWorldCollide(Entity owner) {
    }

    public void onFirstAttached(Entity owner) {
    }

    public void onTakeDamage(Entity owner, float damageAmount) {
    }

    public void onSudoku(Entity owner) {
    }

    public void onCleanUp(Entity owner) {
    }

    public void onPlacedInWorld(Entity owner) {
    }

    public EntityComponent copy() {
        throw new RuntimeException("Copy not supported for " + getClass().getName());
    }

}
