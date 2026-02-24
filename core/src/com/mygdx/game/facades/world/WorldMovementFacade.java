package com.mygdx.game.facades.world;

import com.mygdx.game.Managers;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.components.control.DelayedEvent;
import com.mygdx.game.facades.sceen.VisualEffectsFacade;
import com.mygdx.game.level.LevelExitDirection;
import com.mygdx.game.playState.MapCoordinates;
import com.mygdx.game.playState.world.WorldZone;
import com.mygdx.game.playState.world.level.ZoneLevel;

/**
 * Concerned with moving the player around on the world map.
 */
public class WorldMovementFacade {

    /**
     * Teleports the player to a specific room in a specific zone with specific coordinates
     * @param coordinates - coordinates of the room to teleport to
     * @param x - in room x
     * @param y - in room y
     */
    public static void teleportPlayerToZone(MapCoordinates coordinates, float x, float y) {
        Managers.levelManager.saveCurrentRoomContents();
        Managers.entityManager.clearAllEntities();
        VisualEffectsFacade.clearAllLights();
        Managers.playStateManager.setPlayerZoneCoordinates(coordinates.x, coordinates.y);
        Managers.playStateManager.playerReference.setX(x).setY(y);
        Managers.entityManager.addEntity(Managers.playStateManager.playerReference);
        Managers.levelManager.loadLevel(getLevelByZoneCoordinates(coordinates));
    }

    public static void goToNextZone() {

        fadeTransition(()->{
            Managers.playStateManager.goToNextZone();

            teleportPlayerToZone(new MapCoordinates(0,0), 0f, 0f);
        });

    }

    private static void fadeTransition(Runnable transitionFunction) {
        Managers.drawingManager.screenEffectShaderHandler.dimScreen(50);
        Managers.entityManager.freezeEntities(60);

        Managers.entityManager.addEntity(
                new Entity()
                        .addComponent(new DelayedEvent(
                                transitionFunction,
                                25
                        ))
        );
    }


    public static void enterARoomThroughADoor(MapCoordinates coordinates, LevelExitDirection direction) {
        fadeTransition(
                ()-> {
                    ZoneLevel targetLevel = getLevelByZoneCoordinates(coordinates);

                    // calculate entry point location
                    float entryX = (((targetLevel.getRoomSize() - 1.2f) * 32f) * -direction.x) - 16f;
                    float entryY = (((targetLevel.getRoomSize() - 1.2f) * 32f) * -direction.y) - 16f;

                    teleportPlayerToZone(coordinates, entryX, entryY);
                }
        );
    }


    private static ZoneLevel getLevelByZoneCoordinates(MapCoordinates coordinates) {
        WorldZone zone = Managers.playStateManager.currentZone;
        return zone.rooms.get(coordinates);
    }
}
