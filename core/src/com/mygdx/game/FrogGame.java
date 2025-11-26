package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityManager;
import com.mygdx.game.entities.components.behaviour.PlayerSoulComponent;

public class FrogGame extends ApplicationAdapter {


	SpriteManager spriteManager;
	EntityManager entityManager;

	Entity player;

	
	@Override
	public void create () {
		spriteManager = SpriteManager.getInstance();
		spriteManager.loadSprite("player_1.png", "test");
		spriteManager.loadSprite("badlogic.jpg", "penis");
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayModes()[0]);



		entityManager = EntityManager.getInstance();

		entityManager.addEntity(new Entity()
				.setSprite("test")
				.addComponent(new PlayerSoulComponent()));

		entityManager.addEntity(new Entity()
				.setSprite("penis")
				.setX(32f)
				.setY(32f));



	}

	@Override
	public void render () {
		spriteManager.renderBegin();
		entityManager.update();

		spriteManager.render();
	}
	
	@Override
	public void dispose () {
		spriteManager.dispose();
	}

	@Override
	public void resize(int width, int height) {
		spriteManager.resizedWindow(width, height);
	}

}
