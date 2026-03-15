package com.mygdx.game.drawing;

public enum FontSize {
    SMALL(12),
    MEDIUM(16),
    DISPLAY(24);


    public final int pointSize;

    FontSize(int pointSize) {
        this.pointSize = pointSize;
    }
}
