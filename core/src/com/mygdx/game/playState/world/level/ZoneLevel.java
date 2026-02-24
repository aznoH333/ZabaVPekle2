package com.mygdx.game.playState.world.level;

import com.mygdx.game.Managers;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.items.Quality;
import com.mygdx.game.facades.world.WorldInteractableFacade;
import com.mygdx.game.level.LevelExitDirection;
import com.mygdx.game.playState.MapCoordinates;
import com.mygdx.game.utils.Trait;
import com.mygdx.game.utils.TraitPicker;
import com.mygdx.game.utils.types.NumberUtils;

import java.util.ArrayList;

public class ZoneLevel {
    public final int roomSize;
    public final int enemySpawnSpeed;
    public final LevelTheme theme;
    public final LevelType type;
    public final MapCoordinates coordinates;
    public final ArrayList<Entity> roomContents = new ArrayList<>();

    public ZoneLevel(LevelType levelType, LevelTheme theme, ArrayList<Trait<Entity>> enemyRoster, MapCoordinates coordinates) {
        this.type = levelType;
        this.roomSize = type.roomSize;
        this.enemySpawnSpeed = levelType.spawnSpeed;
        this.theme = theme;
        this.coordinates = coordinates;

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
        
        if (type == LevelType.LOOT) {
            roomContents.add(
                WorldInteractableFacade.createNewAugmentBox(-16f, -16f, Quality.POOR)
            );
        }

        if (type == LevelType.ZONE_EXIT) {
            roomContents.add(
                WorldInteractableFacade.createLevelExit(-16f, -16f)
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
        
        for (Entity entity : Managers.entityManager.getAllEntities()) {
            if (entity.team.savedAsRoomContent) {
                roomContents.add(entity);
            }
        }
    }
}
