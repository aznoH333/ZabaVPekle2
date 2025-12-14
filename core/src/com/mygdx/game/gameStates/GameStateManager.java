package com.mygdx.game.gameStates;

import com.mygdx.game.drawing.DrawingManager;
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



    private HashMap<String, GameState> states = new HashMap<>();
    private GameState currentState;

    public GameStateManager() {
        addGameState(new Game());
        addGameState(new MainMenu());

        currentState = states.get("main menu");
        currentState.initializeState();
    }

    private void addGameState(GameState state) {
        states.put(state.name, state);
    }


    public void switchState(String newState) {

        System.out.println("switching state from " + currentState.name + " to " + newState);
        currentState.cleanUpState();

        currentState = states.get(newState);
        entityManager.clearAllEntities();
        drawingManager.setCameraPosition(0f, 0f);

        currentState.initializeState();
    }


    public void update() {
        System.out.println(currentState.name + " , " + currentState.drawWorld);
        if (currentState.drawWorld) {
            worldManager.draw();
            worldManager.update();
        }
        entityManager.update();
        drawingManager.render();
    }
}
