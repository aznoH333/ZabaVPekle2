package com.mygdx.game.entities.facades.EnemyGeneration;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityTeam;
import com.mygdx.game.entities.components.behaviour.EnemyBehaviour;
import com.mygdx.game.entities.components.visual.AnimatedLegsWithHat;
import com.mygdx.game.entities.components.visual.GameEntityBleed;
import com.mygdx.game.entities.fields.FieldName;
import com.mygdx.game.utils.NumberUtils;

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
 *
 * Specified difficulty scales linearly - (diff jump from 3 to 4 is the same as from 2 to 3)
 * The system is intended to have the first place always generate with diff 1
 */
public class EnemyGeneratorFacade {
    /**
     * Generates a new enemy roster (enemies to spawn in rooms).
     * Each roster is intended to last for 1 place.
     * Always generates a small fodder enemy
     * And a low threat generalist enemy
     * @param specializedEnemyCount
     * @param placeDifficulty
     * @return
     */
    public static ArrayList<Entity> generateEnemyRoster(int specializedEnemyCount, float placeDifficulty) {
        ArrayList<Entity> enemyRoster = new ArrayList<>();


        // small fodder
        enemyRoster.add(
                generateEnemyType(
                        NumberUtils.randomFloat(0f, 0.2f),
                        NumberUtils.randomFloat(0.4f, 0.9f),
                        0.2f,
                        placeDifficulty
                )
        );


        enemyRoster.add(
                generateEnemyType(
                        NumberUtils.randomFloat(0.2f, 0.4f),
                        NumberUtils.randomFloat(0.4f, 0.6f),
                        0.3f,
                        placeDifficulty
                )
        );

        for (int i = 0; i < specializedEnemyCount; i++) {
            enemyRoster.add(
                    generateEnemyType(
                            NumberUtils.randomFloat(0.2f, 0.4f),
                            NumberUtils.randomFloat(0.4f, 0.6f),
                            0.3f,
                            placeDifficulty
                    )
            );
        }


        return enemyRoster;
    }





    private static Entity generateEnemyType(float toughness, float mobility, float threat, float targetDifficulty) {

        boolean isTurret = false;
        boolean hasRangedAttack = false;

        float speed;
        float health;


        // decide if ranged
        if (threat > 0.35f && NumberUtils.randomChance(0.6f)) {
            hasRangedAttack = true;
        }

        // return new Entity();

        // generate speed
        if (mobility < 0.2f && NumberUtils.randomChance(0.75f)) {
            // immobile enemy (turret)
            speed = 0f;
            isTurret = true;
            hasRangedAttack = true;
        }else {
            speed = 0.5f + (mobility * 2.2f);

            if (!hasRangedAttack && threat > 0.5f) {
                speed += threat * 1.2f;
            }

            if (toughness > 0.8f && mobility > 0.9f) {
                speed *= 0.85f; // nerf enemies that would be too tanky and mobile
            }
        }


        // generate health
        health = targetDifficulty * (3f + (toughness * 2f));

        // TODO ranged attacks

        return new Entity()
                .setSprite("enemy_1")
                .setTeam(EntityTeam.DEMON)
                .setNumericStat(FieldName.Health, health)
                .setNumericStat(FieldName.Speed, speed)
                .addComponent(new EnemyBehaviour())
                .addComponent(new AnimatedLegsWithHat(new Color(0.75f, 0.25f, 1f, 1f), new Color(1f, 0.5f, 0.5f, 1f), "hats_" + NumberUtils.randomInt(2, 11)))
                .addComponent(new GameEntityBleed())
                .setDrawingLayer(DrawingLayer.ENEMIES);
    }


}
