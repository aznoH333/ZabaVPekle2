package com.mygdx.game.gameStates.implementations;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.SoundManager;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.EntityManager;
import com.mygdx.game.entities.EntityTeam;
import com.mygdx.game.entities.components.behaviour.PlayerSoul;
import com.mygdx.game.entities.components.behaviour.Shooter;
import com.mygdx.game.entities.components.behaviour.projectile.Shrapnel;
import com.mygdx.game.entities.components.behaviour.projectile.SineTravel;
import com.mygdx.game.entities.components.gui.Button;
import com.mygdx.game.entities.components.gui.Hover;
import com.mygdx.game.entities.components.gui.Text;
import com.mygdx.game.entities.components.visual.AnimatedLegsWithHat;
import com.mygdx.game.entities.components.visual.GameEntityAnimator;
import com.mygdx.game.entities.facades.AugmentBoxFacade;
import com.mygdx.game.entities.facades.GUIFacade;
import com.mygdx.game.entities.items.Augment;
import com.mygdx.game.entities.items.Quality;
import com.mygdx.game.gameStates.GameState;

import java.util.ArrayList;

public class Game extends GameState {
    private static final EntityManager entityManager = EntityManager.getInstance();
    private static final SoundManager soundManager = SoundManager.getInstance();

    public Game() {
        super("game");
        super.drawWorld = true;
    }

    @Override
    public void initializeState() {

        Entity player = entityManager.addEntity(new Entity()
                .setTeam(EntityTeam.FROG)
                .addComponent(new PlayerSoul())
                .setDrawingLayer(DrawingLayer.PLAYER)
                .addComponent(new AnimatedLegsWithHat(new Color(0f, 1f, 0f, 1f), new Color(1f, 0.5f, 0.5f, 1f), "hats_1"))
                .addComponent(new Shooter("hands_0002"))
        );


        AugmentBoxFacade.createNewBox(
                128f,
                128f,
                Quality.POOR
        );
    }

    @Override
    public void cleanUpState() {

    }
}
