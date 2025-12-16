package com.mygdx.game.entities.components.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.drawing.DrawingCommand;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.drawing.DrawingManager;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.utils.NumberUtils;

public class Button extends EntityComponent {

    private final static DrawingManager drawingManager = DrawingManager.getInstance();
    private final Runnable buttonAction;

    public Button(Runnable buttonAction) {
        this.buttonAction = buttonAction;
    }

    @Override
    public void onUpdate(Entity owner) {
        //
        Vector2 mousePos = drawingManager.getScreenMousePosition();
        drawingManager.drawSpriteStatic(new DrawingCommand("player_1", mousePos.x, mousePos.y), DrawingLayer.PLAYER);

        if (NumberUtils.checkCollisions(
                owner.x, owner.y, owner.width, owner.height,
                mousePos.x, mousePos.y, 1, 1
        ) && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            buttonAction.run();
        }
    }
}
