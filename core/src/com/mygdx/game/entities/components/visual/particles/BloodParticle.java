package com.mygdx.game.entities.components.visual.particles;

import com.mygdx.game.utils.NumberUtils;
import com.mygdx.game.SoundManager;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.EntityManager;

public class BloodParticle extends EntityComponent {

    private final static EntityManager entityManager = EntityManager.getInstance();
    private final static SoundManager soundManager = SoundManager.getInstance();

    private final float direction;
    private float verticalVelocity;
    private float height = 0f;
    private float horizontalVelocity = 0f;

    public BloodParticle(float direction, float verticalVelocity, float horizontalVelocity) {
        this.direction = direction;
        this.verticalVelocity = verticalVelocity;
        this.horizontalVelocity = horizontalVelocity;
    }

    @Override
    public void onUpdate(Entity owner) {
        height += verticalVelocity;

        verticalVelocity -= 0.3f;

        if (horizontalVelocity > 0f) {
            horizontalVelocity *= 0.95f;
        }

        owner.spriteOffsetY = height;

        if (height < 0f) {
            owner.commitSudoku();
        }

        owner.goInDirection(direction, horizontalVelocity);
    }

    @Override
    public void onSudoku(Entity owner) {


        String sprite = "blood_big_" + NumberUtils.randomInt(1, 3);
        if (NumberUtils.randomChance(0.8f)) {
            sprite = "blood_" + NumberUtils.randomInt(4, 8);
        }

        // sound
        soundManager.playSound("blood_splat", 0.1f, 0.1f);



        // spawn on ground particle
        entityManager.addEntity(
                new Entity()
                        .setX(owner.x)
                        .setY(owner.y)
                        .setSpriteRotation(NumberUtils.randomFloat(0, NumberUtils.TWO_PI))
                        .addComponent(new FadeParticle(512, false, 0.3f))
                        .setSprite(sprite)
                        .setDrawingLayer(DrawingLayer.BLOOD)
        );
    }
}
