package com.mygdx.game.gameStates.implementations;

import com.mygdx.game.Managers;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.components.visual.AttachedLight;
import com.mygdx.game.facades.gui.GUIFacade;
import com.mygdx.game.facades.world.WorldFacade;
import com.mygdx.game.gameStates.GameState;
import com.mygdx.game.playState.ZoneCoordinates;
import com.mygdx.game.playState.world.WorldZone;

import java.util.HashMap;

public class WorldMap extends GameState {
    
    
    public WorldMap() {
        super("world map");
    }
    
    @Override
    public void initializeState() {
        
        
        HashMap<String, WorldZone> places = Managers.playStateManager.world.zones;
        
        for (WorldZone place : places.values()) {
            Managers.entityManager.addEntity(
                new Entity()
                    .setX(place.mapX)
                    .setY(place.mapY)
                    .setColor(place.mapColor.r, place.mapColor.g, place.mapColor.b, place.mapColor.a)
                    .setDrawingLayer(DrawingLayer.DOOR)
                    .addComponent(new AttachedLight(0.75f, 1f))
                    .setSprite("map_tiles_0001")
            );
            
            GUIFacade.createButton("Go to " + place.placeName, place.mapX, place.mapY, (e)->{
                WorldFacade.teleportPlayerToZone(place.placeName, new ZoneCoordinates(0, 0), 0f, 0f);
                Managers.gameStateManager.switchState("game");
            });
        }
    }
    
    @Override
    public void cleanUpState() {
    
    }
}
