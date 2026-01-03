package com.mygdx.game.entities.components.visual;

import com.mygdx.game.Managers;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.components.visual.particles.BloodParticle;
import com.mygdx.game.entities.fields.FieldName;
import com.mygdx.game.utils.types.NumberUtils;

public class GameEntityBleed extends EntityComponent {

    @Override
    public void onTakeDamage(Entity owner, float amount) {
        spawnParticles(owner, amount);
    }

    @Override
    public void onSudoku(Entity owner) {
        spawnParticles(owner, 20f - owner.getNumericStat(FieldName.Health));
    }

    private void spawnParticles(Entity owner, float amount) {
        int amountOfBloodToSpawn = (int) Math.min(Math.ceil(amount * NumberUtils.randomFloat(0.6f, 1.6f) / 2f) + 1, 20f);

        for (int i = 0; i < amountOfBloodToSpawn; i++) {
            Managers.entityManager.addEntity(new Entity()
                .setSprite("blood_" + NumberUtils.randomInt(1, 3))
                .setX(owner.x)
                .setY(owner.y)
                .addComponent(new BloodParticle(
                    NumberUtils.randomFloat(0f, NumberUtils.TWO_PI),
                    NumberUtils.randomFloat(0.5f, 6f),
                    NumberUtils.randomFloat(0.5f, 2.5f)
                ))
                .setDrawingLayer(DrawingLayer.PROJECTILES)

            );
        }
    }


    @Override
    public EntityComponent copy() {
        return new GameEntityBleed();
    }
}
