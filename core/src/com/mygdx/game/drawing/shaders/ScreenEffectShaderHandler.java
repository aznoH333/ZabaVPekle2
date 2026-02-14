package com.mygdx.game.drawing.shaders;

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
    
    
    private final ShaderProgram screenShader;
    private final ShaderProgram gameShader;
    
    private float screenBrightness = 1f;
    private int screenEffectDimTimer = 0;
    private int screenEffectDimTimerLenght = 1;
    
    
    
    public ScreenEffectShaderHandler(ShaderProgram screenShader, ShaderProgram gameShader) {
        this.screenShader = screenShader;
        this.gameShader = gameShader;
    }
    
    public void apply() {
        
        if (screenEffectDimTimer > 0) {
            screenEffectDimTimer--;
        }
        
        
        screenBrightness = (Math.abs(0.5f - ((float)screenEffectDimTimer / screenEffectDimTimerLenght)) * 2f);
        screenShader.bind();
        this.screenShader.setUniformf("screenBrightness", screenBrightness);
        gameShader.bind();
        this.gameShader.setUniformf("screenBrightness", screenBrightness);
    }
    
    
    public void dimScreen(int length) {
        this.screenEffectDimTimer = length;
        this.screenEffectDimTimerLenght = length;
    }
}
