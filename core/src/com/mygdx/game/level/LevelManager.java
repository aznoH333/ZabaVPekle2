package com.mygdx.game.level;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Managers;
import com.mygdx.game.drawing.DrawingCommand;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.components.control.Door;
import com.mygdx.game.facades.world.WorldFacade;
import com.mygdx.game.playState.WorldCoordinates;
import com.mygdx.game.utils.types.NumberUtils;
import com.mygdx.game.playState.world.level.ZoneLevel;

import java.util.HashMap;
import java.util.Map;


/**
 * Manages world drawing and the current room.
 * Not responsible for world progression or which rooms get loaded
 */
public class LevelManager {

    private static LevelManager instance = null;

    public static LevelManager getInstance() {
        if (instance == null) {
            instance = new LevelManager();
        }
        return instance;
    }

    private boolean doorsOpen = false;
    private ZoneLevel currentLevel = null;
    private HashMap<LevelExitDirection, WorldCoordinates> currentLevelExits = null;

    private LevelManager() {
    }


    public void draw() {
        for (int x = -currentLevel.getOuterRoomSize(); x < currentLevel.getOuterRoomSize(); x++) {
            for (int y = -currentLevel.getOuterRoomSize(); y < currentLevel.getOuterRoomSize(); y++) {
                LevelTileType tileType = getTileType(x, y);
                DrawingLayer layer = null;

                if (tileType.isSolid) {
                    layer = DrawingLayer.WALLS;
                } else {
                    layer = DrawingLayer.FLOOR;
                }

                Color tileColor = getColorForTile(tileType.color);

                Managers.drawingManager.drawSprite(
                    new DrawingCommand(tileType.textureName, x * 32f - 16f, y * 32f - 16f).setColor(tileColor),
                    layer);

                if (tileType.decorationTextureName != null) {
                    assert tileType.decorationColor != null;
                    Color decorationColor = getColorForTile(tileType.decorationColor);

                    Managers.drawingManager.drawSprite(
                        new DrawingCommand(tileType.decorationTextureName, x * 32f - 16f, y * 32f - 16f).setColor(decorationColor),
                        DrawingLayer.DOOR);
                }

            }
        }
    }


    public Color getColorForTile(LevelTileColorGroup tileColor) {
        switch (tileColor) {
            case BRICKS:
                return currentLevel.theme.brickColor;
            case WORLD_TOP:
                return currentLevel.theme.worldTopColor;
            case FLOOR:
                return currentLevel.theme.floorColor;
            default:
                return currentLevel.theme.doorColor;
        }
    }

    // TODO : this is dogshit slow code thats hard to maintain
    public LevelTileType getTileType(int x, int y) {
        int absX = Math.abs(x);
        int absY = Math.abs(y);

        int headerPos = currentLevel.getRoomSize() + 1;


        // doors
        for (LevelExitDirection direction: currentLevelExits.keySet()) {
            if (x == direction.x * currentLevel.getRoomSize() && y == direction.y * currentLevel.getRoomSize()) {
                if (doorsOpen) {
                    return direction.openDoorTile;
                }else {
                    return direction.closedDoorTile;
                }
            }
        }


        int innerWorldSize = currentLevel.getRoomSize();
        
        // bricks
        if (absX < innerWorldSize && y == innerWorldSize) {
            return LevelTileType.BRICK_WALL_TOP;
        }
        if (absX < innerWorldSize && y == -innerWorldSize) {
            return LevelTileType.BRICK_WALL_BOTTOM;
        }
        if (absY < innerWorldSize && x == innerWorldSize) {
            return LevelTileType.BRICK_WALL_RIGHT;
        }
        if (absY < innerWorldSize && x == -innerWorldSize) {
            return LevelTileType.BRICK_WALL_LEFT;
        }

        // brick corners
        if (x == -innerWorldSize && y == innerWorldSize) {
            return LevelTileType.BRICK_CORNER_LEFT_TOP;
        }
        if (x == -innerWorldSize && y == -innerWorldSize) {
            return LevelTileType.BRICK_CORNER_LEFT_BOTTOM;
        }
        if (x == innerWorldSize && y == innerWorldSize) {
            return LevelTileType.BRICK_CORNER_RIGHT_TOP;
        }
        if (x == innerWorldSize && y == -innerWorldSize) {
            return LevelTileType.BRICK_CORNER_RIGHT_BOTTOM;
        }

        // world top corners
        if (x == -headerPos && y == headerPos) {
            return LevelTileType.BRICK_HEADER_CORNER_LEFT_TOP;
        }
        if (x == -headerPos && y == -headerPos) {
            return LevelTileType.BRICK_HEADER_CORNER_RIGHT_BOTTOM;
        }
        if (x == headerPos && y == headerPos) {
            return LevelTileType.BRICK_HEADER_CORNER_RIGHT_TOP;
        }
        if (x == headerPos && y == -headerPos) {
            return LevelTileType.BRICK_HEADER_CORNER_LEFT_BOTTOM;
        }

        // world top
        if (absX < innerWorldSize + 1 && y == innerWorldSize + 1) {
            return LevelTileType.BRICK_HEADER_TOP;
        }
        if (absX < innerWorldSize + 1 && y == -innerWorldSize - 1) {
            return LevelTileType.BRICK_HEADER_BOTTOM;
        }
        if (absY < innerWorldSize + 1 && x == innerWorldSize + 1) {
            return LevelTileType.BRICK_HEADER_RIGHT;
        }
        if (absY < innerWorldSize + 1 && x == -innerWorldSize - 1) {
            return LevelTileType.BRICK_HEADER_LEFT;
        }


        // floor texture
        if (Math.abs(x) < innerWorldSize && Math.abs(y) < innerWorldSize) {
            return LevelTileType.FLOOR_TILE;
        }

        // colorless void
        return LevelTileType.VOID;

    }

    public void update() {

        if (Managers.playStateManager.playerReference == null) {
            
            return;
        }

    }

    private void spawnEnemy() {
        int x;
        int y;

        do {
            x = NumberUtils.randomInt(-currentLevel.getRoomSize(), currentLevel.getRoomSize()) * 32;
            y = NumberUtils.randomInt(-currentLevel.getRoomSize(), currentLevel.getRoomSize()) * 32;
        } while (!isSpaceEmpty(x, y, 32f, 32f) || NumberUtils.pythagoras(x, y, Managers.playStateManager.playerReference.x, Managers.playStateManager.playerReference.y) < 64f);

        /*
        Managers.entityManager.addEntity(
            new Entity()
                .setX(x)
                .setY(y)
                .setDrawingLayer(DrawingLayer.BLOOD)
                .setSprite("enemy_spawner_0001")
                .addComponent(new FadeParticle(120, true, 0.2f))
                .addComponent(
                    new Spawner(
                        progress.getReferenceEnemyToSpawn().copy())
                )
        ); */
    }

    public void killedEnemy() {
    
    }

    private void openDoors() {
        doorsOpen = true;
        
        for (Map.Entry<LevelExitDirection, WorldCoordinates> exit: currentLevelExits.entrySet()) {
            // spawn door object
            Managers.entityManager.addEntity(
                new Entity()
                    .setX((currentLevel.getRoomSize() - 0.45f) * 32f * exit.getKey().x - 16f)
                    .setY((currentLevel.getRoomSize() - 0.45f) * 32f * exit.getKey().y - 16f)
                    .addComponent(new Door(exit.getValue().zoneName, exit.getValue().zoneCoordinates, exit.getKey()))
            );
        }
        
    }

    public void saveCurrentRoomContents() {
        if (currentLevel == null) {
            return;
        }
        this.currentLevel.updateRoomContents();
    }

    public boolean isSpaceEmpty(float x, float y, float width, float height) {
        if (currentLevel == null) {
            return true;
        }
        
        // placehodler logic
        float widthValue = width / 2f;
        float heightValue = height / 2f;

        return x - widthValue > -currentLevel.getRoomSize() * 32f &&
            x + widthValue < (currentLevel.getRoomSize() - 1) * 32f &&
            y - heightValue > -currentLevel.getRoomSize() * 32f &&
            y + heightValue < (currentLevel.getRoomSize() - 1) * 32f;
    }
    
    public void loadLevel(ZoneLevel room) {
        this.currentLevel = room;
        this.currentLevelExits = WorldFacade.getLevelExits(currentLevel);
        
        for (Entity e : room.roomContents) {
            Managers.entityManager.addEntity(e);
        }
        
        openDoors();
    }

}
