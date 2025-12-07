package com.mygdx.game;

import com.mygdx.game.drawing.DrawingCommand;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.drawing.SpriteManager;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityManager;
import com.mygdx.game.entities.EntityTeam;
import com.mygdx.game.entities.components.behaviour.DemonSoul;
import com.mygdx.game.entities.components.behaviour.Spawner;
import com.mygdx.game.entities.components.visual.GameEntityAnimator;
import com.mygdx.game.entities.components.visual.GameEntityBleed;
import com.mygdx.game.entities.components.visual.particles.FadeParticle;
import com.mygdx.game.entities.stats.Stat;

public class WorldManager {

    private static WorldManager instance = null;



    public static WorldManager getInstance() {
        if (instance == null) {
            instance = new WorldManager();
        }
        return instance;
    }

    private static final SpriteManager spriteManager = SpriteManager.getInstance();
    private static final EntityManager entityManager = EntityManager.getInstance();
    private int enemiesToSpawn = 0;
    private int enemySpawnCooldown = 30;
    private int nextEnemySpawnCooldown = 0;


    private final int outerWorldSize = 25;
    private final int innerWorldSize = 15;
    private Entity player = null;


    public void draw() {
        for (int x = -outerWorldSize; x < outerWorldSize; x++) {
            for (int y = -outerWorldSize; y < outerWorldSize; y++) {
                if (Math.abs(x) < innerWorldSize && Math.abs(y) < innerWorldSize) {
                    spriteManager.drawSprite(
                            new DrawingCommand("floor_tile", x * 32f - 16f, y * 32f - 16f)
                                    .setR(0.6f).setG(0.4f).setB(0.0f),
                            DrawingLayer.WORLD);
                }else {
                    spriteManager.drawSprite(
                            new DrawingCommand("brick_wall", x * 32f - 16f, y * 32f - 16f)
                                    .setR(0.6f).setG(0.4f).setB(0.2f),
                            DrawingLayer.WORLD);
                }
            }
        }
    }

    public void update() {

        if (player == null) {
            player = entityManager.findClosestEntityWithComponent(0f, 0f, "soul");
            return;
        }

        // spawn enemies
        if (enemiesToSpawn > 0) {

            if (nextEnemySpawnCooldown == 0) {
                nextEnemySpawnCooldown = enemySpawnCooldown;
                spawnEnemy();
                enemiesToSpawn--;
            }else {
                nextEnemySpawnCooldown--;
            }
        }
    }

    private void spawnEnemy() {


        int x;
        int y;

        do {
            x = NumberUtils.randomInt(-innerWorldSize, innerWorldSize) * 32;
            y = NumberUtils.randomInt(-innerWorldSize, innerWorldSize) * 32;
        }while (!isSpaceEmpty(x, y, 32f, 32f) || NumberUtils.pythagoras(x, y, player.x, player.y) < 128f);

        System.out.println("spawning enemy at " + x + ", " + y);

        entityManager.addEntity(
                new Entity()
                        .setX(x)
                        .setY(y)
                        .setDrawingLayer(DrawingLayer.BLOOD)
                        .setSprite("enemy_spawner_0001")
                        .addComponent(new FadeParticle(120, true, 0.2f))
                        .addComponent(new Spawner(new Entity()
                                .setSprite("enemy_1")
                                .setTeam(EntityTeam.DEMON)
                                .overrideDefault(Stat.Health, 20f, 1f)
                                .setX(x)
                                .setY(y)
                                .addComponent(new DemonSoul())
                                .addComponent(new GameEntityAnimator("enemy", 1, 2, 8, 9, 3))
                                .addComponent(new GameEntityBleed())
                                .setDrawingLayer(DrawingLayer.ENEMIES)))
        );


    }

    public boolean isSpaceEmpty(float x, float y, float width, float height) {
        // placehodler logic
        float widthValue = width / 2f;
        float heightValue = height / 2f;

        return x - widthValue > -innerWorldSize * 32f &&
               x + widthValue < (innerWorldSize - 1) * 32f &&
               y - heightValue > -innerWorldSize * 32f &&
               y + heightValue < (innerWorldSize - 1) * 32f;
    }
}
