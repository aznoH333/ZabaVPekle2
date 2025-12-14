package com.mygdx.game.entities.components.gui;

import com.mygdx.game.drawing.DrawingManager;
import com.mygdx.game.drawing.TextDrawingCommand;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;

public class Text extends EntityComponent {
    private final static DrawingManager drawingManager = DrawingManager.getInstance();

    public String text;

    public Text(String text) {
        this.text = text;
    }

    @Override
    public void onUpdate(Entity owner) {
        drawingManager.drawText(new TextDrawingCommand(text, owner.x, owner.y));
    }

}
