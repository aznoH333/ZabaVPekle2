package com.mygdx.game.entities.facades.EnemyGeneration;

public class Trait<T> {
    public final float chanceToBePicked;
    public final float cost;
    public final T trait;

    public Trait(
        float chanceToBePicked,
        float cost,
        T trait
    ) {
        this.chanceToBePicked = chanceToBePicked;
        this.cost = cost;
        this.trait = trait;
    }
}
