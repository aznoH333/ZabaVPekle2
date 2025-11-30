package com.mygdx.game.entities;

import com.mygdx.game.SpriteManager;
import com.mygdx.game.WorldManager;

import java.util.ArrayList;
import java.util.Objects;

public class Entity {
    private static SpriteManager spriteManager = SpriteManager.getInstance();
    private static WorldManager worldManager = WorldManager.getInstance();


    public String sprite;
    public float x;
    public float y;
    public float width = 16f;
    public float height = 16f;
    private final ArrayList<EntityComponent> components = new ArrayList<>();
    public float speed;
    public float health = 1f;
    public float maxHealth = 1f;
    public float damage = 0f;
    public int invincibilityTimer = 0;
    public int invincibilityTimerMax = 30;
    public EntityTeam team = EntityTeam.NONE;
    public boolean wantsToLive = true;
    public float xVelocity = 0f;
    public float yVelocity = 0f;
    public float lastFrameXVelocity = 0f;
    public float lastFrameYVelocity = 0f;


    // appearance
    public float spriteRotation = 0f;
    public float r = 1f;
    public float g = 1f;
    public float b = 1f;
    public float a = 1f;
    public float scaleX = 1f;
    public float scaleY = 1f;
    public boolean flipX = false;
    public boolean flipY = false;
    public boolean flipWithMoveDirection = false;


    public Entity() {
        resetStats();
    }


    public void update() {

        for (EntityComponent component : this.components) {
            component.onUpdate(this);
        }

        // move
        boolean collidedWithWorld = false;
        if (worldManager.isSpaceEmpty(x + xVelocity, y, width, height)) {
            x += xVelocity;
            lastFrameXVelocity = xVelocity;
        }else {
            collidedWithWorld = true;
            lastFrameXVelocity = 0f;
        }

        if (worldManager.isSpaceEmpty(x, y + yVelocity, width, height)) {
            y += yVelocity;
            lastFrameYVelocity = yVelocity;
        }else {
            collidedWithWorld = true;
            lastFrameYVelocity = 0f;
        }

        xVelocity = 0;
        yVelocity = 0;

        // world collision
        if (collidedWithWorld) {
            for (EntityComponent c : components) {
                c.onWorldCollide(this);
            }
        }



        // draw
        spriteManager.drawSprite(this.sprite, x, y, scaleX, scaleY, flipX, flipY, spriteRotation, r, g, b, a);

        // invincibility
        if (this.invincibilityTimer > 0) {
            this.invincibilityTimer--;
        }
    }


    public void onCollide(Entity other) {
        for (EntityComponent component : this.components) {
            component.onCollide(this, other);
        }
        // take damage
        if (this.invincibilityTimer == 0 && other.team.isAggressiveAgainst(this.team) && other.damage != 0f) {
            this.health -= other.damage;
            this.invincibilityTimer = this.invincibilityTimerMax;

            if (this.health <= 0f) {
                this.commitSudoku();
            }
        }
    }


    private void resetStats() {
        setHealth(1f);
        damage = 0f;
        speed = 1f;
        flipWithMoveDirection = false;
    }


    public void walk(float x, float y) {
        xVelocity += x * speed;
        yVelocity += y * speed;

        if (flipWithMoveDirection) {
            if (xVelocity < -0.5f) {
                flipX = true;
            }else if (xVelocity > 0.5f) {
                flipX = false;
            }
        }
    }

    public void goInDirection(float rotationRad, float speedMultiplier) {
        xVelocity += (float) (Math.cos(rotationRad) * speedMultiplier * speed);
        yVelocity += (float) (Math.sin(rotationRad) * speedMultiplier * speed);

        if (flipWithMoveDirection) {
            if (xVelocity < -0.5f) {
                flipX = true;
            }else if (xVelocity > 0.5f) {
                flipX = false;
            }
        }
    }

    public boolean hasComponent(String name) {
        return this.components.stream().anyMatch((a)-> Objects.equals(a.name, name));
    }

    public EntityComponent getComponentByName(String name) {
        return this.components.stream().filter((a)-> Objects.equals(a.name, name)).findFirst().get();

    }

    public boolean collidesWithEntity(Entity other) {
        float width = this.width / 2.0f;
        float height = this.height / 2.0f;

        float otherWidth = other.width / 2.0f;
        float otherHeight = other.height / 2.0f;

        return x - width < other.x + otherWidth &&
               x + width > other.x - otherWidth &&
               y - height < other.y + otherHeight &&
               y + height > other.y - otherHeight;
    }



    // setters
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



    public Entity setHealth(float maxHealth) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        return this;
    }

    public Entity setDamage(float damage) {
        this.damage = damage;
        return this;
    }

    public Entity setTeam(EntityTeam team) {
        this.team = team;
        return this;
    }


    public Entity addComponent(EntityComponent component) {
        this.components.add(component);
        component.onEntityAttach(this);

        resetStats();
        for (EntityComponent c : components) {
            c.recalculateStats(this);
        }

        return this;
    }

    public void commitSudoku() {
        this.wantsToLive = false;
    }
}
