package com.mygdx.game.entities.components.behaviour;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.NumberUtils;
import com.mygdx.game.SpriteManager;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.EntityManager;
import com.mygdx.game.entities.EntityTeam;

public class PlayerSoulComponent extends EntityComponent {
    private static final SpriteManager spriteManager = SpriteManager.getInstance();
    private static final EntityManager entityManager = EntityManager.getInstance();

    public PlayerSoulComponent() {
        super.name = "soul";
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

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {

            Vector2 mousePos = spriteManager.getMousePosition();
            entityManager
                    .addEntity(new Entity()
                            .setSprite("bullet")
                            .setX(owner.x)
                            .setY(owner.y)
                            .setDamage(5f)
                            .setTeam(EntityTeam.FROG)
                            .addComponent(new BulletComponent(NumberUtils.directionToward(
                                    owner.x,
                                    owner.y,
                                    mousePos.x,
                                    mousePos.y))));
        }
    }

    @Override
    public void recalculateStats(Entity owner) {
        owner.speed = 3.5f;
        owner.flipWithMoveDirection =  true;
    }
}
