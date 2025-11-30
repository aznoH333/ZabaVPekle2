package com.mygdx.game.drawing;

public enum DrawingLayer {
    WORLD(0),
    BLOOD(1),
    ENEMIES(2),
    PROJECTILES(3),
    PLAYER(4);

    public final int value;

    DrawingLayer(int value) {
        this.value = value;
    }

}
