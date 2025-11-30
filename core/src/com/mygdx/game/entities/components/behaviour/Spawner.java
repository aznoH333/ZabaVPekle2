package com.mygdx.game.entities.components.behaviour;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.EntityManager;
import com.mygdx.game.entities.components.visual.particles.FadeParticle;

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

        if (timer > 0) {
            owner.sprite = "enemy_spawner_000" + ((timer / 8) % 2 + 1);
        }
        else if (timer == 0) {
            entityManager.addEntity(entityToSpawn.setX(owner.x).setY(owner.y));
            owner.sprite = "enemy_spawner_0002";
        }
    }
}
