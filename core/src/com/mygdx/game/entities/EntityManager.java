package com.mygdx.game.entities;

import com.sun.jmx.remote.internal.ArrayQueue;

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
        for (Entity e : entities) {
            e.update();
        }

        entities.addAll(waitingRoom);
        waitingRoom.clear();
    }
}
