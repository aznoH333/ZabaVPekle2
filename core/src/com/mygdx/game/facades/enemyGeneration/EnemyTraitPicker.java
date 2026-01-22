package com.mygdx.game.facades.enemyGeneration;

import com.mygdx.game.utils.Trait;

import java.util.ArrayList;

public class EnemyTraitPicker {

    /**
     * This is super hacked together. But it kind of works.
     * Improvement ideas:
     * <ul>
     *     <li>Make enemies more likely to generate with one stat stronger than the others. </li>
     *     <li>Turn enemy generation base into a dictionary so this definition is less repetitive/more programmatic </li>
     * </ul>
     */
    public static final ArrayList<Trait<EnemyGenerationRunnable>> traits = new ArrayList<>();


    static {
        traits.add(new Trait<>(0.25f, 0.1f, (it) -> {
            it.threat += 0.1f;
        }));
        traits.add(new Trait<>(0.25f, 0.1f, (it) -> {
            it.toughness += 0.1f;
        }));
        traits.add(new Trait<>(0.25f, 0.1f, (it) -> {
            it.mobility += 0.1f;
        }));

        traits.add(new Trait<>(0.5f, 0.25f, (it) -> {
            it.threat += 0.30f;
        }));
        traits.add(new Trait<>(0.5f, 0.25f, (it) -> {
            it.toughness += 0.30f;
        }));
        traits.add(new Trait<>(0.5f, 0.25f, (it) -> {
            it.mobility += 0.30f;
        }));

        traits.add(new Trait<>(0.75f, 0.65f, (it) -> {
            it.threat += 0.50f;
        }));
        traits.add(new Trait<>(0.75f, 0.65f, (it) -> {
            it.toughness += 0.50f;
        }));
        traits.add(new Trait<>(0.75f, 0.65f, (it) -> {
            it.mobility += 0.50f;
        }));

        traits.add(new Trait<>(1f, 0.5f, (it) -> {
            it.threat += 0.50f;
            it.toughness -= 0.1f;
            it.mobility -= 0.1f;
        }));
        traits.add(new Trait<>(1f, 0.5f, (it) -> {
            it.toughness += 0.50f;
            it.threat -= 0.1f;
            it.mobility -= 0.1f;
        }));
        traits.add(new Trait<>(1f, 0.5f, (it) -> {
            it.mobility += 0.50f;
            it.toughness -= 0.1f;
            it.threat -= 0.1f;
        }));

    }
}
