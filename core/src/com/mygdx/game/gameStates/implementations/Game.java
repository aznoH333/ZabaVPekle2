package com.mygdx.game.gameStates.implementations;

import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityManager;
import com.mygdx.game.entities.EntityTeam;
import com.mygdx.game.entities.components.behaviour.PlayerSoul;
import com.mygdx.game.entities.components.behaviour.Shooter;
import com.mygdx.game.entities.components.visual.GameEntityAnimator;
import com.mygdx.game.gameStates.GameState;

public class Game extends GameState {
    private static final EntityManager entityManager = EntityManager.getInstance();

    public Game() {
        super("game");
        super.drawWorld = true;
    }

    @Override
    public void initializeState() {
        entityManager.addEntity(new Entity()
                .setSprite("player_1")
                .setTeam(EntityTeam.FROG)
                .addComponent(new PlayerSoul())
                .setDrawingLayer(DrawingLayer.PLAYER)
                .addComponent(new GameEntityAnimator("player", 1, 2, 8, 9, 3))
                .addComponent(new Shooter("hand_0001"))
        );
    }

    @Override
    public void cleanUpState() {

    }
}
