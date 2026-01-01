package com.mygdx.game.entities.facades.EnemyGeneration;

import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.handModifiers.DoubleHank;
import com.mygdx.game.entities.components.gui.EntityRunnable;

import java.util.ArrayList;

public class RangedEnemyTraits {
    public static ArrayList<Trait<EntityRunnable>> traits = new ArrayList<>();

    static {
        traits.add(new Trait<>(0.5f, 1f, (owner -> {
            owner.addComponent(new DoubleHank());
        })));
    }
}
