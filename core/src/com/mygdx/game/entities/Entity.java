package com.mygdx.game.entities;

import com.mygdx.game.SpriteManager;

import java.util.ArrayList;

public class Entity {
    private static SpriteManager spriteManager = SpriteManager.getInstance();


    public String sprite;
    public float x;
    public float y;
    private final ArrayList<EntityComponent> components = new ArrayList<>();
    public float speed;



    public Entity() {
        resetStats();
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

        resetStats();
        for (EntityComponent c : components) {
            c.recalculateStats(this);
        }

        return this;
    }

    private void resetStats() {
        this.speed = 1f;
    }


    public void walk(float x, float y) {
        this.x += x * speed;
        this.y += y * speed;
    }

    public void goInDirection(float rotationRad, float speedMultiplier) {
        this.x += (float) (Math.cos(rotationRad) * speedMultiplier * speed);
        this.y += (float) (Math.sin(rotationRad) * speedMultiplier * speed);
    }
}
