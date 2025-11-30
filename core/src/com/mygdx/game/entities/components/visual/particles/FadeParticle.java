package com.mygdx.game.entities.components.visual.particles;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;

public class FadeParticle extends EntityComponent {

    private int lifeTime;
    private final int lowPercent;
    public FadeParticle(int lifeTime) {
        this.lifeTime = lifeTime;
        this.lowPercent = (int) (lifeTime * 0.3f);
    }


    @Override
    public void onUpdate(Entity owner) {
        this.lifeTime--;


        if (lifeTime < lowPercent) {
            owner.a = (float) lifeTime / lowPercent;
        }

        if (lifeTime <= 0) {
            owner.commitSudoku();
        }
    }
}
