package com.mygdx.game.entities.components.visual;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Managers;
import com.mygdx.game.drawing.DrawingCommand;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.utils.types.NumberUtils;

public class EyeCursor extends EntityComponent {

    public final float yOffset;
    public final float xOffset;
    public final float maxRadius;
    

    public EyeCursor(float xOffset, float yOffset, float maxRadius) {
        this.yOffset = yOffset;
        this.xOffset = xOffset;
        this.maxRadius = maxRadius;
    }


    @Override
    public void onDraw(Entity owner) {

        Vector2 eyeDir = Managers.inputManager.getShootingDirection(owner.x, owner.y);

        float direction = NumberUtils.directionToward(
            0,
            0,
            eyeDir.x,
            eyeDir.y);




        Managers.drawingManager.drawSprite(
            new DrawingCommand(
                "faces_0002",
                (float) (owner.x + (xOffset * NumberUtils.boolToSign(!owner.flipX)) +(Math.cos(direction) * maxRadius)),
                (float) (owner.y + yOffset + (Math.sin(direction) * maxRadius))
            ),
            DrawingLayer.PLAYER
        );
    }
}
