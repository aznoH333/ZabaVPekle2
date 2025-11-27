package com.mygdx.game.entities;

import com.mygdx.game.SpriteManager;

import java.util.ArrayList;
import java.util.Objects;

public class Entity {
    private static SpriteManager spriteManager = SpriteManager.getInstance();


    public String sprite;
    public float x;
    public float y;
    public float width = 32f;
    public float height = 32f;
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


    public void onCollide(Entity other) {
        for (EntityComponent component : this.components) {
            component.onCollide(this, other);
        }
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

    public Entity setWidth(float width) {
        this.width = width;
        return this;
    }

    public Entity setHeight(float height) {
        this.height = height;
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

    public boolean hasComponent(String name) {
        return this.components.stream().anyMatch((a)-> Objects.equals(a.name, name));
    }

    public boolean collidesWithEntity(Entity other) {
        float width = this.width / 2.0f;
        float height = this.height / 2.0f;

        float otherWidth = other.width / 2.0f;
        float otherHeight = other.height / 2.0f;

        return x - width > other.x + otherWidth &&
               x + width < other.x - otherWidth &&
               y - height > other.y + otherHeight &&
               y + height < other.y - otherHeight;
    }
}
