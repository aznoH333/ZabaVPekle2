package com.mygdx.game.playState.world.level;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.playState.ZoneCoordinates;
import com.mygdx.game.utils.Trait;
import com.mygdx.game.utils.TraitPicker;
import com.mygdx.game.utils.types.NumberUtils;

import java.util.ArrayList;

public class ZoneLevel {
    public final int roomSize;
    public final int enemiesToSpawn;
    public final int enemySpawnSpeed;
    public final LevelTheme theme;
    public final LevelType type;
    private final ArrayList<Entity> enemyQueue = new ArrayList<>();
    public final ZoneCoordinates coordinates;
    public final String zoneName;

    public ZoneLevel(LevelType levelType, LevelTheme theme, ArrayList<Trait<Entity>> enemyRoster, ZoneCoordinates coordinates, String zoneName) {
        this.type = levelType;
        this.roomSize = type.roomSize;
        this.enemySpawnSpeed = levelType.spawnSpeed;
        this.theme = theme;
        this.coordinates = coordinates;
        this.zoneName = zoneName;

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
            enemyQueue.add(enemyPicker.pickValue());
        }
        enemiesToSpawn = enemyQueue.size();

    }

    public Entity getReferenceEnemyFromRoster() {
        return enemyQueue.removeFirst();
    }

    /** returns the size of the play area (inner world size)*/
    public int getRoomSize() {
        return roomSize;
    }
    
    /** returns the total size of the world area (includes unplayable borders)*/
    public int getOuterRoomSize() {
        return roomSize + 5;
    }
}
