package com.mygdx.game.entities.facades.EnemyGeneration;

import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.components.behaviour.ememy.movementAi.*;
import com.mygdx.game.utils.Trait;

import java.util.ArrayList;

public class EnemyMovementTraits {
    public static ArrayList<Trait<EntityComponent>> movementTraits = new ArrayList<>();

    static {
        movementTraits.add(new Trait<>(0.5f, 0f, 0.15f, new EnemyAimlessWanderMovement()));
        movementTraits.add(new Trait<>(0.25f, 0.0f, 0.15f, new EnemyPingPongMovement()));
        movementTraits.add(new Trait<>(0.75f, 0.15f, new EnemyStepChaseMovement()));
        movementTraits.add(new Trait<>(0.75f, 0.20f, new EnemyChaseMovement()));
        movementTraits.add(new Trait<>(0.5f, 0.15f, new EnemyCarMovement()));
    }
}
