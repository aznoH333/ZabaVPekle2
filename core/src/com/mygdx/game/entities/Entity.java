package com.mygdx.game.entities;

import com.mygdx.game.Managers;
import com.mygdx.game.drawing.DrawingCommand;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.entities.fields.Copyable;
import com.mygdx.game.entities.fields.EntityFields;
import com.mygdx.game.entities.fields.EntityNumericFields;
import com.mygdx.game.entities.fields.FieldName;
import com.mygdx.game.utils.types.NumberUtils;

import java.util.ArrayList;
import java.util.Objects;

public class Entity implements Copyable {


    public String sprite;
    public float x;
    public float y;
    public float spriteOffsetX = 0f;
    public float spriteOffsetY = 0f;
    public float width = 16f;
    public float height = 16f;
    private final ArrayList<EntityComponent> components = new ArrayList<>();
    public DrawingLayer drawingLayer = DrawingLayer.FLOOR;
    public boolean triggerInvincibility = true;
    public boolean freezeMovement = false;

    private final EntityFields genericFields = new EntityFields();
    private final EntityNumericFields numericFields = new EntityNumericFields();
    public EntityIdentifier identifier = EntityIdentifier.UNDEFINED;


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
    public float lastFrameVelocity = 0;
    public boolean collidedWithWorldOnX = false;
    public boolean collidedWithWorldOnY = false;


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
    public boolean drawAsStatic = false;
    


    public ArrayList<Entity> children = new ArrayList<>();
    public Entity parent = null;

    public Entity() {
        setNumericStat(FieldName.Health, 1f);
        setNumericStat(FieldName.Speed, 0f);
        setNumericStat(FieldName.SpeedMultiplier, 1f);
        setNumericStat(FieldName.DamageMultiplier, 1f);
        setNumericStat(FieldName.Damage, 0f);
    }

    
    public void draw() {
        if (sprite != null) {
            // draw
            Managers.drawingManager.drawSprite(
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
                drawingLayer,
                drawAsStatic
            );
        }
        
        for (EntityComponent component : this.components) {
            component.onDraw(this);
        }
        
        for (Entity child : children) {
            child.draw();
        }
    }

    public void update() {
        handleCompenentLifecycle();

        for (EntityComponent components : this.components) {
            components.onUpdate(this);
        }

        // knock back movement
        if (shouldApplyKnockBack()) {
            xVelocity = (float) (Math.cos(knockBackDirection) * knockBackSpeed);
            yVelocity = (float) (Math.sin(knockBackDirection) * knockBackSpeed);
            knockBackSpeed *= 0.9f;
        }

        // move
        collidedWithWorldOnX = false;
        collidedWithWorldOnY = false;
        if (Managers.levelManager.isSpaceEmpty(x + xVelocity, y, width, height)) {
            x += xVelocity;
            lastFrameXVelocity = xVelocity;
        } else {
            collidedWithWorldOnX = true;
            lastFrameXVelocity = 0f;
        }

        if (Managers.levelManager.isSpaceEmpty(x, y + yVelocity, width, height)) {
            y += yVelocity;
            lastFrameYVelocity = yVelocity;
        } else {
            if (!collidedWithWorldOnX) {
                collidedWithWorldOnY = true;
            }
            lastFrameYVelocity = 0f;
        }

        lastFrameVelocity = NumberUtils.distance(0f, 0f, lastFrameXVelocity, lastFrameYVelocity);
        xVelocity = 0;
        yVelocity = 0;

        // world collision
        if (collidedWithWorldOnX || collidedWithWorldOnY) {
            for (EntityComponent c : components) {
                c.onWorldCollide(this);
            }
        }


        // invincibility
        if (this.invincibilityTimer > 0) {
            this.invincibilityTimer--;
        }
        if (knockBackTimer > 0) {
            this.knockBackTimer--;
        }

        for (Entity child : children) {
            child.update();
        }
        
        children.removeIf((child)->{
            if (!child.wantsToLive) {
                child.invokeSudoku();
                return true;
            }
            
            return false;
        });
    }

    private void handleCompenentLifecycle() {
        // add new components
        for(EntityComponent component : componentWaitingRoom) {
            if (component.componentCountLimit > 0 && countComponentsWithName(component.name) >= component.componentCountLimit) {
                // dont add component if over limit
                continue;
            }
            component.owner = this;
            component.onFirstAttached(this);
            components.add(component);

            for (EntityComponent c : components) {
                c.onComponentAttached(this);
            }
        }
        componentWaitingRoom.clear();


        // kill deleted components
        for (int i = componentsToRemove.size() - 1; i >= 0; i--) {
            components.remove(componentsToRemove.get(i).intValue());
        }
        componentsToRemove.clear();

    }

    public void initializeEntity() {
        handleCompenentLifecycle();
    }


    public void onCollide(Entity other) {
        for (EntityComponent component : this.components) {
            component.onCollide(this, other);
        }
        // take damage
        if (this.invincibilityTimer == 0 && other.team.isAggressiveAgainst(this.team) && other.getNumericStat(FieldName.Damage) != 0f && canBeDamaged) {

            float damageToDeal = (other.getNumericStat(FieldName.Damage) * other.getNumericStat(FieldName.DamageMultiplier));
            addNumericStat(FieldName.Health, -damageToDeal);


            if (other.triggerInvincibility) {
                this.invincibilityTimer = this.invincibilityTimerMax;
            }
            this.knockBackTimer = this.knockBackTimerMax;


            if (this.getNumericStat(FieldName.Health) <= 0f) {
                this.commitSudoku();
            } else {
                for (EntityComponent c : components) {
                    c.onTakeDamage(this, damageToDeal);
                }
            }

            // knock back
            if (other.knockBackMultiplier > 0f) {
                knockBackDirection = NumberUtils.directionToward(other.x, other.y, x, y);
                knockBackSpeed = other.knockBackMultiplier;
            }
        }
    }


    public void walk(float x, float y) {
        if (shouldApplyKnockBack()) {
            return;
        }

        xVelocity += x * getNumericStat(FieldName.Speed) * getNumericStat(FieldName.SpeedMultiplier);
        yVelocity += y * getNumericStat(FieldName.Speed) * getNumericStat(FieldName.SpeedMultiplier);

        if (flipWithMoveDirection) {
            if (xVelocity < -0.5f) {
                flipX = true;
            } else if (xVelocity > 0.5f) {
                flipX = false;
            }
        }
    }

    public void goInDirection(float rotationRad, float speedMultiplier) {
        if (shouldApplyKnockBack()) {
            return;
        }

        xVelocity += (float) (Math.cos(rotationRad) * speedMultiplier * getNumericStat(FieldName.Speed) * getNumericStat(FieldName.SpeedMultiplier));
        yVelocity += (float) (Math.sin(rotationRad) * speedMultiplier * getNumericStat(FieldName.Speed) * getNumericStat(FieldName.SpeedMultiplier));

        if (flipWithMoveDirection) {
            if (xVelocity < -0.01f) {
                flipX = true;
            } else if (xVelocity > 0.01f) {
                flipX = false;
            }
        }
    }

    public boolean hasComponent(ComponentName name) {
        return this.components.stream().anyMatch((a) -> Objects.equals(a.name, name));
    }

    public EntityComponent getComponentByName(ComponentName name) {
        return this.components.stream().filter((a) -> Objects.equals(a.name, name)).findFirst().orElse(null);
    }

    private ArrayList<Integer> componentsToRemove = new ArrayList<>();

    public void removeComponentByName(ComponentName name) {
        for (int i = 0; i < components.size(); i++) {
            if (components.get(i).name == name) {
                componentsToRemove.add(i);

                break;
            }
        }

    }

    public int countComponentsWithName(ComponentName name) {
        return (int) this.components.stream().filter((a) -> Objects.equals(a.name, name)).count();
    }

    public boolean collidesWithEntity(Entity other) {

        return NumberUtils.checkCollisions(
            x, y, width, height,
            other.x, other.y, other.width, other.height
        );

    }

    private boolean shouldApplyKnockBack() {
        return knockBackTimer > 0;
    }

    public boolean isStunned() {
        return this.knockBackTimer != 0;
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

    public Entity setFlipX(boolean value) {
        this.flipX = value;
        return this;
    }

    public Entity setFlipY(boolean value) {
        this.flipY = value;
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

    public Entity makeStatic() {
        drawAsStatic = true;
        return this;
    }

    private ArrayList<EntityComponent> componentWaitingRoom = new ArrayList<>();


    public Entity addComponent(EntityComponent component) {
        this.componentWaitingRoom.add(component);
        component.owner = this;
        return this;
    }

    public Entity setDrawingLayer(DrawingLayer layer) {
        this.drawingLayer = layer;
        return this;
    }

    public Entity setScaleX(float scaleX) {
        this.scaleX = scaleX;
        return this;
    }

    public Entity setScaleY(float scaleY) {
        this.scaleY = scaleY;
        return this;
    }

    public Entity setCanBeDamaged(boolean canBeDamaged) {
        this.canBeDamaged = canBeDamaged;
        return this;
    }

    public Entity setTriggerInvincibility(boolean triggerInvincibility) {
        this.triggerInvincibility = triggerInvincibility;
        return this;
    }

    public void commitSudoku() {
        this.wantsToLive = false;

        for (Entity child : children) {
            child.commitSudoku();
        }
    }

    public void invokeSudoku() {
        for (EntityComponent c : components) {
            c.onSudoku(this);
        }
    }

    public void removedFromWorld() {
        for (EntityComponent c : components) {
            c.onCleanUp(this);
        }
    }

    public void placedInWorld() {
        for (EntityComponent c : components) {
            c.onPlacedInWorld(this);
        }
    }

    public Entity copy() {

        Entity clone = new Entity()
            .setSprite(sprite)
            .setX(x)
            .setY(y)
            .setScaleX(scaleX)
            .setScaleY(scaleY)
            .setTeam(team)
            .setDrawingLayer(drawingLayer)
            .setTriggerInvincibility(triggerInvincibility);
        // components
        for (EntityComponent c : components) {
            clone.addComponent(c.copy());
        }
        for (EntityComponent c : componentWaitingRoom) {
            clone.addComponent(c.copy());
        }

        // stats
        clone.genericFields.importValues(this.genericFields);
        clone.numericFields.importValues(this.numericFields);

        return clone;
    }


    public Entity addChild(Entity child) {
        this.children.add(child);
        child.parent = this;
        return this;
    }

    public float getNumericStat(FieldName fieldName) {
        return this.numericFields.getField(fieldName);
    }

    public Entity setNumericStat(FieldName fieldName, float value) {
        this.numericFields.setField(fieldName, value);
        return this;
    }


    public Entity addNumericStat(FieldName fieldName, float value) {
        this.numericFields.addToField(fieldName, value);
        return this;
    }

    public <T> T getField(FieldName fieldName) {
        return this.genericFields.getField(fieldName);
    }

    public <T> Entity setField(FieldName fieldName, T value) {
        this.genericFields.setField(fieldName, value);
        return this;
    }

    public <T> Entity initializeField(FieldName fieldName, T value) {
        this.genericFields.initializeValue(fieldName, value);
        return this;
    }

    public Entity initializeNumericField(FieldName fieldName, float value) {
        this.numericFields.initializeValue(fieldName, value);
        return this;
    }
    
    public Entity setIdentifier(EntityIdentifier identifier) {
        this.identifier = identifier;
        return  this;
    }

}
