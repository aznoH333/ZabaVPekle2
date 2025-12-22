package com.mygdx.game;

import com.mygdx.game.drawing.DrawingManager;
import com.mygdx.game.entities.EntityManager;
import com.mygdx.game.gameStates.GameStateManager;
import com.mygdx.game.world.WorldManager;

public class Managers {
    public static DrawingManager drawingManager = DrawingManager.getInstance();
    public static EntityManager entityManager = EntityManager.getInstance();
    public static WorldManager worldManager = WorldManager.getInstance();
    public static SoundManager soundManager = SoundManager.getInstance();
    public static GameStateManager gameStateManager = GameStateManager.getInstance();
}
