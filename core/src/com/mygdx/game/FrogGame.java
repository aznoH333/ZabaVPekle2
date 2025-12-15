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


	DrawingManager drawingManager;
	EntityManager entityManager;
	WorldManager worldManager;
	SoundManager soundManager;

	GameStateManager gameStateManager;

	@Override
	public void create () {
		gameStateManager = GameStateManager.getInstance();

		drawingManager = DrawingManager.getInstance();
		drawingManager.loadSpritesInDirectory("assets/sprites");


		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayModes()[0]);



		entityManager = EntityManager.getInstance();

		worldManager = WorldManager.getInstance();

		soundManager = SoundManager.getInstance();

		// game states
		gameStateManager.addGameState(new Game());
		gameStateManager.addGameState(new MainMenu());
		gameStateManager.switchState("main menu");

	}

	@Override
	public void render () {


		gameStateManager.update();
	}
	
	@Override
	public void dispose () {
		drawingManager.dispose();
		soundManager.dispose();
	}

	@Override
	public void resize(int width, int height) {
		drawingManager.resizedWindow(width, height);
	}

}
