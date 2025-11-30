package com.mygdx.game.entities.components.behaviour;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;

public class BulletComponent extends EntityComponent {

    private final float direction;
    public BulletComponent(float direction) {

        this.direction = direction;
    }

    @Override
    public void onUpdate(Entity owner) {

        owner.goInDirection(direction, 6f);
        owner.knockBackMultiplier = 2f;
        owner.spriteRotation = direction;
    }

    @Override
    public void onWorldCollide(Entity owner) {
        owner.commitSudoku();
    }

    @Override
    public void onCollide(Entity owner, Entity other) {
        if (other.team.isAggressiveAgainst(owner.team)) {
            owner.commitSudoku();
        }
    }


    @Override
    public void recalculateStats(Entity owner) {
        owner.damage = 1f;
    }
}
