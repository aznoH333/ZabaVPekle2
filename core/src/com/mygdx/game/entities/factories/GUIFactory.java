package com.mygdx.game.entities.factories;

import com.mygdx.game.SoundManager;
import com.mygdx.game.drawing.DrawingManager;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.components.gui.Button;
import com.mygdx.game.entities.components.gui.GUIRunnable;
import com.mygdx.game.entities.components.gui.Hover;
import com.mygdx.game.entities.components.gui.Text;

public class GUIFactory {



    public static Entity createButton(String text, float x, float y, GUIRunnable action) {



        return new Entity()
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
                            SoundManager.getInstance().playSound("click", 1f, 0.1f);
                        },
                        owner -> {
                            Text textComponent = (Text) owner.getComponentByName("text");
                            textComponent.color.b = 1f;
                        }
                ))
                .setWidth(DrawingManager.getInstance().getTextWidth(text))
                ;
    }
}
