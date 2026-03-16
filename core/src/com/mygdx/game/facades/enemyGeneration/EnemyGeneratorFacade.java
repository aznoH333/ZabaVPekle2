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
import com.mygdx.game.facades.entities.GameEntityCreatorFacade;
import com.mygdx.game.utils.Trait;
import com.mygdx.game.utils.TraitPicker;
import com.mygdx.game.utils.types.NumberUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class EnemyGeneratorFacade {


    public static EnemyRoster generateEnemyRoster(int enemyCount, float placeDifficulty) {
        ArrayList<Trait<Entity>> generatedEnemies = new ArrayList<>();


        ArrayList<EnemyArchetype> archetypes = pickArchetypes(placeDifficulty, enemyCount);


        for (EnemyArchetype archetype : archetypes) {
            generatedEnemies.add(new Trait<>(1f, archetype.spawnWeight, generateEnemyFromBase(new EnemyGenerationBase(placeDifficulty, archetype))));
        }

        return new EnemyRoster(generatedEnemies);
    }


    public static Entity generateBossEnemy(float bossDifficulty, float healthMultiplier) {

        List<EnemyArchetype> bossArchetypes = Arrays
                .stream(EnemyArchetype.values())
                .filter((it)->it.ability == EnemyArchetypeAbility.BOSS).collect(Collectors.toList());


        EnemyArchetype pickedArchetype = bossArchetypes.get(NumberUtils.randomInt(0, bossArchetypes.size() - 1));

        EnemyGenerationBase base = new EnemyGenerationBase(
                bossDifficulty,
                pickedArchetype
            );

        Entity enemy = generateEnemyFromBase(base);


        float health = enemy.getNumericStat(FieldName.Health) * healthMultiplier;

        enemy.setNumericStat(FieldName.Health, health);
        enemy.setNumericStat(FieldName.MaxHealth, health);


        float size = NumberUtils.randomFloat(1.5f, 3f);
        enemy.setScaleX(size);
        enemy.setScaleY(size);

        // make slower
        float speed = enemy.getNumericStat(FieldName.Speed) * 0.85f;
        enemy.setNumericStat(FieldName.Speed, speed);

        // shoot faster
        enemy.addNumericStat(FieldName.FireRateMultiplier, -1f);


        enemy.knockBackMultiplier = 0.25f;

        // add healthbar
        enemy.addChild(
                GameEntityCreatorFacade.createEnemyHealthBarHudElement(enemy)
        );

        return enemy;
    }

    private static ArrayList<EnemyArchetype> pickArchetypes(float placeDifficulty, int enemyCount) {
        ArrayList<EnemyArchetype> output = new ArrayList<>();


        // build pickable archetypes
        ArrayList<Trait<EnemyArchetype>> pickableArchetypes = new ArrayList<>();

        for (EnemyArchetype type : EnemyArchetype.values()) {
            if (type.minGenerationDifficulty <= placeDifficulty && (type.maxGenerationDifficulty >= placeDifficulty || type.maxGenerationDifficulty == -1) && type.ability != EnemyArchetypeAbility.BOSS) {
                pickableArchetypes.add(new Trait<EnemyArchetype>(type.generationChance, 0f, type));
            }
        }

        TraitPicker<EnemyArchetype> archetypePicker = new TraitPicker<>(pickableArchetypes, 0f);

        while (output.size() < enemyCount) {
            EnemyArchetype pickedArchetype = archetypePicker.pickValue();


            if (output.contains(pickedArchetype) && NumberUtils.randomChance(0.75f)) {
                continue;
            }

            output.add(pickedArchetype);
        }

        return output;
    }




    private static Entity generateEnemyFromBase(EnemyGenerationBase base) {
        Entity entity = new Entity()
                .setTeam(EntityTeam.ENEMY)
                .setNumericStat(FieldName.Health, base.health)
                .setNumericStat(FieldName.Speed, base.movementSpeed)
                .addComponent(new EnemyBaseBehaviour(base.behaviours, base.pickBehaviourRandomly))
                .addComponent(new AnimatedLegsWithHat(LegsWithHatType.ENEMY_MEDIUM, new Color(1f, 1f, 1f, 1f), new Color(1f, 0.5f, 0.5f, 1f), "small_enemy_heads_" + NumberUtils.randomInt(1, 9)))
                .addComponent(new GameEntityBleed())
                .addComponent(base.getMovementAiInstance())
                .setScaleX(base.size)
                .setScaleY(base.size)
                .setSprite("player_1")
                .setNumericStat(FieldName.Damage, 1f)
                .setDrawingLayer(DrawingLayer.ENEMIES);




        if (base.isRanged) {


            entity.setNumericStat(FieldName.ProjectileLifeTime, 120f);
            entity.setNumericStat(FieldName.ProjectileSpread, 0f);
            entity.setNumericStat(FieldName.ProjectileSpeed, 0.45f);
            entity.setNumericStat(FieldName.FireRate, 120f);
            entity.setNumericStat(FieldName.FireRateMultiplier, 1.5f);

            entity.addComponent(
                    new Gun("guns_0006")
            );

        }

        // apply abilities
        for (EntityRunnable runnable : base.abilities) {
            runnable.run(entity);
        }

        return entity;
    }

}
