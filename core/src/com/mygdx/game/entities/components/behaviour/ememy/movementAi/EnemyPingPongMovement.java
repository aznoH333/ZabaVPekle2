package com.mygdx.game.entities.components.behaviour.ememy.movementAi;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.utils.types.NumberUtils;

public class EnemyPingPongMovement extends EntityComponent {


    public float xDir = NumberUtils.boolToSign(NumberUtils.randomChance(0.5f));
    public float yDir = NumberUtils.boolToSign(NumberUtils.randomChance(0.5f));
    public float speed = 0f;


    @Override
    public void onUpdate(Entity owner) {
        speed = NumberUtils.gravitateNumber(speed, 2f, 0.1f);


        owner.walk(xDir * speed, yDir * speed);
    }

    @Override
    public void onWorldCollide(Entity owner) {
        if (owner.collidedWithWorldOnX) {
            xDir *= -1f;
        } else {
            yDir *= -1f;
        }

        speed = 0.1f;
    }

    @Override
    public EntityComponent copy() {
        return new EnemyPingPongMovement();
    }
}
