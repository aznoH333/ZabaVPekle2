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

    public final HashMap<ZoneCoordinates, ZoneLevel> rooms = new HashMap<>();

    private final ArrayList<Trait<Entity>> enemyRoster;

    public float mapX;
    public float mapY;
    
    public Color ambientLight;

    public WorldZone(WorldZoneDefinition type) {
        this.type = type;

        this.ambientLight = type.ambientLight;

        this.enemyRoster = EnemyGeneratorFacade.generateEnemyRoster(2, type.placeDifficulty);

        //generateWorldOld(type);
        generateWorldNew();

        printGeneratedLevel(type);

    }

    private class PathGenerator{
        int yOffset;
        int tilesSinceLastChange = 0;
        int distanceTraveled = 0;
        int endsIn;
        boolean isMain;
        int timeSinceLoot = 0;
        int timeSinceMajorCombat = 0;

        public PathGenerator(int yOffset, int endsIn, boolean isMain) {
            this.yOffset = yOffset;
            this.endsIn = endsIn;
            this.isMain = isMain;
        }

        public boolean canChange() {
            return tilesSinceLastChange >= 2 && endsIn > 1;
        }

        public void updatePosition() {
            this.tilesSinceLastChange++;
            this.distanceTraveled++;
            this.timeSinceLoot++;
            this.timeSinceMajorCombat++;
        }

        public void movedHorizontally() {
            this.endsIn--;
        }

        public boolean canSplit() {
            return this.canChange() && this.endsIn > 3;
        }

        public PathGenerator spawnChild() {
            this.tilesSinceLastChange = 0;

            return new PathGenerator(
                    this.yOffset,
                    NumberUtils.randomInt(2, this.endsIn - 1),
                    false
            );
        }

        public LevelType getRoomType() {
            if (isMain && distanceTraveled == 0) {
                return LevelType.SPAWN;
            }

            if (isMain && endsIn == 2 && NumberUtils.randomChance(0.65f)) {
                return LevelType.BOSS;
            }

            if (isMain && endsIn == 0) {
                return LevelType.SPAWN;
            }

            // loot
            if (timeSinceLoot > 3 && NumberUtils.randomChance(0.65f)) {
                this.timeSinceLoot = 0;
                return LevelType.LOOT;
            }

            // combat
            if (timeSinceMajorCombat > 2 && NumberUtils.randomChance(0.35f)) {
                this.timeSinceMajorCombat = 0;
                return LevelType.MAJOR_COMBAT;
            }

            return LevelType.FILLER;
        }

    }

    private void generateWorldNew() {
        int currentX = 0;
        int totalLength = NumberUtils.randomInt(10, 15);



        PathGenerator mainPath = new PathGenerator(0, totalLength, true);
        ArrayList<PathGenerator> activePaths = new ArrayList<>();
        ArrayList<PathGenerator> waitingRoom = new ArrayList<>();
        activePaths.add(mainPath);


        while (!activePaths.isEmpty()) {
            for (PathGenerator path : activePaths) {
                // placeholder room generation function
                generateRoom(currentX, path.yOffset, path.getRoomType());

                path.updatePosition();
                path.movedHorizontally();


                // path ending
                if (!path.isMain && path.endsIn == 0) {
                    shiftPath(path, mainPath.yOffset, currentX);
                }



                if (!path.canChange()) {
                    continue;
                }


                // path splitting
                if (path.canSplit() && NumberUtils.randomChance(0.5f) && activePaths.size() + waitingRoom.size() < 5) {
                    PathGenerator child = path.spawnChild();

                    int childOffset = (int) (NumberUtils.randomInt(2, 3) * NumberUtils.boolToSign(NumberUtils.randomChance(0.5f)));

                    shiftPath(child, child.yOffset + childOffset, currentX);

                    waitingRoom.add(child);
                }
                // shift path
                if (NumberUtils.randomChance(0.25f)) {
                    int tilesShifted = (int) (NumberUtils.randomInt(1, 2) * NumberUtils.boolToSign(NumberUtils.randomChance(0.5f)));

                    shiftPath(path, path.yOffset + tilesShifted, currentX);
                }



            }

            activePaths.addAll(waitingRoom);
            currentX++;

            activePaths.removeIf((it)->it.endsIn <= 0);

        }
    }

    private void shiftPath(PathGenerator path, int targetY, int currentX) {
        for (int i = 0; i <= Math.abs(path.yOffset - targetY); i++) {
            generateRoom(currentX,  path.yOffset + (int)(i * Math.signum(targetY - path.yOffset)), LevelType.SPAWN);
            path.updatePosition();
        }

        path.yOffset = targetY;
        path.tilesSinceLastChange = 0;
    }

    private void generateRoom(int x, int y, LevelType levelType) {

        if (this.rooms.containsKey(new ZoneCoordinates(x, y))) {
            return;
        }

        this.rooms.put(new ZoneCoordinates(x, y),  new ZoneLevel(levelType, LevelTheme.SPECIAL_PLACEHOLDER, enemyRoster, new ZoneCoordinates(x, y)));

    }

    private void generateWorldOld(WorldZoneDefinition type) {
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
        rooms.put(new ZoneCoordinates(0, 0), new ZoneLevel(LevelType.SPAWN, LevelTheme.SPECIAL_PLACEHOLDER, enemyRoster, new ZoneCoordinates(0, 0)));

        for (ZoneCoordinates importantCoordinate: importantCoordinates) {
            rooms.put(importantCoordinate, new ZoneLevel(LevelType.LOOT, LevelTheme.RED_PLACEHOLDER,  enemyRoster, importantCoordinate));
        }

        for (ZoneCoordinates roomCoordinate : mapCoordinates) {
            if (!rooms.containsKey(roomCoordinate)) {
                LevelType levelType = LevelType.FILLER;
                if (NumberUtils.randomChance(0.3f)) {
                    levelType = LevelType.MAJOR_COMBAT;
                }
                rooms.put(roomCoordinate, new ZoneLevel(levelType, type.theme, enemyRoster, roomCoordinate));
            }
        }


    }

    private void printGeneratedLevel(WorldZoneDefinition type) {
        // temp print
        for (int x = -5; x < 5; x++) {
            for (int y = -5; y < 5; y++) {
                if (rooms.get(new ZoneCoordinates(x, y)) != null) {
                    System.out.print("[ ]");
                }else {
                    System.out.print("   ");
                }
            }
            System.out.print("\n");
        }
    }


}
