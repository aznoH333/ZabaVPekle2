package com.mygdx.game.facades.enemyGeneration;

public enum EnemyArchetype {
    TINY_GUY(0.3f, 0.3f, 0.0f, 0.0f, 0.333f, EnemyArchetypeAbility.SMALL,0f, 6f, 1f),
    CHASER(0.6f, 0.5f, 0.0f, 0.0f, 0.75f, 0f,  1f),
    TURRET(0f, 0.5f, 1f, 0.6f, 1f, EnemyArchetypeAbility.STATIC, 0f, 0.666f),
    WANDERER(0.4f, 0f, 0f, 0f, 1f, EnemyArchetypeAbility.RETARDED, 0f, 1.1f, 1f),
    RANGER(0.333f, 0.2f, 1f, 0.333f, 1f, 5f, 0.1f),
    ANNOYING_SHITHEAD(0.5f, 1f, 0.4f, 0.2f, 1f, EnemyArchetypeAbility.INCREASED_SURVIVAL_ABILITY_BUDGET, 6f, 0.5f),
    SNIPER(0.2f, 0f, 1f, 1f, 1.2f, EnemyArchetypeAbility.INCREASED_WEAPON_BUDGET, 10f, 0.5f),
    TANK(0.2f, 1f, 0.1f, 0.1f, 1.2f, EnemyArchetypeAbility.MORE_HEALTH, 10f, 0.5f),
    BERSERKER(1f, 0.5f, 0f, 0f, 1f, EnemyArchetypeAbility.INCREASED_MOVEMENT_ABILITY_BUDGET, 10f, 0.1f),
    SHIT_FUCK(0.3f, 1f, 1f, 0.5f, 1.2f, EnemyArchetypeAbility.MORE_HEALTH, 30f, 0.1f);



    public final float speed;
    public final float survival;
    public final float chanceToBeRanged;
    public final float rangePower;
    public final float spawnWeight;
    public final EnemyArchetypeAbility ability;
    public final float minGenerationDifficulty;
    public final float maxGenerationDifficulty;
    public final float generationChance;

    EnemyArchetype(
            float speed,
            float survival,
            float chanceToBeRanged,
            float rangePower,
            float spawnWeight,
            EnemyArchetypeAbility ability,
            float minGenerationDifficulty,
            float maxGenerationDifficulty,
            float generationChance
    ) {
        this.speed = speed;
        this.survival = survival;
        this.chanceToBeRanged = chanceToBeRanged;
        this.rangePower = rangePower;
        this.ability = ability;
        this.spawnWeight = spawnWeight;
        this.minGenerationDifficulty = minGenerationDifficulty;
        this.maxGenerationDifficulty = maxGenerationDifficulty;
        this.generationChance = generationChance;
    }

    EnemyArchetype(
            float speed,
            float survival,
            float chanceToBeRanged,
            float rangePower,
            float spawnWeight,
            EnemyArchetypeAbility ability,
            float minGenerationDifficulty,
            float generationChance
    ) {
        this.speed = speed;
        this.survival = survival;
        this.chanceToBeRanged = chanceToBeRanged;
        this.rangePower = rangePower;
        this.ability = ability;
        this.spawnWeight = spawnWeight;
        this.minGenerationDifficulty = minGenerationDifficulty;
        this.generationChance = generationChance;
        this.maxGenerationDifficulty = -1f;

    }

    EnemyArchetype(
            float speed,
            float survival,
            float chanceToBeRanged,
            float rangePower,
            float spawnWeight,
            float minGenerationDifficulty,
            float maxGenerationDifficulty,
            float generationChance
    ) {
        this.speed = speed;
        this.survival = survival;
        this.chanceToBeRanged = chanceToBeRanged;
        this.rangePower = rangePower;
        this.ability = null;
        this.spawnWeight = spawnWeight;
        this.minGenerationDifficulty = minGenerationDifficulty;
        this.maxGenerationDifficulty = maxGenerationDifficulty;
        this.generationChance = generationChance;

    }

    EnemyArchetype(
            float speed,
            float survival,
            float chanceToBeRanged,
            float rangePower,
            float spawnWeight,
            float minGenerationDifficulty,
            float generationChance
    ) {
        this.speed = speed;
        this.survival = survival;
        this.chanceToBeRanged = chanceToBeRanged;
        this.rangePower = rangePower;
        this.ability = null;
        this.spawnWeight = spawnWeight;
        this.minGenerationDifficulty = minGenerationDifficulty;
        this.generationChance = generationChance;
        this.maxGenerationDifficulty = -1f;
    }


}
