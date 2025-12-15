package com.mygdx.game.gameStates;

import com.mygdx.game.drawing.DrawingManager;
import com.mygdx.game.drawing.TextDrawingCommand;
import com.mygdx.game.entities.EntityManager;
import com.mygdx.game.gameStates.implementations.Game;
import com.mygdx.game.gameStates.implementations.MainMenu;
import com.mygdx.game.world.WorldManager;

import java.util.HashMap;

public class GameStateManager {


    private static final DrawingManager drawingManager = DrawingManager.getInstance();
    private static final EntityManager entityManager = EntityManager.getInstance();
    private static final WorldManager worldManager = WorldManager.getInstance();

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
        entityManager.clearAllEntities();
        drawingManager.setCameraPosition(0f, 0f);

        currentState.initializeState();
    }


    public void update() {
        drawingManager.drawText(new TextDrawingCommand(currentState.name, 0f, 200f));
        if (currentState.drawWorld) {
            worldManager.draw();
            worldManager.update();
        }
        entityManager.update();
        drawingManager.render();
    }
}
