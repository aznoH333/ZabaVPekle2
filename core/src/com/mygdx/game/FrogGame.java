package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class FrogGame extends ApplicationAdapter {


	SpriteManager spriteManager;
	Entity player;
	
	@Override
	public void create () {
		spriteManager = SpriteManager.getInstance();
		spriteManager.loadSprite("player_1.png", "test");
		spriteManager.loadSprite("badlogic.jpg", "penis");


		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayModes()[0]);
		player = new Entity()
				.setSprite("test")
				.addComponent(new PlayerSoulComponent());
	}

	@Override
	public void render () {
		spriteManager.renderBegin();

		player.update();
		spriteManager.drawSprite("penis", 0f, 0f);

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
