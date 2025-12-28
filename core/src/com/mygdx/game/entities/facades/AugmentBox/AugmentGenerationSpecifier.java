package com.mygdx.game.entities.facades.AugmentBox;

import com.mygdx.game.entities.EntityComponent;

public class AugmentGenerationSpecifier {
    public final float rarity;
    public final EntityComponent component;

    public AugmentGenerationSpecifier(float rarity, EntityComponent component) {
        this.rarity = rarity;
        this.component = component;
    }

}
