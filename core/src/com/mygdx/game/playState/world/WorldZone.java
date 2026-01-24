package com.mygdx.game.playState.world;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.facades.enemyGeneration.EnemyGeneratorFacade;
import com.mygdx.game.playState.ZoneCoordinates;
import com.mygdx.game.playState.world.level.LevelTheme;
import com.mygdx.game.utils.Trait;
import com.mygdx.game.playState.world.level.ZoneLevel;
import com.mygdx.game.playState.world.level.LevelType;
import com.mygdx.game.utils.types.NumberUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class WorldZone {

    public final WorldZoneDefinition type;
    public final String placeName;
    
    
    public final Color mapColor;

    public final HashMap<ZoneCoordinates, ZoneLevel> rooms = new HashMap<>();

    private final ArrayList<Trait<Entity>> enemyRoster;

    public float mapX;
    public float mapY;

    public WorldZone(WorldZoneDefinition type) {
        this.type = type;

        this.placeName = type.zoneName;
        this.mapColor = type.worldMapColor;
        
        this.mapX = type.worldMapX;
        this.mapY = type.worldMapY;

        this.enemyRoster = EnemyGeneratorFacade.generateEnemyRoster(2, type.placeDifficulty);
        
        
        // generate rooms
        HashSet<ZoneCoordinates> mapCoordinates = new HashSet<>();
        /** important coordinates are candidates for special rooms (zone exits/shops/ect) */
        HashSet<ZoneCoordinates> importantCoordinates = new HashSet<>();
        
        
        mapCoordinates.add(new ZoneCoordinates(0, 0)); // 0,0 is always filled
        
        for (int i = 0; i < 5; i++) {
            int currentX = 0;
            int currentY = 0;
            
            for (int lengthIterator = 0; lengthIterator < 5; lengthIterator++) {
                
                int attemptX = currentX;
                int attemptY = currentY;
                int attemptCount = 3;
                
                do {
                    if (NumberUtils.randomChance(0.5f)) {
                        attemptX = (int) (currentX + NumberUtils.boolToSign(NumberUtils.randomChance(0.5f)));
                    } else {
                        attemptY = (int) (currentY + NumberUtils.boolToSign(NumberUtils.randomChance(0.5f)));
                    }
                    
                    attemptCount--;
                } while (mapCoordinates.contains(new ZoneCoordinates(attemptX, attemptY)) && attemptCount > 0);
                
                currentX = attemptX;
                currentY = attemptY;
                
                mapCoordinates.add(new ZoneCoordinates(currentX, currentY));
                
                
            }
            
            importantCoordinates.add(new ZoneCoordinates(currentX, currentY));
        }
        
        
        
        // generate rooms with types
        rooms.put(new ZoneCoordinates(0, 0), new ZoneLevel(LevelType.SPAWN, LevelTheme.SPECIAL_PLACEHOLDER, enemyRoster, new ZoneCoordinates(0, 0), type.zoneName));
        
        for (ZoneCoordinates importantCoordinate: importantCoordinates) {
            rooms.put(importantCoordinate, new ZoneLevel(LevelType.LOOT, LevelTheme.SPECIAL_PLACEHOLDER,  enemyRoster, importantCoordinate, type.zoneName));
        }
        
        for (ZoneCoordinates roomCoordinate : mapCoordinates) {
            if (!rooms.containsKey(roomCoordinate)) {
                LevelType levelType = LevelType.FILLER;
                if (NumberUtils.randomChance(0.3f)) {
                    levelType = LevelType.MAJOR_COMBAT;
                }
                rooms.put(roomCoordinate, new ZoneLevel(levelType, type.theme, enemyRoster, roomCoordinate, type.zoneName));
            }
        }
        
        
        // temp print
        System.out.println("Generated world zone " + type.zoneName);
        for (int x = -5; x < 5; x++) {
            for (int y = -5; y < 5; y++) {
                if (rooms.get(new ZoneCoordinates(x, y)) != null) {
                    System.out.print("[ ]");
                }else {
                    System.out.print("  ");
                }
            }
            System.out.print("\n");
        }
    }
    

}
