package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.drawing.DrawingManager;
import com.mygdx.game.drawing.TextDrawingCommand;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityManager;
import com.mygdx.game.entities.EntityTeam;
import com.mygdx.game.entities.components.behaviour.PlayerSoul;
import com.mygdx.game.entities.components.behaviour.Shooter;
import com.mygdx.game.entities.components.visual.GameEntityAnimator;
import com.mygdx.game.world.WorldManager;

public class FrogGame extends ApplicationAdapter {


	DrawingManager drawingManager;
	EntityManager entityManager;
	WorldManager worldManager;
	SoundManager soundManager;


	@Override
	public void create () {
		drawingManager = DrawingManager.getInstance();
		drawingManager.loadSpritesInDirectory("assets/sprites");


		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayModes()[0]);



		entityManager = EntityManager.getInstance();

		entityManager.addEntity(new Entity()
				.setSprite("player_1")
				.setTeam(EntityTeam.FROG)
				.addComponent(new PlayerSoul())
				.setDrawingLayer(DrawingLayer.PLAYER)
				.addComponent(new GameEntityAnimator("player", 1, 2, 8, 9, 3))
				.addComponent(new Shooter("hand_0001"))
		);






		worldManager = WorldManager.getInstance();

		soundManager = SoundManager.getInstance();

	}

	@Override
	public void render () {
		drawingManager.drawText(new TextDrawingCommand("hjelapwe", 0f, 0f));


		worldManager.draw();
		worldManager.update();
		entityManager.update();

		drawingManager.render();
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
