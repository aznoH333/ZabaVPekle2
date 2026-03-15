package com.mygdx.game.entities.components.gui;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Managers;
import com.mygdx.game.drawing.FontSize;
import com.mygdx.game.drawing.TextDrawingCommand;
import com.mygdx.game.entities.ComponentName;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;

public class Text extends EntityComponent {

    public String text;
    public Color color;
    public final float xOffset;
    public final float yOffset;
    public FontSize fontSize;
    
    public Text(String text, float xOffset, float yOffset) {
        super.name = ComponentName.TEXT;
        this.text = text;
        this.color = new Color(1f, 1f, 1f, 1f);
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        fontSize = FontSize.MEDIUM;
    }

    @Override
    public void onUpdate(Entity owner) {
        Managers.drawingManager.drawText(new TextDrawingCommand(text, owner.x + xOffset, owner.y + yOffset)
                .setColor(color.r, color.b, color.g, color.a)
                .setSize(fontSize)
        );
    }

    public Text setFontSize(FontSize size) {
        this.fontSize = size;
        return this;
    }

}
