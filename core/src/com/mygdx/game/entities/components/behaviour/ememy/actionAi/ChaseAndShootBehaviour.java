package com.mygdx.game.entities.components.behaviour.ememy.actionAi;

import com.mygdx.game.entities.ComponentName;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.components.behaviour.Dash;
import com.mygdx.game.entities.components.behaviour.gun.Gun;
import com.mygdx.game.entities.fields.FieldName;
import com.mygdx.game.utils.types.NumberUtils;

public class ChaseAndShootBehaviour extends EnemyCombatBehaviour{


    private int initialCeaseFire = NumberUtils.randomInt(60, 180);


    public ChaseAndShootBehaviour(int duration) {
        super(duration);
    }

    @Override
    public void act(Entity owner) {

        Entity target = owner.getField(FieldName.Target);
        Gun gun = owner.getField(FieldName.Gun);
        Dash dash = owner.getField(FieldName.Dash);


        float direction = NumberUtils.directionToward(owner.x, owner.y, target.x, target.y);

        if (gun != null) {
            gun.direction = direction;
            if (initialCeaseFire == 0) {
                gun.shoot(owner);
            } else {
                initialCeaseFire--;
            }
        }


        if (dash != null && initialCeaseFire == 0) {
            dash.dashInDirection(direction);
        }
    }

    @Override
    public EnemyCombatBehaviour copy() {
        return new ChaseAndShootBehaviour(super.getDuration());
    }
}
