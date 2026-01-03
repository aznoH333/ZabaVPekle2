package com.mygdx.game.entities.facades.EnemyGeneration;


import com.mygdx.game.utils.types.NumberUtils;

/**
 * A helper class used to hold values that are logically related
 */
public class EnemyGenerationBase {
    /**
     * A value from 0 to 1 that represents the generated enemy's toughness (health)
     */
    public float toughness;
    /**
     * A value from 0 to 1 that represents the generated enemy's speed. May result in additional (dashing, teleporting)
     */
    public float mobility;
    /**
     * A value from 0 to 1 that represents the generated enemy's threat. High threat enemies might generate with ranged attacks.
     */
    public float threat;

    /**
     * The difficulty value of the place where the enemy appears. Value from 1 to infinity
     */
    public float placeDifficulty;

    public EnemyGenerationBase(float toughness, float mobility, float threat, float placeDifficulty) {
        this.toughness = toughness;
        this.mobility = mobility;
        this.threat = threat;

        this.placeDifficulty = placeDifficulty;
    }


    public void normalize() {
        this.toughness = NumberUtils.clampValue(toughness, 0f, 1f);
        this.mobility = NumberUtils.clampValue(mobility, 0f, 1f);
        this.threat = NumberUtils.clampValue(threat, 0f, 1f);
    }

}
