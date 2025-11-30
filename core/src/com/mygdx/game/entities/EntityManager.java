package com.mygdx.game.entities;

import com.mygdx.game.NumberUtils;

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
    private EntityManager() {

    }

    public void addEntity(Entity entity) {
        this.waitingRoom.add(entity);
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

        entities.removeIf((it)->!it.wantsToLive);

        entities.addAll(waitingRoom);
        waitingRoom.clear();
    }

    public Entity findClosestEntityWithComponent(float x, float y, String componentName) {
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

    public Entity findClosestEntityWithComponent(Entity caller, String componentName) {
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
}
