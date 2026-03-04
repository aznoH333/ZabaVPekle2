package com.mygdx.game.entities.components.behaviour.ememy.actionAi;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.components.behaviour.gun.Gun;

public abstract class EnemyCombatBehaviour {

    private final int duration;

    public EnemyCombatBehaviour(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public abstract void act(Entity owner);

    public abstract EnemyCombatBehaviour copy();
}
