package com.mygdx.game.gameStates.implementations;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Managers;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityTeam;
import com.mygdx.game.entities.components.behaviour.PlayerSoul;
import com.mygdx.game.entities.components.behaviour.Gun;
import com.mygdx.game.entities.components.visual.AnimatedLegsWithHat;
import com.mygdx.game.entities.facades.AugmentBoxFacade;
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
                .addComponent(new PlayerSoul())
                .setDrawingLayer(DrawingLayer.PLAYER)
                .addComponent(new AnimatedLegsWithHat(new Color(0f, 1f, 0f, 1f), new Color(1f, 0.5f, 0.5f, 1f), "hats_1"))
                .addComponent(new Gun("hands_0002"))
                .setNumericStat(FieldName.ProjectileSpeed, 0.75f)
                .setNumericStat(FieldName.FireRate, 25f)
                .setNumericStat(FieldName.ProjectileDamage, 2f)
        );

        AugmentBoxFacade.createNewBox(0f, 64f, Quality.POOR);

    }

    @Override
    public void cleanUpState() {

    }
}
