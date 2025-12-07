package com.mygdx.game.entities.components.behaviour;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.stats.Stat;

public class Bullet extends EntityComponent {

    public float direction;
    private final int lifeTime;
    public Bullet(float direction, int lifeTime) {
        super.name = "bullet";
        this.direction = direction;
        this.lifeTime = lifeTime;
    }

    @Override
    public void onUpdate(Entity owner) {
        owner.goInDirection(direction, 6f);
        owner.knockBackMultiplier = 2f;
        owner.stats.add(Stat.ProjectileLifeTime, -1f);

        if (owner.stats.get(Stat.ProjectileLifeTime) <= 0) {
            owner.commitSudoku();
        }
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
        owner.overrideDefault(Stat.Speed, 0.1f, 1f);
        owner.spriteRotation = direction;
        owner.overrideDefault(Stat.ProjectileLifeTime, lifeTime, 1f);
    }

    @Override
    public EntityComponent copy() {
        return new Bullet(direction, lifeTime);
    }
}
