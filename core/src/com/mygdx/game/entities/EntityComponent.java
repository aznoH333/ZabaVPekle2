package com.mygdx.game.entities;

public class EntityComponent {


    public String name = null;
    public EntityComponent() {

    }


    public void onEntityAttach(Entity owner) {}

    public void onUpdate(Entity owner) {}

    public void onCollide(Entity owner, Entity other) {}

    public void onWorldCollide(Entity owner) {}

    public void recalculateStats(Entity owner) {}




}
