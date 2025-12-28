package com.mygdx.game.entities.components.behaviour;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.facades.AugmentBox.AugmentBoxFacade;
import com.mygdx.game.entities.items.Quality;

public class AugmentBox extends EntityComponent {


    private final Quality quality;

    public AugmentBox(Quality quality) {
        this.quality = quality;
    }

    @Override
    public void onCollide(Entity owner, Entity other) {
        if (other.getComponentByName("soul") != null) {
            owner.commitSudoku();
            AugmentBoxFacade.openNewBox(other, quality);
        }
    }
}
