package com.mygdx.game.entities.facades.EnemyGeneration;

public class Trait<T> {
    public final float chanceToBePicked;
    public final float cost;
    public final float budgetCeiling;
    public final T traitValue;

    public Trait(
        float chanceToBePicked,
        float cost,
        T traitValue
    ) {
        this.chanceToBePicked = chanceToBePicked;
        this.cost = cost;
        this.traitValue = traitValue;
        this.budgetCeiling = 0f;
    }

    public Trait(
        float chanceToBePicked,
        float cost,
        float costCeiling,
        T traitValue
    ) {
        this.chanceToBePicked = chanceToBePicked;
        this.cost = cost;
        this.budgetCeiling = costCeiling;
        this.traitValue = traitValue;
    }
}
