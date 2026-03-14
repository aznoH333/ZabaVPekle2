package com.mygdx.game.gameStates.implementations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.Managers;
import com.mygdx.game.facades.enemyGeneration.EnemyGeneratorFacade;
import com.mygdx.game.facades.entities.WorldInteractableFacade;
import com.mygdx.game.gameStates.GameState;

public class Game extends GameState {


    public Game() {
        super("game");
        super.drawWorld = true;
    }

    @Override
    public void initializeState() {


    }

    @Override
    public void cleanUpState() {

    }
    
    @Override
    public void update() {
        Managers.playStateManager.gameTime++;


        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            Managers.entityManager.addEntity(
                    EnemyGeneratorFacade.generateBossEnemy(
                            1f,
                            10f
                    )
            );
        }
    }
}
