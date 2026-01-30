package com.mygdx.game.gameStates;

import com.mygdx.game.Managers;
import com.mygdx.game.drawing.TextDrawingCommand;
import com.mygdx.game.facades.sceen.VisualEffectsFacade;

import java.util.HashMap;

public class GameStateManager {


    private static GameStateManager instance;

    public static GameStateManager getInstance() {
        if (instance == null) {
            instance = new GameStateManager();
        }

        return instance;
    }


    private final HashMap<String, GameState> states = new HashMap<>();
    private GameState currentState;

    private GameStateManager() {
    }

    public void addGameState(GameState state) {
        states.put(state.name, state);
    }


    public void switchState(String newState) {

        if (currentState != null) {
            currentState.cleanUpState();
        }

        currentState = states.get(newState);
        Managers.entityManager.clearAllEntities();
        Managers.drawingManager.setCameraPosition(0f, 0f);
        VisualEffectsFacade.clearAllLights();

        currentState.initializeState();
    }


    public void update() {
        currentState.update();
        
        Managers.drawingManager.drawText(new TextDrawingCommand(currentState.name, 0f, 200f));
        if (currentState.drawWorld) {
            Managers.levelManager.draw();
            Managers.levelManager.update();
        }
        Managers.entityManager.update();
        Managers.drawingManager.mainRenderLoop();
    }
}
