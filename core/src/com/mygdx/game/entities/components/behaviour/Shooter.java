package com.mygdx.game.entities.components.behaviour;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.entities.components.visual.AnimatedLegsWithHat;
import com.mygdx.game.utils.NumberUtils;
import com.mygdx.game.SoundManager;
import com.mygdx.game.drawing.DrawingCommand;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.drawing.DrawingManager;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.EntityManager;
import com.mygdx.game.entities.components.behaviour.projectile.*;
import com.mygdx.game.entities.factories.ProjectileFactory;

import java.util.ArrayList;

public class Shooter extends EntityComponent {
    private static final DrawingManager DRAWING_MANAGER = DrawingManager.getInstance();
    private static final EntityManager entityManager = EntityManager.getInstance();
    private static final SoundManager soundManager = SoundManager.getInstance();

    public float direction = 0f;
    private int scaleTimer = 0;

    private int fireCooldown = 0;


    // statistics
    public int fireRate = 20;
    public float spread = 0.045f;
    public int bulletsPerShot = 1;
    public float bulletSpeed = 0.75f;
    public float damage = 2f;
    public String bulletSprite = "fire_ball";
    private ArrayList<BulletOrigin> bulletOrigins = new ArrayList<>();
    public Color color;
    private AnimatedLegsWithHat legs;


    private ArrayList<EntityComponent> bulletComponents = new ArrayList<>();

    private final String sprite;
    public Shooter(String sprite) {
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

                DRAWING_MANAGER.drawSprite(
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
        fireCooldown = fireRate;

        // play sound
        soundManager.playSound("fire_ball", 1f, 0.1f);

        for (BulletOrigin b : bulletOrigins) {
            float handDirection = direction + b.aimOffset;

            for (int i = 0; i < bulletsPerShot; i++) {
                float bulletDirection = handDirection + NumberUtils.randomFloat(-spread, spread);


                Entity bullet = ProjectileFactory.buildBullet(
                        owner.x,
                        owner.y,
                        bulletSprite,
                        damage,
                        bulletSpeed,
                        owner.team,
                        bulletDirection,
                        120,
                        bulletComponents
                );

                entityManager.addEntity(bullet);
            }
        }


    }

    @Override
    public void onComponentAttached(Entity owner) {
        legs = (AnimatedLegsWithHat) owner.getComponentByName("legs");
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