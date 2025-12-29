package com.mygdx.game.entities.components.gui;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Managers;
import com.mygdx.game.drawing.TextDrawingCommand;
import com.mygdx.game.entities.ComponentName;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;

public class Text extends EntityComponent {

    public String text;
    public Color color;

    public Text(String text) {
        super.name = ComponentName.TEXT;
        this.text = text;
        this.color = new Color(1f, 1f, 1f, 1f);
    }

    @Override
    public void onUpdate(Entity owner) {
        Managers.drawingManager.drawText(new TextDrawingCommand(text, owner.x, owner.y).setColor(color.r, color.b, color.g, color.a));
    }

}
