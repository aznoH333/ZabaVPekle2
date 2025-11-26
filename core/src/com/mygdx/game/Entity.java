package com.mygdx.game;

import java.util.ArrayList;

public class Entity {
    private static SpriteManager spriteManager = SpriteManager.getInstance();


    public String sprite;
    public float x;
    public float y;
    private final ArrayList<EntityComponent> components = new ArrayList<>();


    public Entity() {

    }


    public void update() {

        for (EntityComponent component : this.components) {
            component.onUpdate(this);
        }

        spriteManager.drawSprite(this.sprite, x, y);
    }

    public Entity setX(float x) {
        this.x = x;
        return this;
    }

    public Entity setY(float y) {
        this.y = y;
        return this;
    }

    public Entity setSprite(String sprite) {
        this.sprite = sprite;
        return this;
    }

    public Entity addComponent(EntityComponent component) {
        this.components.add(component);
        return this;
    }
}
