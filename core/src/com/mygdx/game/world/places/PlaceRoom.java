package com.mygdx.game.world.places;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.utils.NumberUtils;

import java.util.ArrayList;

public class PlaceRoom {
    public final int roomSize;
    public final int enemiesToSpawn;
    public final int enemySpawnSpeed;
    public final RoomType type;
    private ArrayList<Entity> roomEnemies = new ArrayList<>();

    public PlaceRoom(RoomType roomType, ArrayList<Entity> enemyRoster) {
        this.type = roomType;
        this.roomSize = type.roomSize;
        this.enemiesToSpawn = NumberUtils.randomInt(roomType.minEnemies, roomType.maxEnemies);
        this.enemySpawnSpeed = roomType.spawnSpeed;


        ArrayList<Integer> indexesToExclude = new ArrayList<>();
        for (int i = Math.min( enemyRoster.size(), NumberUtils.randomInt(1, 3)); i > 0; i--) {
            int pickedIndex;
            do {
                pickedIndex = NumberUtils.randomInt(0, enemyRoster.size() - 1);
            }while (indexesToExclude.contains(pickedIndex));

            indexesToExclude.add(pickedIndex);
            roomEnemies.add(enemyRoster.get(pickedIndex));
        }

    }

    public Entity getReferenceEnemyFromRoster() {
        return roomEnemies.get(NumberUtils.randomInt(0, roomEnemies.size() - 1));
    }

}
