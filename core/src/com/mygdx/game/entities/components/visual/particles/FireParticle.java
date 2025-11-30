package com.mygdx.game.entities.components.visual.particles;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;

public class FireParticle extends EntityComponent {

    private int timer = 0;


    @Override
    public void onUpdate(Entity owner) {
        owner.y += 0.5f;
        timer++;
        owner.sprite = "fire_particle_000" + (((timer / 6) % 2 ) + 1);
    }
}
