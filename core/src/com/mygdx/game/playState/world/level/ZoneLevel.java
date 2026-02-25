package com.mygdx.game.playState.world.level;

import com.mygdx.game.Managers;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.items.Quality;
import com.mygdx.game.facades.entities.WorldInteractableFacade;
import com.mygdx.game.level.LevelExitDirection;
import com.mygdx.game.playState.MapCoordinates;
import com.mygdx.game.playState.world.WorldZone;
import com.mygdx.game.utils.Trait;
import com.mygdx.game.utils.TraitPicker;
import com.mygdx.game.utils.types.NumberUtils;

import java.util.ArrayList;

public class ZoneLevel {
    public final int roomSize;
    public final LevelTheme theme;
    public final LevelType type;
    public final MapCoordinates coordinates;
    public final ArrayList<Entity> roomContents = new ArrayList<>();
    private boolean visited;

    public ZoneLevel(LevelType levelType, LevelTheme theme, ArrayList<Trait<Entity>> enemyRoster, MapCoordinates coordinates, WorldZone parentZone) {
        this.type = levelType;
        this.roomSize = type.roomSize;
        this.theme = theme;
        this.coordinates = coordinates;
        this.visited = false;

        fillRoomContents(levelType, enemyRoster, parentZone);

    }

    private void fillRoomContents(LevelType levelType, ArrayList<Trait<Entity>> enemyRoster, WorldZone parentZone) {
        spawnEnemies(levelType, enemyRoster);

        spawnWorldEntities(parentZone);
    }

    private void spawnWorldEntities(WorldZone parentZone) {
        if (type == LevelType.LOOT) {
            roomContents.add(
                WorldInteractableFacade.createNewAugmentBox(-16f, -16f, parentZone.type.lootRoomBoxQuality)
            );
        }

        if (type == LevelType.ZONE_EXIT) {
            roomContents.add(
                WorldInteractableFacade.createLevelExit(-16f, -16f)
            );
        }

        if (type == LevelType.MACHINE_ROOM) {
            roomContents.add(WorldInteractableFacade.createCraftingStation(-16f, -16f));
        }

        if (type == LevelType.SCRAP_ROOM) {
            roomContents.add(WorldInteractableFacade.createItemBox(-16f, -16f));
        }
    }

    private void spawnEnemies(LevelType levelType, ArrayList<Trait<Entity>> enemyRoster) {
        ArrayList<Integer> indexesToExclude = new ArrayList<>();
        ArrayList<Trait<Entity>> roomEnemies = new ArrayList<>();
        for (int i = Math.min(enemyRoster.size(), NumberUtils.randomInt(1, 3)); i > 0; i--) {
            int pickedIndex;
            do {
                pickedIndex = NumberUtils.randomInt(0, enemyRoster.size() - 1);
            } while (indexesToExclude.contains(pickedIndex));

            indexesToExclude.add(pickedIndex);
            roomEnemies.add(enemyRoster.get(pickedIndex));
        }

        // build queue
        TraitPicker<Entity> enemyPicker = new TraitPicker<>(roomEnemies, NumberUtils.randomFloat(levelType.minEnemies, levelType.maxEnemies));
        while (enemyPicker.hasBudget()) {
            int enemyX;
            int enemyY;
            boolean isSpawnValid;

            do {
                enemyX = NumberUtils.randomInt(-roomSize + 1, roomSize - 1) * 32;
                enemyY = NumberUtils.randomInt(-roomSize + 1, roomSize - 1) * 32;

                isSpawnValid = true;

                /* TODO: make this reflect the actual level exits */
                for (LevelExitDirection direction : LevelExitDirection.values()) {
                    if (NumberUtils.distance(
                        direction.x * roomSize * 32f,
                        direction.y * roomSize * 32f,
                        enemyX,
                        enemyY
                    ) < 64f) {
                        isSpawnValid = false;
                    }
                }


            }while (!isSpawnValid);

            roomContents.add(
                enemyPicker
                    .pickValue()
                    .copy()
                    .setX(enemyX - 16f)
                    .setY(enemyY - 16f)
            );
        }
    }

    /** returns the size of the play area (inner world size)*/
    public int getRoomSize() {
        return roomSize;
    }
    
    /** returns the total size of the world area (includes unplayable borders)*/
    public int getOuterRoomSize() {
        return roomSize + 5;
    }
    
    public ArrayList<Entity> getRoomContents() {
        return roomContents;
    }
    
    /** Saves all entities in a room. */
    public void updateRoomContents() {
        roomContents.clear();
        this.visited = true;
        
        for (Entity entity : Managers.entityManager.getAllEntities()) {
            if (entity.team.savedAsRoomContent) {
                roomContents.add(entity);
            }
        }
    }


    public String getMapSprite() {
        if (visited) {
            if (!roomContents.isEmpty()) {
                return "hud_map_tiles_0003";
            }

            return "hud_map_tiles_0002";
        }


        if (type == LevelType.LOOT || type == LevelType.ZONE_EXIT) {
            return "hud_map_tiles_0005";
        }

        if (type == LevelType.BOSS ||
                type == LevelType.MINI_BOSS_ROOM ||
                type == LevelType.SCRAP_ROOM ||
                type == LevelType.MACHINE_ROOM) {
            return "hud_map_tiles_0006";
        }

        return "hud_map_tiles_0004";

    }
}
