package com.mygdx.game.entities.components.gui;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.drawing.DrawingManager;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.utils.NumberUtils;

public class Hover extends EntityComponent {

    private final static DrawingManager drawingManager = DrawingManager.getInstance();

    private final Runnable onHoverStart;
    private final Runnable onHoverEnd;
    private boolean wasHoveredLastFrame = false;

    public Hover(Runnable onHoverStart, Runnable onHoverEnd) {
        this.onHoverStart = onHoverStart;
        this.onHoverEnd = onHoverEnd;
    }

    @Override
    public void onUpdate(Entity owner) {

        Vector2 mousePos = drawingManager.getScreenMousePosition();

        boolean hoveredThisFrame = NumberUtils.checkCollisions(
                owner.x, owner.y, owner.width, owner.height,
                mousePos.x, mousePos.y, 1, 1
        );


        if (hoveredThisFrame && !wasHoveredLastFrame) {
            onHoverStart.run();
        }
        else if (!hoveredThisFrame && wasHoveredLastFrame) {
            onHoverEnd.run();
        }



        wasHoveredLastFrame = hoveredThisFrame;
    }

}
