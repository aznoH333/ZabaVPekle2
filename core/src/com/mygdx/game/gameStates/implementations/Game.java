package com.mygdx.game.gameStates.implementations;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Managers;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityTeam;
import com.mygdx.game.entities.components.behaviour.PlayerBehaviour;
import com.mygdx.game.entities.components.behaviour.gun.Gun;
import com.mygdx.game.entities.components.visual.AnimatedLegsWithHat;
import com.mygdx.game.entities.components.visual.AttachedLight;
import com.mygdx.game.entities.components.visual.EyeCursor;
import com.mygdx.game.entities.components.visual.LegsWithHatType;
import com.mygdx.game.entities.facades.AugmentBox.AugmentBoxFacade;
import com.mygdx.game.entities.fields.FieldName;
import com.mygdx.game.entities.items.Quality;
import com.mygdx.game.gameStates.GameState;

public class Game extends GameState {


    public Game() {
        super("game");
        super.drawWorld = true;
    }

    @Override
    public void initializeState() {

        Managers.worldManager.restart();

        Managers.entityManager.addEntity(new Entity()
            .setTeam(EntityTeam.FROG)
            .addComponent(new PlayerBehaviour())
            .setDrawingLayer(DrawingLayer.PLAYER)
            .addComponent(new AnimatedLegsWithHat(LegsWithHatType.PLAYER, new Color(1f, 1f, 1f, 1f), new Color(1f, 0.8f, 0.8f, 1f), null))
            .addComponent(new Gun("guns_0001"))
                .addComponent(new EyeCursor(4.5f, 2f))
            .setNumericStat(FieldName.ProjectileSpeed, 0.75f)
            .setNumericStat(FieldName.FireRate, 25f)
            .setNumericStat(FieldName.ProjectileDamage, 2f)
            .addComponent(new AttachedLight(0.75f, 1.0f))
        );


        Managers.entityManager.addEntity(new Entity()
            .setTeam(EntityTeam.FROG)
            .setDrawingLayer(DrawingLayer.PLAYER)
            .setX(64)
            .addComponent(new AnimatedLegsWithHat(LegsWithHatType.ENEMY_MEDIUM, new Color(1f, 1f, 1f, 1f), new Color(1f, 0.8f, 0.8f, 1f), "small_enemy_heads_1"))
        );

        Managers.entityManager.addEntity(new Entity()
            .setTeam(EntityTeam.FROG)
            .setDrawingLayer(DrawingLayer.PLAYER)
            .setX(-64)
            .setSprite("world_0011")
        );


        AugmentBoxFacade.createNewBox(0f, 64f, Quality.POOR);

    }

    @Override
    public void cleanUpState() {

    }
}
