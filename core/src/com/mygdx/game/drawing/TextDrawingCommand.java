package com.mygdx.game.drawing;

public class TextDrawingCommand {
    public String text;
    public float x;
    public float y;

    public TextDrawingCommand(String text, float x, float y) {
        this.text = text;
        this.x = x;
        this.y = y;
    }
}
