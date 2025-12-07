package com.mygdx.game.entities.components.behaviour.projectile;

import com.mygdx.game.NumberUtils;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.EntityManager;
import com.mygdx.game.entities.components.behaviour.Bullet;
import com.mygdx.game.entities.stats.Stat;

public class Shrapnel extends EntityComponent {

    private final static EntityManager entityManager = EntityManager.getInstance();
    private final int amountToSpawn;

    public Shrapnel(int amountToSpawn) {
        this.amountToSpawn = amountToSpawn;
    }

    @Override
    public void onSudoku(Entity owner) {
        if (owner.stats.get(Stat.BounceCount) != 0f) {
            return;
        }

        float rotationPercentage = NumberUtils.TWO_PI / amountToSpawn;
        float currentRotation = 0f;
        for (int i = 0; i < amountToSpawn; i++) {
            Entity newBullet = owner.copy();

            Bullet bullet = (Bullet)newBullet.getComponentByName("bullet");
            bullet.direction += currentRotation;
            currentRotation += rotationPercentage;
            bullet.lifeTime = 10;
            newBullet.addStat(Stat.BounceCount, 1f);

            entityManager.addEntity(newBullet);
        }
    }

    @Override
    public EntityComponent copy() {
        return new Shrapnel(amountToSpawn);
    }
}
