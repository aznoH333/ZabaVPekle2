package com.mygdx.game.entities.components.behaviour;

import com.mygdx.game.Managers;
import com.mygdx.game.drawing.TextDrawingCommand;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.fields.FieldName;

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
        owner.addNumericStat(FieldName.RemainingProjectileLifeTime, -1f);


        float remainingLifetime = owner.getNumericStat(FieldName.RemainingProjectileLifeTime);


        // scale down
        if (remainingLifetime < 30f) {
            owner.scaleX = remainingLifetime / 30f;
            owner.scaleY = remainingLifetime / 30f;
        }

        // expire
        if (remainingLifetime <= 0) {
            owner.commitSudoku();
        }
    }

    @Override
    public void onFirstAttached(Entity owner) {
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
    public EntityComponent copy() {
        return new Bullet(direction, lifeTime);
    }
}
