package com.mygdx.game.drawing;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class LightHandle {
    public float x;
    public float y;
    public float intensity;
    private int lightIndex;
    private boolean active;

    public LightHandle(float x, float y, float intensity, int lightIndex) {
        this.x = x;
        this.y = y;
        this.intensity = intensity;
        this.lightIndex = lightIndex;



        this.active = lightIndex != -1;
    }

    public boolean isActive() {
        return active;
    }

    public void destroy() {
        this.active = false;
    }


    /**
     * encodes light as float[3]
     * output [0] and [1] are the lights x and y converted to screen coordinates (-1 to 1)
     * output [2] is the lights intensity
     * */
    public Float[] convertToShaderParams(OrthographicCamera activeCamera) {
        return new Float[] {
            (x - activeCamera.position.x) * 1.77777f * activeCamera.zoom / DrawingManager.SCREEN_WIDTH,
            (y - activeCamera.position.y) * activeCamera.zoom / DrawingManager.SCREEN_HEIGHT,
            intensity
        };
    }
}
