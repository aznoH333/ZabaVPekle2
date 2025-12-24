package com.mygdx.game.entities.components.behaviour.augments.projectile;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.components.behaviour.Bullet;
import com.mygdx.game.entities.fields.FieldName;
import com.mygdx.game.utils.NumberUtils;

public class SineTravel extends EntityComponent {

    private Bullet bullet;
    private int timer = 3;


    public SineTravel() {
        effectDescription = "wave shot";
    }

    @Override
    public void onUpdate(Entity owner) {
        timer++;

        float direction = bullet.direction + NumberUtils.HALF_PI;
        float strength = (float) (Math.sin(timer / 10f) * 4f);

        owner.goInDirection(direction, strength);
    }

    @Override
    public void onComponentAttached(Entity owner) {
        bullet = (Bullet) owner.getComponentByName("bullet");

    }

    @Override
    public void onFirstAttached(Entity owner) {
        owner.addNumericStat(FieldName.Speed, 0.025f);
    }

    @Override
    public SineTravel copy() {
        return new SineTravel();
    }
}
