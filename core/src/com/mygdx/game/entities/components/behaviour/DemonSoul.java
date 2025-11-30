package com.mygdx.game.entities.components.behaviour;

import com.mygdx.game.NumberUtils;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.EntityManager;

public class DemonSoul extends EntityComponent {
    private final EntityManager entityManager = EntityManager.getInstance();


    private Entity target = null;

    public DemonSoul() {
        super.name = "evil soul";
    }

    @Override
    public void onUpdate(Entity owner) {
        if (target == null) {
            target = entityManager.findClosestEntityWithComponent(owner, "soul");
        }

        if (target != null) {
            float direction = NumberUtils.directionToward(owner.x, owner.y, target.x, target.y);


            owner.goInDirection(direction, 1f);
        }
    }

    @Override
    public void recalculateStats(Entity owner) {
        owner.speed = 1.3f;
        owner.setHealth(6f);
        owner.damage = 1f;
        owner.knockBackMultiplier = 8f;
        owner.flipWithMoveDirection = true;
        owner.canBeDamaged = true;
    }

    @Override
    public void onCollide(Entity owner, Entity other) {
        if (NumberUtils.pythagoras(owner.x, owner.y, other.x, other.y) < 16f && other.hasComponent("evil soul")) {
            // bump away from each other
            owner.goInDirection(NumberUtils.directionToward(other.x, other.y, owner.x, owner.y), 0.25f);
        }
    }
}
