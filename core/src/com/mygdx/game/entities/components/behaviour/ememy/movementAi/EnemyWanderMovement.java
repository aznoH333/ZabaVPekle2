package com.mygdx.game.entities.components.behaviour.ememy.movementAi;

import com.mygdx.game.Managers;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.components.behaviour.ememy.Coordinate;
import com.mygdx.game.entities.fields.FieldName;
import com.mygdx.game.utils.NumberUtils;

public class EnemyWanderMovement extends EntityComponent {

    private Coordinate movementTarget = null;
    private int moveTimer = 0;

    @Override
    public void onUpdate(Entity owner) {



        if (moveTimer > 0) {
            moveTimer--;
        }


        if (moveTimer == 0 && movementTarget == null) {
            Entity target = owner.getField(FieldName.Target);


            if (target != null && NumberUtils.pythagoras(owner.x, owner.y, target.x, target.y) < 1000f && NumberUtils.randomChance(0.80f)) {
                movementTarget = new Coordinate(target.x, target.y);
            }else {
                float pickedX;
                float pickedY;

                do {
                    pickedX = owner.x + (NumberUtils.randomFloat(32f, 128f) * NumberUtils.boolToSign(NumberUtils.randomChance(0.5f)));
                    pickedY = owner.y + (NumberUtils.randomFloat(32f, 128f) * NumberUtils.boolToSign(NumberUtils.randomChance(0.5f)));
                }while (!Managers.worldManager.isSpaceEmpty(pickedX, pickedY, owner.width, owner.height));

                movementTarget = new Coordinate(pickedX, pickedY);
            }
        }

        if (movementTarget == null) {

            return;

        }



        float direction = NumberUtils.directionToward(owner.x, owner.y, movementTarget.x(), movementTarget.y());

        owner.goInDirection(direction, 2f);

        if (NumberUtils.pythagoras(owner.x, owner.y, movementTarget.x(), movementTarget.y()) < 10f) {
            movementTarget = null;
            moveTimer = 20;
        }

    }

    @Override
    public EntityComponent copy() {
        return new EnemyWanderMovement();
    }
}
