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
    public final float maxRadius;
    

    public EyeCursor(float yOffset, float maxRadius) {
        this.yOffset = yOffset;
        this.maxRadius = maxRadius;
    }


    @Override
    public void onDraw(Entity owner) {

        Vector2 mousePos = Managers.drawingManager.getMousePosition();

        float direction = NumberUtils.directionToward(
            owner.x,
            owner.y,
            mousePos.x,
            mousePos.y);




        Managers.drawingManager.drawSprite(
            new DrawingCommand(
                "faces_0002",
                (float) (owner.x + (Math.cos(direction) * maxRadius)),
                (float) (owner.y + yOffset + (Math.sin(direction) * maxRadius))
            ),
            DrawingLayer.PLAYER
        );
    }
}
