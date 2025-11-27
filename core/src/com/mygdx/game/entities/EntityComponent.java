package com.mygdx.game.entities;

public class EntityComponent {


    public String name = null;
    public EntityComponent() {

    }



    public void onUpdate(Entity owner) {}

    public void onCollide(Entity owner, Entity other) {}

    public void recalculateStats(Entity owner) {}


}
