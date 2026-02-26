package com.mygdx.game.facades.enemyGeneration;


import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.gunBehaviourModifiers.MachineGunAugment;
import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.gunBehaviourModifiers.ShotGunAugment;
import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.handModifiers.TripleHank;
import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.shotBehaviour.BoomerangShotAugment;
import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.shotBehaviour.ShrapnelShotAugment;
import com.mygdx.game.entities.components.behaviour.ememy.movementAi.EnemyAimlessWanderMovement;
import com.mygdx.game.entities.components.behaviour.ememy.movementAi.EnemyChaseMovement;
import com.mygdx.game.entities.components.behaviour.ememy.movementAi.EnemyStepChaseMovement;
import com.mygdx.game.entities.components.gui.EntityRunnable;
import com.mygdx.game.entities.fields.FieldName;
import com.mygdx.game.entities.items.Quality;
import com.mygdx.game.utils.Trait;
import com.mygdx.game.utils.TraitPicker;
import com.mygdx.game.utils.types.NumberUtils;

import java.util.ArrayList;

/**
 * A helper class used to group all values that are needed to generate a new enemy.
 */
public class EnemyGenerationBase {

    private final float placeDifficulty;
    private final EnemyArchetype archetype;

    private final float speed;
    private final float survival;
    private final float rangePower;
    public final boolean isRanged;
    private final EnemyMovementAi movementAi;

    public float health;
    public float movementSpeed;
    public float size;
    public ArrayList<EntityRunnable> abilities = new ArrayList<>();


    public EnemyGenerationBase(
            float placeDifficulty,
            EnemyArchetype archetype
    ) {
        this.placeDifficulty = placeDifficulty;
        this.archetype = archetype;

        this.speed = randomizeTraitValue(archetype.speed);
        this.survival = randomizeTraitValue(archetype.survival);
        this.rangePower = randomizeTraitValue(archetype.rangePower);
        this.isRanged = NumberUtils.randomChance(archetype.chanceToBeRanged);

        this.movementAi = pickAi();

        generateSurvival();
        generateSpeed();

        if (isRanged) {
            generateRangedAbilities();
        }
    }


    private void generateSurvival() {
        float totalHealthBudget = placeDifficulty + (survival * 2f);

        health = 1f;
        float survivalAbilityCost = 10f;
        size = 0.75f + (survival * 0.33f);

        if (archetype.ability == EnemyArchetypeAbility.SMALL) {
            size *= 0.75f;
        }

        if (archetype.ability == EnemyArchetypeAbility.INCREASED_SURVIVAL_ABILITY_BUDGET) {
            survivalAbilityCost -= 5f;
        }

        float healthPerPoint = 0.5f;

        if (archetype.ability == EnemyArchetypeAbility.MORE_HEALTH) {
            healthPerPoint = 1.5f;
            size *= 1.25f;
        }

        while (totalHealthBudget > 0) {

            if (totalHealthBudget > survivalAbilityCost && NumberUtils.randomChance(0.33f)) {
                // generate ability
                // TODO : this
                totalHealthBudget -= survivalAbilityCost;
            } else {
                health += healthPerPoint;
                totalHealthBudget -= 1f;
            }

        }

    }

    private void generateSpeed() {
        float totalSpeedBudget = speed * 10;
        movementSpeed = 0.25f;
        float movementAbilityCost = 10f;

        if (archetype.ability == EnemyArchetypeAbility.INCREASED_MOVEMENT_ABILITY_BUDGET) {
            movementAbilityCost -= 5f;
        }

        while (totalSpeedBudget > 0) {
            if (totalSpeedBudget > movementAbilityCost && NumberUtils.randomChance(0.33f)) {
                // generate ability
                // TODO : this
                totalSpeedBudget -= movementAbilityCost;
            } else {
                movementSpeed += 0.15f;
                totalSpeedBudget -= 1f;
            }
        }
    }


    private static ArrayList<Trait<EntityRunnable>> possibleAugments = new ArrayList<>();

    static {
        possibleAugments.add(new Trait<>(1f, 1f, (entity)->{
            entity.addNumericStat(FieldName.FireRate, -0.25f);
        }));
        possibleAugments.add(new Trait<>(0.15f, 4f, (entity)->{
            entity.addComponent(new ShotGunAugment(Quality.COMMON));
        }));
        possibleAugments.add(new Trait<>(0.15f, 4f, (entity)->{
            entity.addComponent(new MachineGunAugment(Quality.COMMON));
        }));
        possibleAugments.add(new Trait<>(0.15f, 2.5f, (entity)->{
            entity.addComponent(new TripleHank());
        }));
        possibleAugments.add(new Trait<>(0.15f, 2.5f, (entity)->{
            entity.addComponent(new BoomerangShotAugment());
        }));
        possibleAugments.add(new Trait<>(0.15f, 7.5f, (entity)->{
            entity.addComponent(new ShrapnelShotAugment(Quality.REFINED));
        }));
        possibleAugments.add(new Trait<>(0.15f, 7.7f, (entity)->{
            entity.addComponent(new MachineGunAugment(Quality.DIVINE));
        }));
        possibleAugments.add(new Trait<>(0.15f, 7.7f, (entity)->{
            entity.addComponent(new ShrapnelShotAugment(Quality.DIVINE));
        }));

    }

    private void generateRangedAbilities() {
        float totalRangedBudget = (rangePower * placeDifficulty * 0.25f);

        if (archetype.ability == EnemyArchetypeAbility.INCREASED_WEAPON_BUDGET) {
            totalRangedBudget += 10f;
        }


        TraitPicker<EntityRunnable> runnablePicker = new TraitPicker<>(possibleAugments, totalRangedBudget);
        System.out.println(totalRangedBudget);
        while (runnablePicker.hasBudget()) {
            this.abilities.add(runnablePicker.pickValue());
        }

    }


    private EnemyMovementAi pickAi() {
        if (archetype.ability == EnemyArchetypeAbility.STATIC) {
            return EnemyMovementAi.STATIC;
        }

        if (archetype.ability == EnemyArchetypeAbility.RETARDED) {
            return EnemyMovementAi.WANDER;
        }


        if (NumberUtils.randomChance(0.35f)) {
            return EnemyMovementAi.STEP;
        }
        return EnemyMovementAi.CHASE;
    }

    public EntityComponent getMovementAiInstance() {
        switch (movementAi) {
            case STEP:
                return new EnemyStepChaseMovement();
            case WANDER:
                return new EnemyAimlessWanderMovement();
            case STATIC:
                movementSpeed = 0f;
                return new EnemyChaseMovement();
            case CHASE:
            default:
                return new EnemyChaseMovement();
        }
    }

    private static float randomizeTraitValue(float value) {
        return NumberUtils.clampValue(value + NumberUtils.randomFloat(-0.10f, 0.10f), 0f, 1f);
    }

}
