package com.mygdx.game.entities.components.behaviour;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.NumberUtils;
import com.mygdx.game.drawing.SpriteManager;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.stats.Stat;

public class PlayerSoul extends EntityComponent {
    private static final SpriteManager spriteManager = SpriteManager.getInstance();

    private Shooter shooter = null;


    public PlayerSoul() {
        super.name = "soul";
    }


    @Override
    public void onComponentAttached(Entity owner) {
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
        owner.overrideDefault(Stat.Speed, 3.5f, 1f);
        owner.flipWithMoveDirection =  true;
        owner.overrideDefault(Stat.MaxHealth, 6f, 1f);
        owner.canBeDamaged = true;
        owner.addStat(Stat.Health, 6666666f);
    }
}
