package com.mygdx.game.entities.facades;

import com.mygdx.game.entities.EntityComponent;

public class AugmentGenerationSpecifier {
    public final float rarity;
    public final EntityComponent component;
    public final boolean intendedForProjectile;

    public AugmentGenerationSpecifier(float rarity, EntityComponent component, boolean intendedForProjectile) {
        this.rarity = rarity;
        this.component = component;
        this.intendedForProjectile = intendedForProjectile;
    }
}
