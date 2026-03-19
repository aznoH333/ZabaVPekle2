package com.mygdx.game.drawing;


public class TextDrawingCommand {
    public String text;
    public float x;
    public float y;

    public float r = 1f;
    public float g = 1f;
    public float b = 1f;
    public float a = 1f;

    public FontSize fontSize = FontSize.MEDIUM;

    public TextDrawingCommand(String text, float x, float y) {
        this.text = text;
        this.x = x;
        this.y = y;

    }

    public TextDrawingCommand setColor(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;

        return this;
    }

    public TextDrawingCommand setSize(FontSize size) {
        this.fontSize = size;
        return this;
    }
}
