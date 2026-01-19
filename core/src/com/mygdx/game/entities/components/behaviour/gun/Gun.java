package com.mygdx.game.entities.components.behaviour.gun;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Managers;
import com.mygdx.game.drawing.DrawingCommand;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.entities.ComponentName;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.components.behaviour.augments.projectileModifiers.SpinSprite;
import com.mygdx.game.entities.components.visual.AnimatedLegsWithHat;
import com.mygdx.game.entities.facades.ProjectileFactory;
import com.mygdx.game.entities.fields.FieldName;
import com.mygdx.game.utils.types.NumberUtils;

import java.util.ArrayList;


/**
 * An entity component responsible for spawning bullets.
 * Intended to be used by the player and common enemies.
 * Uses bullet origins to determine which direction should shots go.
 *
 * @see BulletOrigin
 */
public class Gun extends EntityComponent {
    /**
     * The firing direction in radians. Used to update the offset of each bullet origin.
     */
    public float direction = 0f;

    /**
     * How long until the gun resets firing cooldown for origins.
     */
    private int fireCooldownResetTimer = 0;


    // statistics
    /**
     * Color of the gun
     */
    public Color color;

    /**
     * A reference to the owners body component. Used to determine the guns color
     *
     * @see AnimatedLegsWithHat
     */
    private AnimatedLegsWithHat legs;

    /**
     * a reference value tied to the owners [FieldName.ProjectileComponents] field
     */
    private ArrayList<EntityComponent> bulletComponents = null;

    /**
     * a reference value tied to the owners [FieldName.ProjectileComponents] field
     */
    public ArrayList<BulletOrigin> bulletOrigins = null;

    private final String sprite;

    /**
     * Constructs a new gun. Initializes a new bullet origin with offset 0f
     */
    public Gun(String sprite) {
        super.name = ComponentName.GUN;
        this.sprite = sprite;

    }

    @Override
    public void onUpdate(Entity owner) {

        if (fireCooldownResetTimer > 0) {
            fireCooldownResetTimer--;

            if (fireCooldownResetTimer == 0) {
                resetBulletOriginCooldowns();
            }
        }


        // draw
        if (sprite != null) {
            for (BulletOrigin origin : bulletOrigins) {

                if (origin.scaleTimer > 0) {
                    origin.scaleTimer--;
                }

                float gunScale = (float) origin.scaleTimer / getFireRate();
                float handDir = direction + origin.aimOffset;

                Managers.drawingManager.drawSprite(
                    new DrawingCommand(sprite,
                        (float) Math.cos(handDir) * (((1f - gunScale) * 5f + 5f) * owner.scaleX) + owner.x,
                        (float) Math.sin(handDir) * (((1f - gunScale) * 5f + 5f) * owner.scaleY) + owner.y
                    )
                        .setRotationRad(handDir)
                        .setFlipVertically(owner.flipX)
                        .setWidth((1 + gunScale * 0.25f) * owner.scaleX)
                        .setHeight((1 + gunScale * 0.25f) * owner.scaleY)
                        .setColor(legs.currentColor),
                    DrawingLayer.HAND);
            }
        }

    }

    public void shoot(Entity owner) {

        int projectilesPerShot = (int) owner.getNumericStat(FieldName.ProjectilesPerShot);
        float spreadValue = owner.getNumericStat(FieldName.ProjectileSpread) * owner.getNumericStat(FieldName.ProjectileSpread);
        fireCooldownResetTimer = getFireRate();
        for (BulletOrigin origin : bulletOrigins) {


            if (origin.fireCooldown > 0) {
                origin.fireCooldown--;
                continue;
            }

            origin.fireCooldown = getFireRate();
            origin.scaleTimer = getFireRate();
            Managers.soundManager.playSound("fire_ball", 1f, 0.1f);

            float gunDirection = direction + origin.aimOffset;

            // shoot projectiles
            for (int i = 0; i < projectilesPerShot; i++) {
                float bulletDirection = gunDirection + NumberUtils.randomFloat(-spreadValue, spreadValue);


                Entity bullet = ProjectileFactory.buildBullet(
                    (float) Math.cos(bulletDirection) * (5f * owner.scaleX) + owner.x,
                    (float) Math.sin(bulletDirection) * (5f * owner.scaleY) + owner.y,
                    owner.getField(FieldName.ProjectileSprite),
                    owner.getNumericStat(FieldName.ProjectileDamage) * owner.getNumericStat(FieldName.DamageMultiplier),
                    owner.getNumericStat(FieldName.ProjectileSpeed),
                    owner.team,
                    bulletDirection,
                    (int) owner.getNumericStat(FieldName.ProjectileLifeTime),
                    bulletComponents,
                    owner.getNumericStat(FieldName.BounceCount)
                );

                Managers.entityManager.addEntity(bullet);
            }
        }
    }

    /**
     * Adds a new bullet origin. Recalculates origin firing delays based on their configuration.
     *
     * @param newOrigin
     */
    public void addBulletOrigin(BulletOrigin newOrigin) {


        // add new origin
        this.bulletOrigins.add(newOrigin);


        bulletOrigins.sort((a, b) -> (int) (a.aimOffset - b.aimOffset));
        // recalculate firing delays
        ArrayList<BulletOrigin> asynchronousBulletOrigins = new ArrayList<>();

        for (BulletOrigin origin : bulletOrigins) {
            if (origin.asynchronousFiring) {
                asynchronousBulletOrigins.add(origin);
            }
        }

        int asynchronousOriginCount = asynchronousBulletOrigins.size();
        float originPercentage = 1f / asynchronousOriginCount;

        for (int i = 0; i < asynchronousOriginCount; i++) {
            BulletOrigin origin = asynchronousBulletOrigins.get(i);

            origin.initialFireDelay = originPercentage * i;
        }

        resetBulletOriginCooldowns();
    }

    private void resetBulletOriginCooldowns() {
        for (BulletOrigin origin : bulletOrigins) {
            origin.fireCooldown = (int) (getFireRate() * origin.initialFireDelay);
        }
    }

    private int getFireRate() {
        return (int) (owner.getNumericStat(FieldName.FireRate) * owner.getNumericStat(FieldName.FireRateMultiplier));
    }

    @Override
    public void onComponentAttached(Entity owner) {
        legs = (AnimatedLegsWithHat) owner.getComponentByName(ComponentName.LEGS);
    }

    public void onFirstAttached(Entity owner) {
        owner.initializeNumericField(FieldName.FireRate, 1f);
        owner.initializeNumericField(FieldName.ProjectilesPerShot, 1f);
        owner.initializeNumericField(FieldName.ProjectileDamage, 1f);
        owner.initializeNumericField(FieldName.DamageMultiplier, 1f);


        owner.initializeField(FieldName.ProjectileSprite, "bullets_0001");

        owner.initializeField(FieldName.ProjectileComponents, new ArrayList<EntityComponent>());
        this.bulletComponents = owner.getField(FieldName.ProjectileComponents);

        owner.initializeField(FieldName.Guns, new ArrayList<EntityComponent>());
        this.bulletOrigins = owner.getField(FieldName.Guns);


        // add default gun values
        addBulletOrigin(new BulletOrigin(0f, true));

    }

    public void addBulletComponent(EntityComponent component) {
        this.bulletComponents.add(component);
    }

    @Override
    public EntityComponent copy() {
        return new Gun(sprite);
    }
}

