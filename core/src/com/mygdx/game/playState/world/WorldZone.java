package com.mygdx.game.playState.world;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.facades.enemyGeneration.EnemyGeneratorFacade;
import com.mygdx.game.utils.Trait;
import com.mygdx.game.playState.world.level.ZoneLevel;
import com.mygdx.game.playState.world.level.LevelType;

import java.util.ArrayList;

public class WorldZone {

    public final WorldZoneDefinition type;
    public final String placeName;
    
    
    public final Color mapColor;

    public final ArrayList<ZoneLevel> rooms = new ArrayList<>();

    private int currentProgress = 0;

    private ArrayList<Trait<Entity>> enemyRoster;

    public float mapX;
    public float mapY;

    public WorldZone(WorldZoneDefinition type) {
        this.type = type;

        this.placeName = type.zoneName;
        this.mapColor = type.worldMapColor;
        
        this.mapX = type.worldMapX;
        this.mapY = type.worldMapY;

        this.enemyRoster = EnemyGeneratorFacade.generateEnemyRoster(2, type.placeDifficulty);

        rooms.add(
            new ZoneLevel(LevelType.SPAWN, type.theme, enemyRoster)
        );

        for (int i = 0; i < 1; i++) {
            this.rooms.add(new ZoneLevel(LevelType.FILLER, type.theme, enemyRoster));
            this.rooms.add(new ZoneLevel(LevelType.FILLER, type.theme, enemyRoster));
            this.rooms.add(new ZoneLevel(LevelType.MAJOR_COMBAT, type.theme, enemyRoster));
            this.rooms.add(new ZoneLevel(LevelType.FILLER, type.theme, enemyRoster));
            this.rooms.add(new ZoneLevel(LevelType.LOOT, type.theme, enemyRoster));
        }
        this.rooms.add(new ZoneLevel(LevelType.BOSS, type.theme, enemyRoster));

    }

    public void completedRoom() {
        currentProgress++;
    }

    public ZoneLevel getCurrentRoom() {
        return rooms.get(currentProgress);
    }

    public boolean isComplete() {
        return currentProgress >= rooms.size();
    }
}
