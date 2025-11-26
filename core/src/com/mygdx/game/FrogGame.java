package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class FrogGame extends ApplicationAdapter {


	SpriteManager spriteManager;

	
	@Override
	public void create () {
		spriteManager = SpriteManager.getInstance();
		spriteManager.loadSprite("player_1.png", "test");


		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayModes()[0]);

	}
	float a = 0f;

	@Override
	public void render () {
		spriteManager.renderBegin();

		spriteManager.drawSprite("test", 20f, 20f);

		for (float t = 0f; t < 600f; t+=4) {
			spriteManager.drawSprite("test", t, t);
			spriteManager.drawSprite("test", -t, t);
			spriteManager.drawSprite("test", t, -t);
			spriteManager.drawSprite("test", -t, -t);


		}
		spriteManager.setCameraPosition(a, 0);
		a += 0.1f;

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
