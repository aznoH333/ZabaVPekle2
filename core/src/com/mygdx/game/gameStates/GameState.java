package com.mygdx.game.gameStates;

public abstract class GameState {

    public String name;
    public boolean drawWorld = false;

    public GameState(String name) {
        this.name = name;
    }

    public abstract void initializeState();

    public abstract void cleanUpState();
    
    public void update() {}


}
