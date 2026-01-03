package com.mygdx.game.entities.components.behaviour.ememy.movementAi;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.utils.types.NumberUtils;

public class EnemyAimlessWanderMovement extends EntityComponent {
    float directionChange = 0f;
    float directionChangeSign = 1f;
    float direction = NumberUtils.randomFloat(0f, NumberUtils.TWO_PI);
    int directionChangeTimer = 0;
    float speed = 0f;

    @Override
    public void onUpdate(Entity owner) {
        speed = NumberUtils.gravitateNumber(speed, 1f, 0.05f);

        if (directionChange > 0.07f) {
            direction = NumberUtils.constrictRotationToRad(direction + (0.07f * directionChangeSign));
            directionChange -= 0.07f;
        }

        if (directionChangeTimer > 0) {
            directionChangeTimer--;
        } else {
            directionChange = NumberUtils.randomFloat(0, NumberUtils.PI);
            directionChangeSign = NumberUtils.boolToSign(NumberUtils.randomChance(0.5f));
            directionChangeTimer = NumberUtils.randomInt(60, 200);
            speed = 0.1f;
        }

        owner.goInDirection(direction, speed);
    }

    @Override
    public void onWorldCollide(Entity owner) {
        speed = 0.1f;
        directionChangeTimer -= 10;
    }

    @Override
    public EntityComponent copy() {
        return new EnemyAimlessWanderMovement();
    }
}
