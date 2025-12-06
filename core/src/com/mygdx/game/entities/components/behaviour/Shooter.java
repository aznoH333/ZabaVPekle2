package com.mygdx.game.entities.components.behaviour;

import com.mygdx.game.NumberUtils;
import com.mygdx.game.drawing.DrawingCommand;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.drawing.SpriteManager;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.EntityManager;
import com.mygdx.game.entities.EntityTeam;
import com.mygdx.game.entities.components.behaviour.projectile.SpinObject;
import com.mygdx.game.entities.stats.Stat;

import java.util.ArrayList;

public class Shooter extends EntityComponent {
    private static final SpriteManager spriteManager = SpriteManager.getInstance();
    private static final EntityManager entityManager = EntityManager.getInstance();

    public float direction = 0f;
    private int scaleTimer = 0;

    private int fireCooldown = 0;


    // statistics
    public int fireRate = 8;
    public float spread = 0.015f;
    public int bulletsPerShot = 1;
    public float bulletSpeed = 0.25f;
    public float damage = 2f;
    public String bulletSprite = "fire_ball";


    private ArrayList<EntityComponent> bulletComponents = new ArrayList<>();

    private final String sprite;
    public Shooter(String sprite) {
        super.name = "shooter";
        this.sprite = sprite;
        // addBulletComponent(new SineTravel());
        // addBulletComponent(new Guided("evil soul"));
        addBulletComponent(new SpinObject());
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
            spriteManager.drawSprite(
                    new DrawingCommand(sprite,
                            (float) Math.cos(direction) * (10f - (scaleTimer / 10f) * 2f) + owner.x,
                            (float) Math.sin(direction) * (10f - (scaleTimer / 10f) * 2f) + owner.y
                    )
                            .setRotationRad(direction)
                            .setFlipVertically(owner.flipX)
                            .setWidth(1 + ((scaleTimer / 10f) * 0.25f))
                            .setHeight(1 + ((scaleTimer / 10f) * 0.25f)),
                    DrawingLayer.HAND);
        }
    }

    public void shoot(Entity owner) {
        if (fireCooldown != 0) {
            return;
        }

        scaleTimer = 10;
        fireCooldown = fireRate;

        for (int i = 0; i < bulletsPerShot; i++) {
            float bulletDirection = direction + NumberUtils.randomFloat(-spread, spread);

            Entity bullet = new Entity()
                            .setSprite(bulletSprite)
                            .setX(owner.x)
                            .setY(owner.y)
                            .overrideDefault(Stat.Damage, damage, 2f)
                            .overrideDefault(Stat.Speed, bulletSpeed, 2f)
                            .setTriggerInvincibility(false)
                            .setTeam(EntityTeam.FROG)
                            .setDrawingLayer(DrawingLayer.PROJECTILES)
                            .addComponent(new Bullet(bulletDirection));

            for (EntityComponent c : bulletComponents) {
                bullet.addComponent(c.copy());
            }


            entityManager.addEntity(bullet);
        }

    }

    public void addBulletComponent(EntityComponent component) {
        this.bulletComponents.add(component);
    }
}
