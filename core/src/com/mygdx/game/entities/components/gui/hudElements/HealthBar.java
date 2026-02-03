package com.mygdx.game.entities.components.gui.hudElements;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Managers;
import com.mygdx.game.drawing.DrawingCommand;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.fields.FieldName;
import com.mygdx.game.utils.types.NumberUtils;

public class HealthBar extends EntityComponent {
    
    
    private final static Color fullHealthColor = new Color(0.333f, 1.0f, 0.333f, 1f);
    private final static Color lowHealthColor = new Color(0.666f, 0.0f, 0.0f, 1f);
    /// width of the display (the screen not the entire element)
    private final static int HUD_DISPLAY_WIDTH = 67;
    /// height of the display (the screen not the entire element)
    private final static int HUD_DISPLAY_HEIGHT = 32;
    private final static float HUD_DISPLAY_LINE_SPREAD = HUD_DISPLAY_HEIGHT * 0.35f;
    private final static float HUD_DISPLAY_LINE_OFFSET_Y = -4f;
    @Override
    public void onDraw(Entity owner) {
        float healthPercentage = owner.parent.getNumericStat(FieldName.Health) / owner.parent.getNumericStat(FieldName.MaxHealth);
        
        Managers.drawingManager.drawSpriteStatic(new DrawingCommand(
            "hud_health_0002",
            owner.x,
            owner.y
        )
                .setR(NumberUtils.smoothStep(lowHealthColor.r, fullHealthColor.r, healthPercentage))
                .setG(NumberUtils.smoothStep(lowHealthColor.g, fullHealthColor.g, healthPercentage))
                .setB(NumberUtils.smoothStep(lowHealthColor.b, fullHealthColor.b, healthPercentage))
            , DrawingLayer.GUI);
        
        
        
        
        // draw wave
        float batteryDisplayValue = NumberUtils.TWO_PI * (2f - healthPercentage);
        
        for (int i = 1; i < HUD_DISPLAY_WIDTH; i += 2) {
            
            float pixelValue = i + Managers.playStateManager.gameTime;
            
            Managers.drawingManager.drawSpriteStatic(
                
                new DrawingCommand(
                    "pixel",
                    owner.x - (HUD_DISPLAY_WIDTH >> 1) + i,
                    (float) (owner.y + HUD_DISPLAY_LINE_OFFSET_Y + (Math.sin((double) pixelValue / HUD_DISPLAY_WIDTH * batteryDisplayValue) * HUD_DISPLAY_LINE_SPREAD))
                )
                    .setR(NumberUtils.smoothStep(lowHealthColor.r, fullHealthColor.r, healthPercentage))
                    .setG(NumberUtils.smoothStep(lowHealthColor.g, fullHealthColor.g, healthPercentage))
                    .setB(NumberUtils.smoothStep(lowHealthColor.b, fullHealthColor.b, healthPercentage))
                ,
                DrawingLayer.GUI
            );
        }
    }
    
}
