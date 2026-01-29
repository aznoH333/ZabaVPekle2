package com.mygdx.game.facades.sceen;

import com.mygdx.game.Managers;
import com.mygdx.game.drawing.LightHandle;

/**
 * A facade that controls the various shader related and vfx systems
 */
public class VisualEffectsFacade {
    public static LightHandle getNewLight(float x, float y, float intensity, float brightness) {
        return Managers.drawingManager.lightingShaderHandler.getNewLight(x, y, intensity, brightness);
    }
    
    public static void clearAllLights() {
        Managers.drawingManager.lightingShaderHandler.clearAllLights();
    }
}
