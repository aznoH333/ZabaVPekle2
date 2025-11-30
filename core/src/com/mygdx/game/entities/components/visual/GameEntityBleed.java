package com.mygdx.game.entities.components.visual;

import com.mygdx.game.NumberUtils;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.EntityManager;
import com.mygdx.game.entities.components.visual.particles.BloodParticle;

public class GameEntityBleed extends EntityComponent {
    private final static EntityManager entityManager = EntityManager.getInstance();

    @Override
    public void onTakeDamage(Entity owner, float amount) {
        spawnParticles(owner, amount);
    }

    @Override
    public void onSudoku(Entity owner) {
        spawnParticles(owner, 20f - owner.health);
    }

    private void spawnParticles(Entity owner, float amount) {
        int amountOfBloodToSpawn = (int) Math.min(Math.ceil(amount * NumberUtils.randomFloat(0.6f, 1.6f) / 10f) + 1, 5f);

        for (int i = 0; i < amountOfBloodToSpawn; i++) {
            entityManager.addEntity(new Entity()
                    .setSprite("blood_" + NumberUtils.randomInt(1, 3))
                    .setX(owner.x)
                    .setY(owner.y)
                    .addComponent(new BloodParticle(NumberUtils.randomFloat(0f, NumberUtils.TWO_PI), NumberUtils.randomFloat(0.5f, 2f), NumberUtils.randomFloat(0.5f, 2.5f)))
                    .setDrawingLayer(DrawingLayer.PROJECTILES)

            );
        }
    }
}
