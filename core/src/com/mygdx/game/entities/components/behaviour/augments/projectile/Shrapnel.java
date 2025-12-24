package com.mygdx.game.entities.components.behaviour.augments.projectile;

import com.mygdx.game.Managers;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.components.behaviour.Bullet;
import com.mygdx.game.entities.fields.FieldName;
import com.mygdx.game.entities.items.EffectPotency;
import com.mygdx.game.utils.NumberUtils;

public class Shrapnel extends EntityComponent {
    private final int amountToSpawn;

    public Shrapnel(int amountToSpawn) {
        this.amountToSpawn = amountToSpawn;

        super.effectDescription = "shrapnel";
        super.potency = EffectPotency.MODERATE;
    }

    @Override
    public void onSudoku(Entity owner) {
        if (owner.getNumericStat(FieldName.BounceCount) <= 0f) {
            return;
        }

        float rotationPercentage = NumberUtils.TWO_PI / amountToSpawn;
        float currentRotation = 0f;
        for (int i = 0; i < amountToSpawn; i++) {
            Entity newBullet = owner.copy();

            Bullet bullet = (Bullet) newBullet.getComponentByName("bullet");
            bullet.direction += currentRotation;
            currentRotation += rotationPercentage;
            newBullet.setNumericStat(FieldName.ProjectileLifeTime, 30f);

            newBullet.setNumericStat(FieldName.BounceCount, 0f);
            newBullet.setNumericStat(FieldName.DamageMultiplier, -0.75f);
            newBullet.setNumericStat(FieldName.BounceCount, 0f);

            Managers.entityManager.addEntity(newBullet);
        }
    }



    @Override
    public EntityComponent copy() {
        return new Shrapnel(amountToSpawn);
    }
}
