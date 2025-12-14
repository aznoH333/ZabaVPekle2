package com.mygdx.game.world;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.NumberUtils;
import com.mygdx.game.drawing.DrawingCommand;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.drawing.DrawingManager;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityManager;
import com.mygdx.game.entities.EntityTeam;
import com.mygdx.game.entities.components.behaviour.DemonSoul;
import com.mygdx.game.entities.components.behaviour.Spawner;
import com.mygdx.game.entities.components.control.Door;
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

    private static final DrawingManager DRAWING_MANAGER = DrawingManager.getInstance();
    private static final EntityManager entityManager = EntityManager.getInstance();
    private int enemiesToSpawn = 10;
    private int enemiesToKill = enemiesToSpawn;
    private int enemySpawnCooldown = 10;
    private int nextEnemySpawnCooldown = 0;
    private boolean doorsOpen = false;


    private final int outerWorldSize = 25;
    private final int innerWorldSize = 10;
    private Entity player = null;


    private Color floorColor = new Color(0.2f, 0.2f, 0.2f, 1f);
    private Color brickColor = new Color(0.95f, 0.25f, 0.1f, 1f);
    private Color worldTopColor = new Color(0.95f, 0.25f, 0.1f, 1f);
    private Color doorColor = new Color(0.8f, 0.8f, 0.8f, 1f);


    public void draw() {
        for (int x = -outerWorldSize; x < outerWorldSize; x++) {
            for (int y = -outerWorldSize; y < outerWorldSize; y++) {
                WorldTileType tileType = getTileType(x, y);
                DrawingLayer layer = null;

                if (tileType.isSolid) {
                    layer = DrawingLayer.WALLS;
                }else {
                    layer = DrawingLayer.FLOOR;
                }

                Color tileColor = getColorForTile(tileType.color);

                DRAWING_MANAGER.drawSprite(
                        new DrawingCommand(tileType.textureName, x * 32f - 16f, y * 32f - 16f).setColor(tileColor),
                        layer);

                if (tileType.decorationTextureName != null) {
                    assert tileType.decorationColor != null;
                    Color decorationColor = getColorForTile(tileType.decorationColor);

                    DRAWING_MANAGER.drawSprite(
                            new DrawingCommand(tileType.decorationTextureName, x * 32f - 16f, y * 32f - 16f).setColor(decorationColor),
                            DrawingLayer.DOOR);
                }

            }
        }
    }


    public Color getColorForTile(WorldTileColor tileColor) {
        switch (tileColor) {
            case BRICKS:
                return brickColor;
            case WORLD_TOP:
                return worldTopColor;
            case FLOOR:
                return floorColor;
            default:
                return doorColor;
        }
    }


    public WorldTileType getTileType(int x, int y) {
        int absX = Math.abs(x);
        int absY = Math.abs(y);

        int headerPos = innerWorldSize + 1;


        // doors
        if (x == 0 && y == innerWorldSize) {
            if (doorsOpen) {
                return WorldTileType.DOOR_TOP_OPEN;
            } else {
                return WorldTileType.DOOR_TOP_CLOSED;
            }
        }
        if (x == 0 && y == -innerWorldSize) {
            return WorldTileType.DOOR_BOTTOM_CLOSED;
        }


        // bricks
        if (absX < innerWorldSize && y == innerWorldSize) {
            return WorldTileType.BRICK_WALL_TOP;
        }
        if (absX < innerWorldSize && y == -innerWorldSize) {
            return WorldTileType.BRICK_WALL_BOTTOM;
        }
        if (absY < innerWorldSize && x == innerWorldSize) {
            return WorldTileType.BRICK_WALL_RIGHT;
        }
        if (absY < innerWorldSize && x == -innerWorldSize) {
            return WorldTileType.BRICK_WALL_LEFT;
        }

        // brick corners
        if (x == -innerWorldSize && y == innerWorldSize) {
            return WorldTileType.BRICK_CORNER_LEFT_TOP;
        }
        if (x == -innerWorldSize && y == -innerWorldSize) {
            return WorldTileType.BRICK_CORNER_LEFT_BOTTOM;
        }
        if (x == innerWorldSize && y == innerWorldSize) {
            return WorldTileType.BRICK_CORNER_RIGHT_TOP;
        }
        if (x == innerWorldSize && y == -innerWorldSize) {
            return WorldTileType.BRICK_CORNER_RIGHT_BOTTOM;
        }

        // world top corners
        if (x == -headerPos && y == headerPos) {
            return WorldTileType.BRICK_HEADER_CORNER_LEFT_TOP;
        }
        if (x == -headerPos && y == -headerPos) {
            return WorldTileType.BRICK_HEADER_CORNER_RIGHT_BOTTOM;
        }
        if (x == headerPos && y == headerPos) {
            return WorldTileType.BRICK_HEADER_CORNER_RIGHT_TOP;
        }
        if (x == headerPos && y == -headerPos) {
            return WorldTileType.BRICK_HEADER_CORNER_LEFT_BOTTOM;
        }

        // world top
        if (absX < innerWorldSize + 1 && y == innerWorldSize + 1) {
            return WorldTileType.BRICK_HEADER_TOP;
        }
        if (absX < innerWorldSize + 1 && y == -innerWorldSize - 1) {
            return WorldTileType.BRICK_HEADER_BOTTOM;
        }
        if (absY < innerWorldSize + 1 && x == innerWorldSize + 1) {
            return WorldTileType.BRICK_HEADER_RIGHT;
        }
        if (absY < innerWorldSize + 1 && x == -innerWorldSize - 1) {
            return WorldTileType.BRICK_HEADER_LEFT;
        }




        // floor texture
        if (Math.abs(x) < innerWorldSize && Math.abs(y) < innerWorldSize) {
            return WorldTileType.FLOOR_TILE;
        }

        // colorless void
        return WorldTileType.VOID;

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
                                .overrideDefault(Stat.Health, 6f, 1f)
                                .setX(x)
                                .setY(y)
                                .addComponent(new DemonSoul())
                                .addComponent(new GameEntityAnimator("enemy", 1, 2, 8, 9, 3))
                                .addComponent(new GameEntityBleed())
                                // .addComponent(new Shooter("hand_0001")) TODO : lol, lmfao xd

                                .setDrawingLayer(DrawingLayer.ENEMIES)))
        );
    }

    public void killedEnemy() {
        this.enemiesToKill--;

        if (enemiesToKill == 0) {
            doorsOpen = true;

            // spawn door object
            entityManager.addEntity(
                    new Entity()
                            .setX(-16f)
                            .setY((innerWorldSize -1) * 32f)
                            .addComponent(new Door())
            );
        }
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

    public void moveToNewLevel(Entity playerRef) {
        entityManager.clearAllEntities();
        entityManager.addEntity(playerRef.setX(-16f).setY((-innerWorldSize +1) * 32f + 16f));


        this.enemiesToSpawn = 10;
        this.doorsOpen = false;
        this.enemiesToKill = enemiesToSpawn;
    }
}
