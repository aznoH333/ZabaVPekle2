package com.mygdx.game.entities.components.visual;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Managers;
import com.mygdx.game.drawing.DrawingCommand;
import com.mygdx.game.entities.ComponentName;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.utils.types.NumberUtils;

public class AnimatedLegsWithHat extends GameEntityAnimator {
    private static final float HAT_OFFSET_Y = 7f;
    private static final float HAT_OFFSET_X = -1f;

    public final Color bodyColor;
    public final Color hurtColor;
    public final String hatSprite;
    public Color currentColor;
    public final LegsWithHatType type;

    public AnimatedLegsWithHat(LegsWithHatType type, Color bodyColor, Color hurtColor, String hatSprite) {
        super(
            type.bodyBaseSprite, type.idleIndex, type.walkStartIndex, type.walkEndIndex, type.hurtIndex, 4
        );

        super.name = ComponentName.LEGS;

        this.type = type;
        this.bodyColor = bodyColor;
        this.hurtColor = hurtColor;
        this.hatSprite = hatSprite;
    }

    @Override
    public void onUpdate(Entity owner) {
        // draw hat
        float xOffset = HAT_OFFSET_X * (NumberUtils.boolToInt(owner.flipX) * 2 - 1);


        if (hatSprite != null) {
            Managers.drawingManager.drawSprite(
                new DrawingCommand(hatSprite, owner.x + xOffset, owner.y + (HAT_OFFSET_Y * owner.scaleY))
                    .setFlipHorizontally(owner.flipX)
                    .setWidth(owner.scaleX)
                    .setHeight(owner.scaleY),
                owner.drawingLayer
            );
        }


        super.onUpdate(owner);

        // color
        if (owner.isStunned()) {
            currentColor = hurtColor;
        } else {
            currentColor = bodyColor;
        }
        owner.setColor(currentColor.r, currentColor.g, currentColor.b, currentColor.a);


    }

    @Override
    public EntityComponent copy() {
        return new AnimatedLegsWithHat(
            type,
            this.bodyColor,
            this.hurtColor,
            this.hatSprite
        );
    }


}
