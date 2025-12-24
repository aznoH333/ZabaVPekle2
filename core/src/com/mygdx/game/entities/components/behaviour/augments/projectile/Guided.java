package com.mygdx.game.entities.components.behaviour.augments.projectile;

import com.mygdx.game.Managers;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.components.behaviour.Bullet;
import com.mygdx.game.entities.fields.FieldName;
import com.mygdx.game.utils.NumberUtils;

public class Guided extends EntityComponent {

    private final String targetComponent;
    private Bullet bullet;
    private Entity target;

    public Guided(String targetTeam) {
        this.targetComponent = targetTeam;
    }

    @Override
    public void onUpdate(Entity owner) {
        if (target == null || !target.wantsToLive) {
            target = Managers.entityManager.findClosestEntityWithComponent(owner, targetComponent);
        } else if (NumberUtils.pythagoras(owner.x, owner.y, target.x, target.y) < 96f) {


            float direction = NumberUtils.constrictRotationToRad(NumberUtils.directionToward(owner.x, owner.y, target.x, target.y) + NumberUtils.TWO_PI);
            float bulletDirection = NumberUtils.constrictRotationToRad(bullet.direction);
            owner.addNumericStat(FieldName.Speed, 0.01f);


            if (NumberUtils.constrictRotationToRad(direction - bulletDirection + NumberUtils.TWO_PI) < Math.PI) {
                bullet.direction += 0.5f * owner.getNumericStat(FieldName.Speed);
            } else {
                bullet.direction -= 0.5f * owner.getNumericStat(FieldName.Speed);
            }


        }
    }


    @Override
    public void onComponentAttached(Entity owner) {
        bullet = (Bullet) owner.getComponentByName("bullet");
        owner.addNumericStat(FieldName.SpeedMultiplier, -0.1f);
    }


    @Override
    public EntityComponent copy() {
        return new Guided(targetComponent);
    }
}
