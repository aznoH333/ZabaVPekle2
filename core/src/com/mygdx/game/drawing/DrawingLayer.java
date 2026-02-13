package com.mygdx.game.drawing;

public enum DrawingLayer {
    FLOOR(0),
    BLOOD(1),
    WALLS(2),
    DOOR(3),
    ITEMS(4),
    ENEMIES(5),
    PROJECTILES(6),
    EFFECTS(7),
    PLAYER(8),
    HAND(9),
    GUI(10);

    public final int value;


    DrawingLayer(int value) {
        this.value = value;
    }

}
