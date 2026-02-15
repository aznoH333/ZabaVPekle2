package com.mygdx.game.facades.sceen;

import com.mygdx.game.Managers;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.entities.ComponentName;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.components.gui.Button;
import com.mygdx.game.entities.components.gui.EntityRunnable;
import com.mygdx.game.entities.components.gui.Hover;
import com.mygdx.game.entities.components.gui.Text;
import com.mygdx.game.entities.items.Augment;

public class GUIFacade {


    public static void createButton(String text, float x, float y, EntityRunnable action) {
        Managers.entityManager.addEntity(buildButton(text, x, y, action));
    }

    public static Entity buildButton(String text, float x, float y, EntityRunnable action) {
        return new Entity()
            .makeStatic()
            .setX(x)
            .setY(y)
            .addComponent(
                new Text(text, 0f, 3f)
            )
            .setSprite("button")
            .setWidth(128f)
            .setHeight(32f)
            .setColor(0.25f, 0.25f, 0.25f, 1f)
            .addComponent(new Button(action))
            .addComponent(new Hover(
                (owner) -> {
                    Text textComponent = (Text) owner.getComponentByName(ComponentName.TEXT);
                    textComponent.color.b = 0f;
                    Managers.soundManager.playSound("click", 1f, 0.1f);
                    owner.setColor(0.35f, 0.35f, 0.35f, 1f);
                },
                owner -> {
                    Text textComponent = (Text) owner.getComponentByName(ComponentName.TEXT);
                    textComponent.color.b = 1f;
                    owner.setColor(0.25f, 0.25f, 0.25f, 1f);
                }
            ));
    }

    public static void createAugmentGUI(Augment augment, float x, float y, Entity player, Entity guiOwner) {
        guiOwner.addChild(
            new Entity()
                .setSprite(augment.quality.augmentSprite)
                .addComponent(augment)
                .setScaleX(3f)
                .setScaleY(3f)
                .setX(x)
                .setY(y)
                .makeStatic()
                .setDrawingLayer(DrawingLayer.GUI));


        guiOwner.addChild(buildButton("Choose", x, y + 48f,
            owner -> {
                guiOwner.commitSudoku();
                augment.attachToEntity(player);
            }));
    }
}
