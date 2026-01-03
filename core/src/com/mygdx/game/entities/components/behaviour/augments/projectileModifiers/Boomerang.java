package com.mygdx.game.entities.components.behaviour.augments.projectileModifiers;

import com.mygdx.game.entities.ComponentName;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.components.behaviour.Bullet;
import com.mygdx.game.entities.fields.FieldName;
import com.mygdx.game.utils.types.NumberUtils;

public class Boomerang extends EntityComponent {

    private float boomerangTime = 1f;
    private float rotationToAdd = (float) Math.PI;
    private Bullet bullet;
    private boolean leftWard = NumberUtils.randomChance(0.5f);


    public Boomerang() {
        super.effectDescription = "boomerang";
    }

    @Override
    public void onUpdate(Entity owner) {
        float speedMultiplier = owner.getNumericStat(FieldName.Speed) / 32f;

        boomerangTime -= speedMultiplier;

        if (boomerangTime <= 0 && rotationToAdd >= 0f) {


            if (leftWard) {
                bullet.direction += 5.5f * speedMultiplier;
            } else {
                bullet.direction -= 5.5f * speedMultiplier;
            }

            rotationToAdd -= 5.5f * speedMultiplier;

        }
    }

    @Override
    public void onComponentAttached(Entity owner) {
        bullet = (Bullet) owner.getComponentByName(ComponentName.BULLET);

    }

    @Override
    public void onFirstAttached(Entity owner) {
        owner.addNumericStat(FieldName.ProjectileLifeTime, 120f);
    }

    @Override
    public EntityComponent copy() {
        return new Boomerang();
    }
}
