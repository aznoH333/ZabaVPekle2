package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.drawing.DrawingCommand;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.drawing.DrawingManager;
import com.mygdx.game.drawing.TextDrawingCommand;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityManager;
import com.mygdx.game.entities.EntityTeam;
import com.mygdx.game.entities.components.behaviour.PlayerSoul;
import com.mygdx.game.entities.components.behaviour.Shooter;
import com.mygdx.game.entities.components.visual.GameEntityAnimator;
import com.mygdx.game.gameStates.GameStateManager;
import com.mygdx.game.gameStates.implementations.Game;
import com.mygdx.game.gameStates.implementations.MainMenu;
import com.mygdx.game.world.WorldManager;

public class FrogGame extends ApplicationAdapter {





	@Override
	public void create () {

		Managers.drawingManager.loadSpritesInDirectory("assets/sprites");


		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayModes()[0]);


		// game states
		Managers.gameStateManager.addGameState(new Game());
		Managers.gameStateManager.addGameState(new MainMenu());
		Managers.gameStateManager.switchState("main menu");

	}

	@Override
	public void render () {
		Managers.gameStateManager.update();
	}
	
	@Override
	public void dispose () {
		Managers.drawingManager.dispose();
		Managers.soundManager.dispose();
	}

	@Override
	public void resize(int width, int height) {
		Managers.drawingManager.resizedWindow(width, height);
	}

}
