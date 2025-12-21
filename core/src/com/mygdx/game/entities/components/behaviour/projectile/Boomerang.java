package com.mygdx.game.entities.components.behaviour.projectile;

import com.mygdx.game.utils.NumberUtils;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.components.behaviour.Bullet;
import com.mygdx.game.entities.stats.Stat;

public class Boomerang extends EntityComponent {

    private float boomerangTime = 1f;
    private float rotationToAdd = (float) Math.PI;
    private Bullet bullet;
    private boolean leftWard = NumberUtils.randomChance(0.5f);


    public Boomerang() {
        super.effectDescription = "boomerang";
    }

    @Override
    public void onUpdate(Entity owner) {
        float speedMultiplier = owner.stats.get(Stat.Speed) / 32f;

        boomerangTime -= speedMultiplier;

        if (boomerangTime <= 0 && rotationToAdd >= 0f) {


            if (leftWard) {
                bullet.direction += 5.5f * speedMultiplier;
            }else {
                bullet.direction -= 5.5f * speedMultiplier;
            }

            rotationToAdd -= 5.5f * speedMultiplier;

        }
    }

    @Override
    public void onComponentAttached(Entity owner) {
        owner.stats.add(Stat.ProjectileLifeTime, 120f);
        bullet = (Bullet) owner.getComponentByName("bullet");

    }

    @Override
    public EntityComponent copy() {
        return new Boomerang();
    }
}
