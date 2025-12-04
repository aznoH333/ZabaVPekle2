package com.mygdx.game.entities.components.behaviour.projectile;

import com.mygdx.game.NumberUtils;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.components.behaviour.Bullet;

public class SineTravel extends EntityComponent {

    private Bullet bullet;
    private int timer = 3;


    @Override
    public void onUpdate(Entity owner) {
        timer++;

        float direction = bullet.direction + NumberUtils.HALF_PI;
        float strength = (float) (Math.sin(timer / 10f) * 4f);

        owner.goInDirection(direction, strength);
    }

    @Override
    public void onComponentAttached(Entity owner) {
        bullet = (Bullet) owner.getComponentByName("bullet");
        owner.speed += 0.2f;
    }

    @Override
    public SineTravel copy() {
        return new SineTravel();
    }
}
