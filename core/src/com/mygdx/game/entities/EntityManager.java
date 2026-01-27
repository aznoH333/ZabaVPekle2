package com.mygdx.game.entities;

import com.mygdx.game.utils.types.NumberUtils;

import java.util.ArrayList;

public class EntityManager {

    private static EntityManager instance;

    public static EntityManager getInstance() {
        if (instance == null) {
            instance = new EntityManager();
        }

        return instance;
    }


    private final ArrayList<Entity> entities = new ArrayList<>();
    private final ArrayList<Entity> waitingRoom = new ArrayList<>();
    private boolean clearAllEntitiesOnCycleEnd = false;

    private EntityManager() {

    }

    public Entity addEntity(Entity entity) {
        this.waitingRoom.add(entity);
        return entity;
    }

    public void update() {

        // update loop
        for (Entity e : entities) {
            e.update();
        }
        // collide loop
        for (Entity e : entities) {
            for (Entity other : entities) {
                if (other != e && e.collidesWithEntity(other)) {
                    e.onCollide(other);
                }
            }
        }

        entities.removeIf((it) -> {
            if (!it.wantsToLive) {
                it.invokeSudoku();
            }

            boolean returnValue = !it.wantsToLive || clearAllEntitiesOnCycleEnd;

            if (returnValue) {
                it.removedFromWorld();
            }

            return returnValue;
        });


        clearAllEntitiesOnCycleEnd = false;


        waitingRoom.forEach(Entity::placedInWorld);
        entities.addAll(waitingRoom);
        waitingRoom.clear();
    }

    public Entity findClosestEntityWithComponent(float x, float y, ComponentName componentName) {
        Entity closestEntity = null;
        float closestDistance = 0;

        for (Entity e : entities) {
            if (e.hasComponent(componentName)) {
                float distance = NumberUtils.pythagoras(x, y, e.x, e.y);

                if (closestEntity == null || distance < closestDistance) {
                    closestEntity = e;
                    closestDistance = distance;
                }
            }
        }

        return closestEntity;
    }

    public Entity findClosestEntityWithComponent(Entity caller, ComponentName componentName) {
        Entity closestEntity = null;
        float closestDistance = 0;

        for (Entity e : entities) {
            if (e != caller && e.hasComponent(componentName)) {
                float distance = NumberUtils.pythagoras(caller.x, caller.y, e.x, e.y);

                if (closestEntity == null || distance < closestDistance) {
                    closestEntity = e;
                    closestDistance = distance;
                }
            }
        }

        return closestEntity;
    }

    public void clearAllEntities() {
        clearAllEntitiesOnCycleEnd = true;
    }
    
    public ArrayList<Entity> getAllEntities() {
        ArrayList<Entity> entitiesCombined = new ArrayList<>();
        entitiesCombined.addAll(entities);
        entitiesCombined.addAll(waitingRoom);
        
        return entitiesCombined;
    }
}
