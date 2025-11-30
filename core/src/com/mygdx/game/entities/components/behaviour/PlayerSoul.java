package com.mygdx.game.entities.components.behaviour;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.NumberUtils;
import com.mygdx.game.drawing.DrawingCommand;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.drawing.SpriteManager;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.EntityManager;
import com.mygdx.game.entities.EntityTeam;

public class PlayerSoul extends EntityComponent {
    private static final SpriteManager spriteManager = SpriteManager.getInstance();
    private static final EntityManager entityManager = EntityManager.getInstance();


    private float direction = 0f;
    private int beam = 1;
    private int beamFactor = 0;
    public PlayerSoul() {
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
        Vector2 mousePos = spriteManager.getMousePosition();
        direction = NumberUtils.directionToward(
                owner.x,
                owner.y,
                mousePos.x,
                mousePos.y);


        if (beamFactor > 0) {
            beamFactor--;
        }
        // draw hand temporary
        spriteManager.drawSprite(
                new DrawingCommand("hand_000" + beam,
                        (float) Math.cos(direction) * (10f - (beamFactor / 10f) * 2f) + owner.x,
                        (float) Math.sin(direction) * (10f - (beamFactor / 10f) * 2f) + owner.y
                )
                        .setRotationRad(direction)
                        .setFlipVertically(owner.flipX)
                        .setWidth(1 + ((beamFactor / 10f) * 0.25f))
                        .setHeight(1 + ((beamFactor / 10f) * 0.25f)),
                DrawingLayer.HAND);

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            beam = ((beam) % 7) + 1;
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            beamFactor = 10;
            entityManager
                    .addEntity(new Entity()
                            .setSprite("bullet")
                            .setX(owner.x)
                            .setY(owner.y)
                            .setDamage(5f)
                            .setTeam(EntityTeam.FROG)
                            .setDrawingLayer(DrawingLayer.PROJECTILES)
                            .addComponent(new Bullet(direction)));
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
