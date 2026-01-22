package com.mygdx.game.entities.components.behaviour;

import com.mygdx.game.entities.ComponentName;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.facades.augmentBox.AugmentBoxFacade;
import com.mygdx.game.entities.items.Quality;

public class AugmentBox extends EntityComponent {


    private final Quality quality;

    public AugmentBox(Quality quality) {
        this.quality = quality;
    }

    @Override
    public void onCollide(Entity owner, Entity other) {
        if (other.getComponentByName(ComponentName.PLAYER) != null) {
            owner.commitSudoku();
            AugmentBoxFacade.openNewBox(other, quality);
        }
    }
}
