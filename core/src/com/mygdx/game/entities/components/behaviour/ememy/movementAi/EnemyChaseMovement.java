package com.mygdx.game.entities.components.behaviour.ememy.movementAi;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.fields.FieldName;
import com.mygdx.game.utils.types.NumberUtils;

public class EnemyChaseMovement extends EntityComponent {
    @Override
    public void onUpdate(Entity owner) {
        Entity target = owner.getField(FieldName.Target);


        if (target != null) {
            float direction = NumberUtils.directionToward(owner.x, owner.y, target.x, target.y);
            owner.goInDirection(direction, 1f);
        }
    }


    @Override
    public EntityComponent copy() {
        return new EnemyChaseMovement();
    }
}
