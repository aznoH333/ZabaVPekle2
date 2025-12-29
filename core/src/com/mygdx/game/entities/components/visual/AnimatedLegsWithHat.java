package com.mygdx.game.entities.components.visual;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Managers;
import com.mygdx.game.drawing.DrawingCommand;
import com.mygdx.game.entities.ComponentName;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.utils.NumberUtils;

public class AnimatedLegsWithHat extends GameEntityAnimator {
    private static final float HAT_OFFSET_Y = 7f;
    private static final float HAT_OFFSET_X = 1f;

    public final Color bodyColor;
    public final Color hurtColor;
    public final String hatSprite;
    public Color currentColor;

    public AnimatedLegsWithHat(Color bodyColor, Color hurtColor, String hatSprite) {
        super(
                "legs", 1, 2, 8, 9, 3
        );
        super.name = ComponentName.LEGS;


        this.bodyColor = bodyColor;
        this.hurtColor = hurtColor;
        this.hatSprite = hatSprite;
    }

    @Override
    public void onUpdate(Entity owner) {
        // draw hat
        float xOffset = HAT_OFFSET_X * (NumberUtils.boolToInt(owner.flipX) * 2 - 1);

        Managers.drawingManager.drawSprite(
                new DrawingCommand(hatSprite, owner.x + xOffset, owner.y + HAT_OFFSET_Y)
                        .setFlipHorizontally(owner.flipX),
                owner.drawingLayer
        );

        super.onUpdate(owner);

        // color
        if (owner.isStunned()) {
            currentColor = hurtColor;
        } else {
            currentColor = bodyColor;
        }
        owner.setColor(currentColor.r, currentColor.g, currentColor.b, currentColor.a);


    }


}
