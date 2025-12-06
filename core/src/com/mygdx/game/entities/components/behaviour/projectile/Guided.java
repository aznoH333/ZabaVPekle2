package com.mygdx.game.entities.components.behaviour.projectile;

import com.mygdx.game.NumberUtils;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.EntityManager;
import com.mygdx.game.entities.components.behaviour.Bullet;

public class Guided extends EntityComponent {

    private final static EntityManager entityManager = EntityManager.getInstance();

    private final String targetComponent;
    private Bullet bullet;
    private Entity target;

    public Guided(String targetTeam) {
        this.targetComponent = targetTeam;
    }

    @Override
    public void onUpdate(Entity owner) {
        if (target == null || !target.wantsToLive) {
            target = entityManager.findClosestEntityWithComponent(owner, targetComponent);
        }else if (NumberUtils.pythagoras(owner.x, owner.y, target.x, target.y) < 96f){


            float direction = NumberUtils.constrictRotationToRad(NumberUtils.directionToward(owner.x, owner.y, target.x, target.y) + NumberUtils.TWO_PI);
            float bulletDirection = NumberUtils.constrictRotationToRad(bullet.direction);
            owner.speed += 0.01f;

            if (NumberUtils.constrictRotationToRad(direction - bulletDirection + NumberUtils.TWO_PI) < Math.PI) {
                bullet.direction += 0.5f * owner.speed;
            }else {
                bullet.direction -= 0.5f * owner.speed;
            }


        }
    }


    @Override
    public void onComponentAttached(Entity owner) {
        bullet = (Bullet) owner.getComponentByName("bullet");
    }


    @Override
    public EntityComponent copy() {
        return new Guided(targetComponent);
    }
}
