package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class PlayerSoulComponent extends EntityComponent{

    @Override
    public void onUpdate(Entity owner) {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            owner.x -= 1f;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            owner.x += 1f;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            owner.y += 1f;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            owner.y -= 1f;
        }
    }
}
