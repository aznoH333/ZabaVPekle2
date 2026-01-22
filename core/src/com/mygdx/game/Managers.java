package com.mygdx.game;

import com.mygdx.game.drawing.DrawingManager;
import com.mygdx.game.entities.EntityManager;
import com.mygdx.game.gameStates.GameStateManager;
import com.mygdx.game.playState.PlayStateManager;
import com.mygdx.game.level.LevelManager;

public class Managers {
    public static DrawingManager drawingManager = DrawingManager.getInstance();
    public static EntityManager entityManager = EntityManager.getInstance();
    public static LevelManager levelManager = LevelManager.getInstance();
    public static SoundManager soundManager = SoundManager.getInstance();
    public static GameStateManager gameStateManager = GameStateManager.getInstance();
    public static PlayStateManager playStateManager = PlayStateManager.getInstance();
}
