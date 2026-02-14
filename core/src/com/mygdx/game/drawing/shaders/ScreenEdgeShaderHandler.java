package com.mygdx.game.drawing.shaders;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.mygdx.game.drawing.DrawingManager;

public class ScreenEdgeShaderHandler {
    
    public float levelSize = 128f;
    
    private final ShaderProgram gameShader;
    private final OrthographicCamera camera;
    
    public ScreenEdgeShaderHandler(ShaderProgram gameShader, OrthographicCamera camera) {
        this.gameShader = gameShader;
        this.camera = camera;
    }
    
    
    public void apply() {
        gameShader.bind();
        gameShader.setUniformf("levelSize", levelSize);
        gameShader.setUniform2fv("worldOffset", new float[] {camera.position.x + 32f, camera.position.y + 16f}, 0, 2);
        gameShader.setUniform2fv("screenSize", new float[] {DrawingManager.SCREEN_WIDTH * camera.zoom, DrawingManager.SCREEN_HEIGHT * camera.zoom}, 0, 2);
        
    }
}
