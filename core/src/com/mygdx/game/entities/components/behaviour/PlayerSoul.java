package com.mygdx.game.entities.components.behaviour;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.NumberUtils;
import com.mygdx.game.drawing.SpriteManager;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;

public class PlayerSoul extends EntityComponent {
    private static final SpriteManager spriteManager = SpriteManager.getInstance();

    private Shooter shooter = null;


    public PlayerSoul() {
        super.name = "soul";
    }


    @Override
    public void onAnyComponentAttachedToEntity(Entity owner) {
        shooter = (Shooter) owner.getComponentByName("shooter");
    }

    @Override
    public void onUpdate(Entity owner) {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            owner.walk(-1f, 0f);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            owner.walk(1f, 0f);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            owner.walk(0f, 1f);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            owner.walk(0f, -1f);
        }

        // set camera
        spriteManager.setCameraPosition(owner.x, owner.y);


        if (shooter != null) {
            Vector2 mousePos = spriteManager.getMousePosition();

            shooter.direction = NumberUtils.directionToward(
                    owner.x,
                    owner.y,
                    mousePos.x,
                    mousePos.y);

            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                shooter.shoot(owner);
            }
        }


    }

    @Override
    public void recalculateStats(Entity owner) {
        owner.speed = 3.5f;
        owner.flipWithMoveDirection =  true;
        owner.health = 6f;
        owner.canBeDamaged = true;
    }
}
