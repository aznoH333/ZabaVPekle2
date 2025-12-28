package com.mygdx.game.entities.components.gui;

import com.mygdx.game.entities.Entity;

/**
 * A variation of runnable that takes an entity as its argument
 * @see Runnable
 * */
public interface EntityRunnable {
    void run(Entity owner);
}
