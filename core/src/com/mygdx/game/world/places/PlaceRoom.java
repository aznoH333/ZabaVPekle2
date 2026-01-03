package com.mygdx.game.world.places;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.utils.Trait;
import com.mygdx.game.utils.TraitPicker;
import com.mygdx.game.utils.types.NumberUtils;

import java.util.ArrayList;

public class PlaceRoom {
    public final int roomSize;
    public final int enemiesToSpawn;
    public final int enemySpawnSpeed;
    public final RoomType type;
    private final ArrayList<Entity> enemyQueue = new ArrayList<>();

    public PlaceRoom(RoomType roomType, ArrayList<Trait<Entity>> enemyRoster) {
        this.type = roomType;
        this.roomSize = type.roomSize;
        this.enemySpawnSpeed = roomType.spawnSpeed;


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
        TraitPicker<Entity> enemyPicker = new TraitPicker<>(roomEnemies, NumberUtils.randomFloat(roomType.minEnemies, roomType.maxEnemies));
        while (enemyPicker.hasBudget()) {
            enemyQueue.add(enemyPicker.pickValue());
        }
        enemiesToSpawn = enemyQueue.size();

    }

    public Entity getReferenceEnemyFromRoster() {
        return enemyQueue.removeFirst();
    }

}
