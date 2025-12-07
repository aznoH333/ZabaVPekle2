package com.mygdx.game.entities;

import com.mygdx.game.NumberUtils;
import com.mygdx.game.drawing.DrawingCommand;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.drawing.SpriteManager;
import com.mygdx.game.WorldManager;
import com.mygdx.game.entities.stats.EntityStats;
import com.mygdx.game.entities.stats.Stat;

import java.util.ArrayList;
import java.util.Objects;

public class Entity {
    private static SpriteManager spriteManager = SpriteManager.getInstance();
    private static WorldManager worldManager = WorldManager.getInstance();


    public String sprite;
    public float x;
    public float y;
    public float spriteOffsetX = 0f;
    public float spriteOffsetY = 0f;
    public float width = 16f;
    public float height = 16f;
    private final ArrayList<EntityComponent> components = new ArrayList<>();
    public DrawingLayer drawingLayer = DrawingLayer.WORLD;
    public boolean triggerInvincibility = true;
    public EntityStats stats = new EntityStats();

    public int invincibilityTimer = 0;
    public int invincibilityTimerMax = 30;
    public int knockBackTimer = 0;
    public int knockBackTimerMax = 20;
    public EntityTeam team = EntityTeam.NONE;
    public boolean wantsToLive = true;
    public float xVelocity = 0f;
    public float yVelocity = 0f;
    public float lastFrameXVelocity = 0f;
    public float lastFrameYVelocity = 0f;


    // combat
    public float knockBackMultiplier = 0f;
    public float knockBackDirection = 0f;
    public float knockBackSpeed = 0f;
    public boolean canBeDamaged = false;

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


        // knock back movement
        if (shouldApplyKnockBack()) {
            xVelocity = (float) (Math.cos(knockBackDirection) * knockBackSpeed);
            yVelocity = (float) (Math.sin(knockBackDirection) * knockBackSpeed);
            knockBackSpeed *= 0.9f;
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
        spriteManager.drawSprite(
                new DrawingCommand(sprite, x + spriteOffsetX, y + spriteOffsetY)
                        .setWidth(scaleX)
                        .setHeight(scaleY)
                        .setFlipHorizontally(flipX)
                        .setFlipVertically(flipY)
                        .setRotationRad(spriteRotation)
                        .setR(r)
                        .setG(g)
                        .setB(b)
                        .setA(a),
                drawingLayer
        );

        // invincibility
        if (this.invincibilityTimer > 0) {
            this.invincibilityTimer--;
        }
        if (knockBackTimer > 0) {
            this.knockBackTimer--;
        }
    }


    public void onCollide(Entity other) {
        for (EntityComponent component : this.components) {
            component.onCollide(this, other);
        }
        // take damage
        if (this.invincibilityTimer == 0 && other.team.isAggressiveAgainst(this.team) && other.stats.get(Stat.Damage) != 0f && canBeDamaged) {
            this.stats.add(Stat.Health, -other.stats.get(Stat.Damage));


            if (other.triggerInvincibility) {
                this.invincibilityTimer = this.invincibilityTimerMax;
            }
            this.knockBackTimer = this.knockBackTimerMax;



            if (this.stats.get(Stat.Health) <= 0f) {
                this.commitSudoku();


            }else {
                for (EntityComponent c : components) {
                    c.onTakeDamage(this, other.stats.get(Stat.Damage));
                }
            }

            // knock back
            if (other.knockBackMultiplier > 0f) {
                knockBackDirection = NumberUtils.directionToward(other.x, other.y, x, y);
                knockBackSpeed = other.knockBackMultiplier;
            }
        }
    }


    private void resetStats() {
        stats.reset();
    }


    public void walk(float x, float y) {
        if (shouldApplyKnockBack()) {
            return;
        }

        xVelocity += x * stats.get(Stat.Speed);
        yVelocity += y * stats.get(Stat.Speed);

        if (flipWithMoveDirection) {
            if (xVelocity < -0.5f) {
                flipX = true;
            }else if (xVelocity > 0.5f) {
                flipX = false;
            }
        }
    }

    public void goInDirection(float rotationRad, float speedMultiplier) {
        if (shouldApplyKnockBack()) {
            return;
        }

        xVelocity += (float) (Math.cos(rotationRad) * speedMultiplier * stats.get(Stat.Speed));
        yVelocity += (float) (Math.sin(rotationRad) * speedMultiplier * stats.get(Stat.Speed));

        if (flipWithMoveDirection) {
            if (xVelocity < -0.01f) {
                flipX = true;
            }else if (xVelocity > 0.01f) {
                flipX = false;
            }
        }
    }

    public boolean hasComponent(String name) {
        return this.components.stream().anyMatch((a)-> Objects.equals(a.name, name));
    }

    public EntityComponent getComponentByName(String name) {
        return this.components.stream().filter((a)-> Objects.equals(a.name, name)).findFirst().orElse(null);

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

    private boolean shouldApplyKnockBack() {
        return knockBackTimer > 0;
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

    public Entity setSpriteRotation(float rotation) {
        this.spriteRotation = rotation;
        return this;
    }


    public Entity setTeam(EntityTeam team) {
        this.team = team;
        return this;
    }

    public Entity setColor(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        return this;
    }


    public Entity addComponent(EntityComponent component) {
        this.components.add(component);

        resetStats();
        for (EntityComponent c : components) {
            c.onComponentAttached(this);
            c.recalculateStats(this);
        }

        return this;
    }

    public Entity setDrawingLayer(DrawingLayer layer) {
        this.drawingLayer = layer;
        return this;
    }


    public Entity setTriggerInvincibility(boolean triggerInvincibility) {
        this.triggerInvincibility = triggerInvincibility;
        return this;
    }

    public void commitSudoku() {
        for (EntityComponent c: components) {
            c.onSudoku(this);
        }

        this.wantsToLive = false;
    }

    public Entity copy() {
        Entity clone = new Entity()
                .setSprite(sprite)
                .setX(x)
                .setY(y)
                .setTeam(team)
                .setDrawingLayer(drawingLayer)
                .setTriggerInvincibility(triggerInvincibility);
        // components
        for (EntityComponent c : components) {
            clone.addComponent(c.copy());
        }
        // stats
        clone.stats.importValues(stats);

        return clone;
    }


    // overrides
    public Entity overrideDefault(Stat stat, float value, float overridePriority) {
        stats.overrideDefault(stat, value, overridePriority);

        return this;
    }

    public Entity addStat(Stat stat, float value) {
        stats.add(stat, value);
        return this;
    }

    public Entity multiplyStat(Stat stat, float value) {
        stats.multiply(stat, value);
        return this;
    }

}
