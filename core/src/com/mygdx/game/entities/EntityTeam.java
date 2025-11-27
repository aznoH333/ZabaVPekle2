package com.mygdx.game.entities;

public enum EntityTeam {
    FROG,
    DEMON,
    NONE;

    public boolean isAggressiveAgainst(EntityTeam other) {
        return other != NONE && other != this;
    }
}
