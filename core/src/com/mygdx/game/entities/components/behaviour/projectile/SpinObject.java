package com.mygdx.game.entities.components.behaviour.projectile;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.components.behaviour.Bullet;

public class SpinObject extends EntityComponent {


    private Bullet bullet = null;
    private float spinSpeed = 1f;



    @Override
    public void onUpdate(Entity owner) {
        if (spinSpeed > 0f) {
            spinSpeed -= 0.01f;
            //owner.speed -= 0.05f;
            bullet.direction += 0.5f * spinSpeed;
            owner.spriteRotation += 0.5f * spinSpeed;
        }
    }

    @Override
    public void onComponentAttached(Entity owner) {
        bullet = (Bullet) owner.getComponentByName("bullet");
        owner.speed += 1.5f;
    }

    @Override
    public EntityComponent copy() {
        return new SpinObject();
    }
}
