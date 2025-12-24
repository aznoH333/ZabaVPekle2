package com.mygdx.game.entities.components.behaviour.augments.projectile;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;

public class SpinSprite extends EntityComponent {


    private final float spinSpeed;

    public SpinSprite(float spinSpeed) {
        this.spinSpeed = spinSpeed;
    }

    @Override
    public void onUpdate(Entity owner) {
        owner.spriteRotation += spinSpeed;

    }


    @Override
    public EntityComponent copy() {
        return new SpinSprite(spinSpeed);
    }
}
