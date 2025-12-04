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
        }else if (NumberUtils.pythagoras(owner.x, owner.y, target.x, target.y) < 680f){
            float direction = NumberUtils.directionToward(owner.x, owner.y, target.x, target.y);
            System.out.println(direction + ", " + bullet.direction);
            if ((direction - bullet.direction) < 0f) {
                bullet.direction -= 0.05f;
            }else {
                bullet.direction += 0.05f;
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
