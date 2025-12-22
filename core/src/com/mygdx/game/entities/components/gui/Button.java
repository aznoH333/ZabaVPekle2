package com.mygdx.game.entities.components.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Managers;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.utils.NumberUtils;

public class Button extends EntityComponent {

    private final GUIRunnable buttonAction;

    public Button(GUIRunnable buttonAction) {
        this.buttonAction = buttonAction;
    }

    @Override
    public void onUpdate(Entity owner) {
        Vector2 mousePos = Managers.drawingManager.getScreenMousePosition();

        if (NumberUtils.checkCollisions(
                owner.x, owner.y, owner.width, owner.height,
                mousePos.x, mousePos.y, 1, 1
        ) && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            buttonAction.run(owner);
        }
    }
}
