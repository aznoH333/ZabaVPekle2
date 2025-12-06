package com.mygdx.game.entities.components.behaviour.projectile;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.components.behaviour.Bullet;
import com.mygdx.game.entities.stats.Stat;

public class SpinObject extends EntityComponent {


    private Bullet bullet = null;
    private float spinSpeed = 1f;



    @Override
    public void onUpdate(Entity owner) {
        if (spinSpeed > 0f) {
            float spinSpeedMultiplier = 0.25f * owner.stats.get(Stat.Speed);
            spinSpeed -= 0.02f * spinSpeedMultiplier;
            //owner.speed -= 0.05f;
            bullet.direction += 0.7f * spinSpeed * spinSpeedMultiplier;
            owner.spriteRotation += 0.7f * spinSpeed * spinSpeedMultiplier;
        }
    }

    @Override
    public void onComponentAttached(Entity owner) {
        bullet = (Bullet) owner.getComponentByName("bullet");
        // owner.addStat(Stat.Speed, 0.5f);
    }

    @Override
    public EntityComponent copy() {
        return new SpinObject();
    }
}
