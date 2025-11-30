package com.mygdx.game.entities.components.behaviour;

import com.mygdx.game.NumberUtils;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.EntityManager;
import com.mygdx.game.entities.components.visual.particles.FadeParticle;
import com.mygdx.game.entities.components.visual.particles.FireParticle;

public class Spawner extends EntityComponent {
    private static final EntityManager entityManager = EntityManager.getInstance();

    private final Entity entityToSpawn;
    private int timer = 90;

    public Spawner(Entity enemyToSpawn) {
        this.entityToSpawn = enemyToSpawn;

    }

    @Override
    public void onUpdate(Entity owner) {
        timer--;

        if (timer % 3 == 0) {
            entityManager.addEntity(
                    new Entity()
                            .setY(owner.y - NumberUtils.randomFloat(0f, 10f))
                            .setX(owner.x + NumberUtils.randomFloat(-10f, 10f))
                            .setSprite("fire_particle_0003")
                            .setDrawingLayer(DrawingLayer.EFFECTS)
                            .setColor(1f, NumberUtils.randomFloat(0.4f, 0.5f), 0.1f, 1f)
                            .addComponent(new FadeParticle(30, true, 0.2f))
                            .addComponent(new FireParticle())
            );
        }


        if (timer > 0) {
            owner.sprite = "enemy_spawner_000" + ((timer / 8) % 2 + 1);
        }
        else if (timer == 0) {
            entityManager.addEntity(entityToSpawn.setX(owner.x).setY(owner.y));
            owner.sprite = "enemy_spawner_0002";
        }
    }
}
