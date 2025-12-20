package com.mygdx.game.drawing;

public enum DrawingLayer {
    FLOOR(0),
    BLOOD(1),
    WALLS(2),
    DOOR(3),
    ENEMIES(4),
    PROJECTILES(5),
    EFFECTS(6),
    PLAYER(7),
    HAND(8),
    GUI(9);

    public final int value;


    DrawingLayer(int value) {
        this.value = value;
    }

}
