package com.mygdx.game.facades.enemyGeneration;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.EntityTeam;
import com.mygdx.game.entities.components.behaviour.ememy.EnemyBaseBehaviour;
import com.mygdx.game.entities.components.behaviour.gun.Gun;
import com.mygdx.game.entities.components.gui.EntityRunnable;
import com.mygdx.game.entities.components.visual.AnimatedLegsWithHat;
import com.mygdx.game.entities.components.visual.GameEntityBleed;
import com.mygdx.game.entities.components.visual.LegsWithHatType;
import com.mygdx.game.entities.fields.FieldName;
import com.mygdx.game.utils.Trait;
import com.mygdx.game.utils.TraitPicker;
import com.mygdx.game.utils.types.NumberUtils;

import java.util.ArrayList;


/**
 * This utility class is responsible for simplifying enemy generation (The process that creates new enemy variants)
 * <p>
 * Some broader concepts behind the system:
 * When generating a new enemy type the system takes into account 3 variables
 * </p>
 * <ul>
 *     <li>
 *         Toughness (scale 0 to 1) - how much health should the final enemy have
 *     </li>
 *     <li>
 *        Mobility (scale 0 to 1) - How fast/ how often should the enemy move
 *     </li>
 *     <li>
 *         Threat (scale 0 to 1) - How dangerous are the attacks of this enemy (Number of projectiles, Projectile path, damage)
 *         may give special abilities like dashing to some enemies
 *     </li>
 * </ul>
 * Additionally the system takes into account a specified difficulty (1 to infinity) <br>
 * The system tries to generate a somewhat reasonable enemy distribution based on the input difficulty.
 * Most places will generate with
 * <ol>
 *     <li>tiny fodder enemy (low stats)</li>
 *     <li>a somewhat common generalist enemy (average stats)</li>
 * </ol>
 * <p>
 * Specified difficulty scales linearly - (diff jump from 3 to 4 is the same as from 2 to 3)
 * The system is intended to have the first place always generate with diff 1
 */
public class EnemyGeneratorFacade {
    /**
     * Generates a new enemy roster (enemies to spawn in rooms).
     * Each roster is intended to last for 1 place.
     * Always generates a small fodder enemy
     * And a low threat generalist enemy
     *
     * @param specializedEnemyCount
     * @param placeDifficulty
     * @return
     */
    public static ArrayList<Trait<Entity>> generateEnemyRoster(int specializedEnemyCount, float placeDifficulty) {
        ArrayList<Trait<Entity>> enemyRoster = new ArrayList<>();


        // small fodder
        enemyRoster.add(
            new Trait<>(
                1f,
            0.5f,
            generateEnemyType(
                new EnemyGenerationBase(
                    NumberUtils.randomFloat(0f, 0.2f),
                    NumberUtils.randomFloat(0.4f, 0.6f),
                    0.2f,
                    placeDifficulty
                )
            ))
        );

        // generic fodder enemy
        enemyRoster.add(
            new Trait<>(

                1f,
                0.75f,
                generateEnemyType(
                    new EnemyGenerationBase(
                        NumberUtils.randomFloat(0.2f, 0.4f),
                        NumberUtils.randomFloat(0.3f, 0.5f),
                        placeDifficulty > 3.2f ? 0.45f : 0.3f, // this code is here to make sure that basic ranged enemies don't generate on first 2 floors
                        placeDifficulty
                    )

                )
            )

        );

        for (int i = 0; i < specializedEnemyCount; i++) {

            EnemyGenerationBase base = new EnemyGenerationBase(0f, 0f, 0f, placeDifficulty);

            float perksBudget = placeDifficulty > 4.5 ? 1.75f : 1f;

            TraitPicker<EnemyGenerationRunnable> generationBasePicker = new TraitPicker<>(EnemyTraitPicker.traits, perksBudget);

            while (generationBasePicker.hasBudget()) {
                Trait<EnemyGenerationRunnable> trait = generationBasePicker.pickTrait();

                trait.traitValue.run(base);
                base.normalize();
            }
            enemyRoster.add(
                new Trait<>(
                    1f,
                    (base.threat * 0.75f) + (base.mobility * 0.75f) + (base.toughness * 0.75f),
                    generateEnemyType(
                        base
                    )
                )

            );
        }


        return enemyRoster;
    }


    private static Entity generateEnemyType(EnemyGenerationBase base) {

        boolean isTurret = false;
        boolean hasRangedAttack = false;
        float size = 1f;
        float additionalHeight = 1f;
        float additionalWidth = 1f;

        float speed;
        float health;


        // decide if ranged
        if (base.threat > 0.35f && NumberUtils.randomChance(0.6f)) {
            hasRangedAttack = true;
        }

        // return new Entity();

        // generate speed
        if (base.mobility < 0.2f && NumberUtils.randomChance(0.75f)) {
            // immobile enemy (turret)
            speed = 0f;
            isTurret = true;
            hasRangedAttack = true;
        } else {
            speed = 0.5f + (base.mobility);

            if (!hasRangedAttack && base.threat > 0.5f) {
                speed += base.threat * 1.2f;
            }

            if (base.toughness > 0.8f && base.mobility > 0.9f) {
                speed *= 0.85f; // nerf enemies that would be too tanky and mobile
            }
        }


        // generate health
        health = base.placeDifficulty * (2f + (base.toughness * 10f));

        // size
        if (base.toughness < 0.35f) {
            size -= 0.05f;
        }
        if (base.toughness < 0.2f && base.threat < 0.3f) {
            size -= 0.05f;
        }
        if (base.toughness > 0.6f) {
            size += (base.toughness - 0.6f) * 2f;
        }
        // width height
        if (base.toughness > 0.6f) {
            additionalWidth += (base.toughness - 0.6f) * 2f;
        }
        if (base.threat > 0.6f) {
            additionalHeight += (base.threat - 0.6f) * 2f;
        }

        // pick movement
        TraitPicker<EntityComponent> movementPicker = new TraitPicker<>(EnemyMovementTraits.movementTraits, base.mobility);
        EntityComponent movementAi = movementPicker.pickValue().copy();

        Entity entity = new Entity()
            .setTeam(EntityTeam.ENEMY)
            .setNumericStat(FieldName.Health, health)
            .setNumericStat(FieldName.Speed, speed)
            .addComponent(new EnemyBaseBehaviour())
            .addComponent(new AnimatedLegsWithHat(LegsWithHatType.ENEMY_MEDIUM, new Color(1f, 1f, 1f, 1f), new Color(1f, 0.5f, 0.5f, 1f), "small_enemy_heads_" + NumberUtils.randomInt(1, 9)))
            .addComponent(new GameEntityBleed())
            .addComponent(movementAi)
            .setScaleX(size * additionalWidth)
            .setScaleY(size * additionalHeight)
            .setNumericStat(FieldName.Damage, 1f)
            .setDrawingLayer(DrawingLayer.ENEMIES);



        if (hasRangedAttack) {
            float rangedAttackPowerScale = (1f + base.threat) * base.placeDifficulty;


            TraitPicker<EntityRunnable> rangedAttackTraitPicker = new TraitPicker<>(RangedEnemyTraits.traits, rangedAttackPowerScale);

            entity.setNumericStat(FieldName.ProjectileLifeTime, 60f);
            entity.setNumericStat(FieldName.ProjectileSpread, 0f);
            entity.setNumericStat(FieldName.ProjectileSpeed, 0.45f);
            entity.setNumericStat(FieldName.FireRate, 120f);
            entity.setNumericStat(FieldName.FireRateMultiplier, 1.5f);

            entity.addComponent(
                new Gun("guns_0006")
            );

            while (rangedAttackTraitPicker.hasBudget()) {
                Trait<EntityRunnable> trait = rangedAttackTraitPicker.pickTrait();
                trait.traitValue.run(entity);
            }
        }

        return entity;
    }


}
