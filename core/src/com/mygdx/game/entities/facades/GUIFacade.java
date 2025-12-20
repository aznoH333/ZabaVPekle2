package com.mygdx.game.entities.facades;

import com.mygdx.game.SoundManager;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.drawing.DrawingManager;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityManager;
import com.mygdx.game.entities.components.gui.Button;
import com.mygdx.game.entities.components.gui.GUIRunnable;
import com.mygdx.game.entities.components.gui.Hover;
import com.mygdx.game.entities.components.gui.Text;
import com.mygdx.game.entities.items.Augment;

public class GUIFacade {
    private final static EntityManager entityManager = EntityManager.getInstance();
    private final static SoundManager soundManager = SoundManager.getInstance();


    public static void createButton(String text, float x, float y, GUIRunnable action) {
        entityManager.addEntity(new Entity()
                .makeStatic()
                .setX(x)
                .setY(y)
                .addComponent(
                        new Text(text)
                )
                .addComponent(new Button(action))
                .addComponent(new Hover(
                        (owner)->{
                            Text textComponent = (Text) owner.getComponentByName("text");
                            textComponent.color.b = 0f;
                            soundManager.playSound("click", 1f, 0.1f);
                        },
                        owner -> {
                            Text textComponent = (Text) owner.getComponentByName("text");
                            textComponent.color.b = 1f;
                        }
                ))
                .setWidth(DrawingManager.getInstance().getTextWidth(text))
        );
    }

    public static void createAugmentGUI(Augment augment, float x, float y, Entity player) {
        Entity augmentGui = entityManager.addEntity(
                new Entity()
                        .setSprite(augment.quality.augmentSprite)
                        .addComponent(augment)
                        .setScaleX(3f)
                        .setScaleY(3f)
                        .setX(x)
                        .setY(y)
                        .makeStatic()
                        .setDrawingLayer(DrawingLayer.GUI)
        );

        createButton("Choose", x, y + 48f,
                owner -> {
                    owner.commitSudoku();
                    augmentGui.commitSudoku();
                    augment.attachToEntity(player);
                });
    }
}
