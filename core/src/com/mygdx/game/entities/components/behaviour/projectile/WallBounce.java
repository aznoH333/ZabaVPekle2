package com.mygdx.game.entities.components.behaviour.projectile;

import com.mygdx.game.Managers;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.components.behaviour.Bullet;
import com.mygdx.game.entities.fields.FieldName;
import com.mygdx.game.utils.NumberUtils;

public class WallBounce extends EntityComponent {

    private Bullet bullet;

    public WallBounce() {
        super.name = "wall bounce";
        super.effectDescription = "wall bounce";
        super.componentCountLimit = 3;
    }

    @Override
    public void onWorldCollide(Entity owner) {
        if (owner.getNumericStat(FieldName.BounceCount) <= 1f) {
            return;
        }

        float xDir = (float) Math.cos(bullet.direction);
        float yDir = (float) Math.sin(bullet.direction);

        if (owner.collidedWithWorldOnX) {
            bullet.direction = NumberUtils.directionToward(0, 0, -xDir, yDir);
        } else {
            bullet.direction = NumberUtils.directionToward(0, 0, xDir, -yDir);
        }

        Entity newBullet = owner.copy();
        newBullet.addNumericStat(FieldName.BounceCount, -1f);

        Managers.entityManager.addEntity(newBullet);

    }


    @Override
    public void onComponentAttached(Entity owner) {
        bullet = (Bullet) owner.getComponentByName("bullet");
    }

    @Override
    public void onFirstAttached(Entity owner) {
        owner.setNumericStat(FieldName.BounceCount, 1f);
        owner.addNumericStat(FieldName.ProjectileLifeTime, 30f);

    }

    @Override
    public EntityComponent copy() {
        return new WallBounce();
    }
}
