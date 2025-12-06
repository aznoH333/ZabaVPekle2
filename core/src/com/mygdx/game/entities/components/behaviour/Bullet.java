package com.mygdx.game.entities.components.behaviour;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.stats.Stat;

public class Bullet extends EntityComponent {

    public float direction;
    public Bullet(float direction) {
        super.name = "bullet";
        this.direction = direction;
    }

    @Override
    public void onUpdate(Entity owner) {
        owner.goInDirection(direction, 6f);
        owner.knockBackMultiplier = 2f;
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
        owner.overrideDefault(Stat.Damage, 1f, 1f);
        owner.spriteRotation = direction;

    }
}
