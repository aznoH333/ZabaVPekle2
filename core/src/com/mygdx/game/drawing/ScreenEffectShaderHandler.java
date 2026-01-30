package com.mygdx.game.drawing;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * Handles logic related to various screen effects.
 * <p>
 * Currently handles
 * <ul>
 *     <li>Screen dimming</li>
 * </ul>
 */
public class ScreenEffectShaderHandler {
    
    
    private final ShaderProgram shader;
    
    private float screenBrightness = 1f;
    private int screenEffectDimTimer = 0;
    private int screenEffectDimTimerLenght = 1;
    
    
    public ScreenEffectShaderHandler(ShaderProgram shader) {
        this.shader = shader;
    }
    
    public void apply() {
        
        shader.bind();
        if (screenEffectDimTimer > 0) {
            screenEffectDimTimer--;
        }
        
        
        screenBrightness = (Math.abs(0.5f - ((float)screenEffectDimTimer / screenEffectDimTimerLenght)) * 2f);
        
        this.shader.setUniformf("screenBrightness", screenBrightness);
    }
    
    
    public void dimScreen(int length) {
        this.screenEffectDimTimer = length;
        this.screenEffectDimTimerLenght = length;
    }
}
