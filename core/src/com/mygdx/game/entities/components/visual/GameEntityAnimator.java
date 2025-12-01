package com.mygdx.game.entities.components.visual;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;

public class GameEntityAnimator extends EntityComponent {

    private final String baseSprite;
    private final int walkStartIndex;
    private final int walkEndIndex;
    private final int hurtIndex;
    private final int idleIndex;
    private final int framesPerWalkFrame;
    private int walkTimer = 0;


    public GameEntityAnimator(String baseSprite, int idleIndex, int walkStartIndex, int walkEndIndex, int hurtIndex, int framesPerWalkFrame) {
        this.baseSprite = baseSprite;
        this.idleIndex = idleIndex;
        this.walkStartIndex = walkStartIndex;
        this.walkEndIndex = walkEndIndex + 1;
        this.hurtIndex = hurtIndex;
        this.framesPerWalkFrame = framesPerWalkFrame;
    }

    @Override
    public void onUpdate(Entity owner) {


        // hurt
        if (owner.knockBackTimer != 0) {
            owner.sprite = baseSprite + "_" + hurtIndex;
            return;
        }

        // walk
        if (Math.abs(owner.lastFrameXVelocity) + Math.abs(owner.lastFrameYVelocity) > 0.25f) {

            walkTimer++;

            int walkSprite = (int) ((double) (walkTimer / framesPerWalkFrame) % (walkEndIndex - walkStartIndex)) + walkStartIndex;

            owner.sprite = baseSprite + "_" + walkSprite;
            return;
        }

        walkTimer = 0;
        owner.sprite = baseSprite + "_" + idleIndex;
        return;

    }
}
