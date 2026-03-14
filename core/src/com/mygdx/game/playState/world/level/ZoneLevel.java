package com.mygdx.game.playState.world.level;

import com.mygdx.game.Managers;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.facades.enemyGeneration.EnemyGeneratorFacade;
import com.mygdx.game.facades.enemyGeneration.EnemyRoster;
import com.mygdx.game.facades.entities.WorldInteractableFacade;
import com.mygdx.game.level.LevelExitDirection;
import com.mygdx.game.playState.MapCoordinates;
import com.mygdx.game.playState.world.WorldZone;
import com.mygdx.game.utils.TraitPicker;
import com.mygdx.game.utils.types.NumberUtils;

import java.util.ArrayList;

public class ZoneLevel {
    public final int roomSize;
    public final LevelTheme theme;
    public final LevelType type;
    public final MapCoordinates coordinates;
    public final ArrayList<Entity> roomContents = new ArrayList<>();
    public boolean visited;
    private final WorldZone parentZone;

    public ZoneLevel(LevelType levelType, LevelTheme theme, EnemyRoster enemyRoster, MapCoordinates coordinates, WorldZone parentZone) {
        this.type = levelType;
        this.roomSize = type.roomSize;
        this.theme = theme;
        this.coordinates = coordinates;
        this.visited = false;
        this.parentZone = parentZone;

        fillRoomContents(enemyRoster, parentZone);

    }

    private void fillRoomContents( EnemyRoster enemyRoster, WorldZone parentZone) {
        spawnEnemies(enemyRoster);

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
            float rng = NumberUtils.randomFloat(0f, 1f);

            if (rng <= 0.33f) {
                roomContents.add(WorldInteractableFacade.createRepairStation(-16f, -16f));
            }else if (rng <= 0.66f) {
                roomContents.add(WorldInteractableFacade.createProcessorAssembler(-16f, -16f));
            }else {
                roomContents.add(WorldInteractableFacade.createForgingStation(-16f, -16f));
            }

        }

        if (type == LevelType.SCRAP_ROOM) {
            roomContents.add(WorldInteractableFacade.createItemBox(-16f, -16f));
        }

        if (type == LevelType.MINI_BOSS_ROOM) {
            roomContents.add(EnemyGeneratorFacade.generateBossEnemy(parentZone.type.placeDifficulty + 10f, 3));

        }

        if (type == LevelType.BOSS) {
            roomContents.add(EnemyGeneratorFacade.generateBossEnemy(parentZone.type.placeDifficulty + 30f, 10));
        }
    }

    private void spawnEnemies(EnemyRoster enemyRoster) {
        TraitPicker<Entity> enemyPicker = enemyRoster.createBudgetedPickerForRoom(NumberUtils.randomFloat(type.minEnemies, type.maxEnemies));
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
