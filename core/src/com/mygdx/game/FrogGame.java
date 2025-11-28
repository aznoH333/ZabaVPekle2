package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityManager;
import com.mygdx.game.entities.EntityTeam;
import com.mygdx.game.entities.components.behaviour.DemonSoulComponent;
import com.mygdx.game.entities.components.behaviour.PlayerSoulComponent;

public class FrogGame extends ApplicationAdapter {


	SpriteManager spriteManager;
	EntityManager entityManager;
	WorldManager worldManager;


	@Override
	public void create () {
		spriteManager = SpriteManager.getInstance();
		spriteManager.loadSprite("player_1.png", "test");
		spriteManager.loadSprite("badlogic.jpg", "penis");
		spriteManager.loadSprite("brick_wall.png", "wall");
		spriteManager.loadSprite("floor_tile.png", "floor");
		spriteManager.loadSprite("Enemy_1.png", "enemy");
		spriteManager.loadSprite("bullet.png", "bullet");
		spriteManager.loadSpritesInDirectory("assets/sprites");


		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayModes()[0]);



		entityManager = EntityManager.getInstance();

		entityManager.addEntity(new Entity()
				.setSprite("player_1")
				.setTeam(EntityTeam.FROG)
				.addComponent(new PlayerSoulComponent()));



		entityManager.addEntity(new Entity()
				.setSprite("enemy")
				.setTeam(EntityTeam.DEMON)
				.setHealth(20f)
				.setX(320f)
				.setY(20f)
				.addComponent(new DemonSoulComponent()));


		worldManager = WorldManager.getInstance();

	}

	@Override
	public void render () {
		spriteManager.renderBegin();

		worldManager.draw();

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
