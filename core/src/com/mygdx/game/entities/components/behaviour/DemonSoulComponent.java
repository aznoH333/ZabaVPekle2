package com.mygdx.game.entities.components.behaviour;

import com.mygdx.game.NumberUtils;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.EntityManager;

public class DemonSoulComponent extends EntityComponent {
    private final EntityManager entityManager = EntityManager.getInstance();


    private Entity target = null;

    public DemonSoulComponent() {
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
        owner.flipWithMoveDirection = true;
    }
}
