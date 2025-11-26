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

		// Example of setting fullscreen in LibGDX
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayModes()[0]);
	}

	@Override
	public void render () {
		spriteManager.renderBegin();

		spriteManager.drawSprite("test", 20f, 20f);

		spriteManager.render();
	}
	
	@Override
	public void dispose () {
		spriteManager.dispose();
	}
}
