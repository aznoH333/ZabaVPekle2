package com.mygdx.game.drawing;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class LightHandle {
    public float x;
    public float y;
    public float radius;
    private int lightIndex;
    private boolean active;
    public float brightness;

    public LightHandle(float x, float y, float radius, float brightness, int lightIndex) {
        this.x = x;
        this.y = y;
        this.brightness = brightness;
        this.radius = radius;
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
     * encodes light as float[4]
     * output [0] and [1] are the lights x and y converted to screen coordinates (-1 to 1)
     * output [2] is the lights radius
     * output [3] is the lights brightness
     * */
    public Float[] convertToShaderParams(OrthographicCamera activeCamera, float aspectRatio) {
        return new Float[] {
            (x - activeCamera.position.x) * aspectRatio * activeCamera.zoom / DrawingManager.SCREEN_WIDTH,
            (y - activeCamera.position.y) * activeCamera.zoom / DrawingManager.SCREEN_HEIGHT,
            radius,
            brightness
        };
    }
}
