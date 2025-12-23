package com.mygdx.game.entities.components.behaviour;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Managers;
import com.mygdx.game.drawing.DrawingCommand;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.components.behaviour.projectile.SpinSprite;
import com.mygdx.game.entities.components.visual.AnimatedLegsWithHat;
import com.mygdx.game.entities.facades.ProjectileFactory;
import com.mygdx.game.entities.fields.FieldName;
import com.mygdx.game.utils.NumberUtils;

import java.util.ArrayList;

public class Gun extends EntityComponent {

    public float direction = 0f;
    private int scaleTimer = 0;

    private int fireCooldown = 0;


    // statistics
    private ArrayList<BulletOrigin> bulletOrigins = new ArrayList<>();
    public Color color;
    private AnimatedLegsWithHat legs;


    private ArrayList<EntityComponent> bulletComponents = new ArrayList<>();

    private final String sprite;

    public Gun(String sprite) {
        super.name = "shooter";
        this.sprite = sprite;
        // add default origin
        bulletOrigins.add(new BulletOrigin(0f));

        //bulletOrigins.add(new BulletOrigin(NumberUtils.PI - NumberUtils.THIRD_PI));
        //bulletOrigins.add(new BulletOrigin(NumberUtils.PI + NumberUtils.THIRD_PI));


        // addBulletComponent(new SineTravel());
        // addBulletComponent(new Guided("evil soul"));

        //addBulletComponent(new SpinObject());

        addBulletComponent(new SpinSprite(-0.25f));
        // addBulletComponent(new Shrapnel(3));
        // addBulletComponent(new Boomerang());
        //addBulletComponent(new Boomerang());
        //addBulletComponent(new Shrapnel(5));

        //addBulletComponent(new WallBounce());
        //addBulletComponent(new WallBounce());


    }

    @Override
    public void onUpdate(Entity owner) {
        // decrement timers
        if (scaleTimer > 0) {
            scaleTimer--;
        }
        if (fireCooldown > 0) {
            fireCooldown--;
        }


        // draw
        if (sprite != null) {
            for (BulletOrigin b : bulletOrigins) {
                float handDir = direction + b.aimOffset;

                Managers.drawingManager.drawSprite(
                        new DrawingCommand(sprite,
                                (float) Math.cos(handDir) * (10f - (scaleTimer / 10f) * 2f) + owner.x,
                                (float) Math.sin(handDir) * (10f - (scaleTimer / 10f) * 2f) + owner.y
                        )
                                .setRotationRad(handDir)
                                .setFlipVertically(owner.flipX)
                                .setWidth(1 + ((scaleTimer / 10f) * 0.25f))
                                .setHeight(1 + ((scaleTimer / 10f) * 0.25f))
                                .setColor(legs.currentColor),
                        DrawingLayer.HAND);
            }
        }

    }

    public void shoot(Entity owner) {
        if (fireCooldown != 0) {
            return;
        }

        scaleTimer = 10;
        fireCooldown = (int) owner.getNumericStat(FieldName.FireRate);

        // play sound
        Managers.soundManager.playSound("fire_ball", 1f, 0.1f);

        int projectilesPerShot = (int) owner.getNumericStat(FieldName.ProjectilesPerShot);

        for (BulletOrigin b : bulletOrigins) {
            float handDirection = direction + b.aimOffset;
            for (int i = 0; i < projectilesPerShot; i++) {
                float bulletDirection = handDirection + NumberUtils.randomFloat(-owner.getNumericStat(FieldName.ProjectileSpread), owner.getNumericStat(FieldName.ProjectileSpread));


                Entity bullet = ProjectileFactory.buildBullet(
                        owner.x,
                        owner.y,
                        owner.getField(FieldName.ProjectileSprite),
                        owner.getNumericStat(FieldName.ProjectileDamage) * owner.getNumericStat(FieldName.DamageMultiplier),
                        owner.getNumericStat(FieldName.ProjectileSpeed),
                        owner.team,
                        bulletDirection,
                        120,
                        bulletComponents
                );

                Managers.entityManager.addEntity(bullet);
            }
        }


    }

    @Override
    public void onComponentAttached(Entity owner) {
        legs = (AnimatedLegsWithHat) owner.getComponentByName("legs");
    }

    public void onFirstAttached(Entity owner) {
        owner.setNumericStat(FieldName.ProjectileDamage, 0f);
        owner.setNumericStat(FieldName.ProjectileSpeed, 0.1f);
        owner.setNumericStat(FieldName.FireRate, 1f);
        owner.setNumericStat(FieldName.ProjectileSpread, 0f);
        owner.setNumericStat(FieldName.ProjectilesPerShot, 1f);
        owner.setField(FieldName.ProjectileSprite, "fire_ball");
    }

    public void addBulletComponent(EntityComponent component) {
        this.bulletComponents.add(component);
    }
}

class BulletOrigin {
    public float aimOffset;

    public BulletOrigin(float aimOffset) {
        this.aimOffset = aimOffset;
    }
}