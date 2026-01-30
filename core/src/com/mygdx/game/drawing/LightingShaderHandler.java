package com.mygdx.game.drawing;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import java.util.ArrayList;

/// Handles logic related to lights and lighting
public class LightingShaderHandler {
    
    public static final int MAX_LIGHTS = 32;
    
    private ArrayList<LightHandle> lights = new ArrayList<>();
    private static final int LIGHT_COMPONENT_COUNT = 4;
    
    private final ShaderProgram shader;
    private final OrthographicCamera camera;
    
    public LightingShaderHandler(ShaderProgram shader, OrthographicCamera camera) {
        this.shader = shader;
        this.camera = camera;
    }
    
    public LightHandle getNewLight(float x, float y, float intensity, float brightness) {
        if (lights.size() >= MAX_LIGHTS) {
            System.out.println("Warning : failed to instantiate light (exceeded light limit of " + MAX_LIGHTS + ")");
            return new LightHandle(x, y, intensity, brightness, -1);
        }
        
        LightHandle handle = new LightHandle(x, y, intensity, brightness, lights.size());
        
        lights.add(handle);
        
        return handle;
    }
    
    
    public void applyLights(float aspectRatio) {
        
        
        lights.removeIf(lightHandle -> !lightHandle.isActive());
        
        
        // lights
        float[] parametrisedLights = new float[lights.size() * LIGHT_COMPONENT_COUNT];
        
        for (int i = 0; i < lights.size(); i++) {
            Float[] params = lights.get(i).convertToShaderParams(camera, aspectRatio);
            
            
            for (int j = 0; j < LIGHT_COMPONENT_COUNT; j++) {
                parametrisedLights[i * LIGHT_COMPONENT_COUNT + j] = params[j];
            }
            
        }
        
        
        shader.setUniform1fv("lights", parametrisedLights, 0, parametrisedLights.length);
        shader.setUniformi("usedLights", lights.size());
        
    }
    
    public void clearAllLights() {
        this.lights.clear();
    }
}
