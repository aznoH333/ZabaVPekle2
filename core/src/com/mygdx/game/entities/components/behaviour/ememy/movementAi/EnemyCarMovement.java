package com.mygdx.game.entities.components.behaviour.ememy.movementAi;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.fields.FieldName;
import com.mygdx.game.utils.types.NumberUtils;

public class EnemyCarMovement extends EntityComponent {

    private float speed = 0f;
    private float direction = NumberUtils.randomFloat(0f, NumberUtils.TWO_PI);

    @Override
    public void onUpdate(Entity owner) {
        Entity target = owner.getField(FieldName.Target);

        owner.goInDirection(direction, speed * 1.5f);

        if (target == null) {
            return;
        }


        float directionTowardsTarget = NumberUtils.constrictRotationToRad(NumberUtils.directionToward(owner.x, owner.y, target.x, target.y));
        float diff = NumberUtils.constrictRotationToRad(directionTowardsTarget - direction + NumberUtils.TWO_PI);


        if (diff < Math.PI) {
            direction += 1.75f * (1f - speed);
        } else {
            direction -= 1.75f * (1f - speed);
        }

        direction = NumberUtils.constrictRotationToRad(direction);


        if (NumberUtils.constrictRotationToRad(NumberUtils.constrictRotationToRad(direction - directionTowardsTarget + NumberUtils.HALF_PI)) < NumberUtils.PI) {
            speed = NumberUtils.gravitateNumber(speed, 1f, 0.05f);
        } else {
            speed = NumberUtils.gravitateNumber(speed, 0.1f, 0.05f);
        }


    }

    @Override
    public EntityComponent copy() {
        return new EnemyCarMovement();
    }
}
