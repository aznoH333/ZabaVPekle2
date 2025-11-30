package com.mygdx.game.entities.components.visual.particles;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;

public class FadeParticle extends EntityComponent {

    private int lifeTime;
    private final int lowPercent;
    private final int highPercent;
    private final boolean fadeIn;
    public FadeParticle(int lifeTime, boolean fadeIn, float fadePercentage) {
        this.lifeTime = lifeTime;
        this.lowPercent = (int) (lifeTime * fadePercentage);
        this.fadeIn = fadeIn;
        this.highPercent = lifeTime - lowPercent;
    }


    @Override
    public void onUpdate(Entity owner) {
        this.lifeTime--;

        if (fadeIn && lifeTime > highPercent) {
            owner.a = 1 - ((float) ((lifeTime - highPercent)) / lowPercent);
        } else if (lifeTime < lowPercent) {
            owner.a = (float) lifeTime / lowPercent;
        }

        if (lifeTime <= 0) {
            owner.commitSudoku();
        }
    }
}
