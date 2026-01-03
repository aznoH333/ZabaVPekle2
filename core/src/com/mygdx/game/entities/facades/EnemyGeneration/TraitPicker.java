package com.mygdx.game.entities.facades.EnemyGeneration;

import com.mygdx.game.utils.NumberUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * A weighted collection of traits.
 * Picks traits based on available budget and chance.
 */
public class TraitPicker<T> {
    private final ArrayList<Trait<T>> traits;
    private float budget;

    /**
     * Crates a new trait picker.
     *
     * @param traits a list of traits which can be picked.
     * @param budget how much budget can be spent on traits. When a trait is picked its cost is subtracted from budget.
     */
    public TraitPicker(ArrayList<Trait<T>> traits, float budget) {
        this.traits = traits;
        this.budget = budget;
    }

    private List<Trait<T>> getTraitsInBudget() {
        return traits.stream().filter((it) -> it.cost <= this.budget && (it.budgetCeiling == 0f || it.budgetCeiling > this.budget)).toList();
    }

    /**
     * @return if the picker can use its budget to buy at least one trait.
     */
    public boolean hasBudget() {
        return !getTraitsInBudget().isEmpty();
    }


    /**
     * Picks a random trait in budget and returns it. The traits cost is subtracted from the budget.
     *
     * @return a random trait.
     * @throws IllegalStateException if there are no traits to pick within the specified budget.
     */
    public Trait<T> pickTrait() {
        List<Trait<T>> traitsWithinBudget = getTraitsInBudget();

        if (traitsWithinBudget.isEmpty()) {
            throw new IllegalStateException();
        }

        float cumulativeChance = 0f;
        for (Trait<T> trait : traitsWithinBudget) {
            cumulativeChance += trait.chanceToBePicked;
        }

        float pickedTraitChance = NumberUtils.randomFloat(0f, cumulativeChance);
        float summedChance = 0f;
        for (Trait<T> trait : traitsWithinBudget) {
            if (trait.chanceToBePicked + summedChance >= pickedTraitChance) {

                budget -= trait.cost;
                return trait;
            }

            summedChance += trait.chanceToBePicked;
        }

        throw new NullPointerException();
    }

    /**
     * Same as pickTrait but returns the trait value instead of the trait instance.
     * @return the picked trait value
     * @see #pickTrait()
     */
    public T pickValue() {
        return pickTrait().traitValue;
    }
}
