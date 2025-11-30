package com.mygdx.game.drawing;

public class DrawingCommand {
    String spriteName;
    float x;
    float y;
    float width = 1f;
    float height = 1f;
    boolean flipHorizontally = false;
    boolean flipVertically = false;
    float rotationRad = 0f;
    float r = 1f;
    float g = 1f;
    float b = 1f;
    float a = 1f;

    public DrawingCommand(String spriteName, float x, float y) {
        this.spriteName = spriteName;
        this.x = x;
        this.y = y;
    }


    public DrawingCommand setWidth(float width) {
        this.width = width;
        return this;
    }

    public DrawingCommand setHeight(float height) {
        this.height = height;
        return this;
    }

    public DrawingCommand setFlipHorizontally(boolean flipHorizontally) {
        this.flipHorizontally = flipHorizontally;
        return this;
    }

    public DrawingCommand setFlipVertically(boolean flipVertically) {
        this.flipVertically = flipVertically;
        return this;
    }

    public DrawingCommand setRotationRad(float rotationRad) {
        this.rotationRad = rotationRad;
        return this;
    }

    public DrawingCommand setR(float r) {
        this.r = r;
        return this;
    }

    public DrawingCommand setG(float g) {
        this.g = g;
        return this;
    }

    public DrawingCommand setB(float b) {
        this.b = b;
        return this;
    }

    public DrawingCommand setA(float a) {
        this.a = a;
        return this;
    }

}
