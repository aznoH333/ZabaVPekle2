package com.mygdx.game.entities.components.behaviour.projectile;

import com.mygdx.game.NumberUtils;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.EntityManager;
import com.mygdx.game.entities.components.behaviour.Bullet;
import com.mygdx.game.entities.stats.Stat;

public class WallBounce extends EntityComponent {

    private Bullet bullet;
    private final static EntityManager entityManager = EntityManager.getInstance();

    @Override
    public void onWorldCollide(Entity owner) {
        float xDir = (float) Math.cos(bullet.direction);
        float yDir = (float) Math.sin(bullet.direction);

        if (owner.collidedWithWorldOnX) {
            bullet.direction = NumberUtils.directionToward(0, 0, -xDir, yDir);
        }else {
            bullet.direction = NumberUtils.directionToward(0, 0, xDir, -yDir);
        }

        Entity newBullet = owner.copy();
        newBullet.addStat(Stat.BounceCount, -1f);
        entityManager.addEntity(newBullet);

    }


    @Override
    public void onComponentAttached(Entity owner) {
        owner.stats.add(Stat.BounceCount, 1f);
        owner.stats.add(Stat.ProjectileLifeTime, 30f);
        bullet = (Bullet) owner.getComponentByName("bullet");
    }

    @Override
    public EntityComponent copy() {
        return new WallBounce();
    }
}
