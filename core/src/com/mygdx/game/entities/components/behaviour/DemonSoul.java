package com.mygdx.game.entities.components.behaviour;

import com.mygdx.game.NumberUtils;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.EntityManager;
import com.mygdx.game.entities.stats.Stat;

public class DemonSoul extends EntityComponent {
    private final EntityManager entityManager = EntityManager.getInstance();


    private Entity target = null;
    private Shooter shooter = null;

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


            if (shooter != null) {
                shooter.direction = direction;
                shooter.shoot(owner);
            }

            owner.goInDirection(direction, 1f);
        }
    }

    @Override
    public void onComponentAttached(Entity owner) {
        shooter = (Shooter) owner.getComponentByName("shooter");
    }

    @Override
    public void recalculateStats(Entity owner) {
        owner.overrideDefault(Stat.Speed, 1.3f, 1f);
        owner.overrideDefault(Stat.Health, 6f, 1f);
        owner.overrideDefault(Stat.Damage, 1f, 1f);

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
