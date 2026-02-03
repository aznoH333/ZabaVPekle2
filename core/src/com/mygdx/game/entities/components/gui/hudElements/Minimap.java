package com.mygdx.game.entities.components.gui.hudElements;

import com.mygdx.game.Managers;
import com.mygdx.game.drawing.DrawingCommand;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.playState.ZoneCoordinates;
import com.mygdx.game.playState.world.level.ZoneLevel;

import java.util.HashMap;

public class Minimap extends EntityComponent {
    
    private final static int CELLS_DRAWN = 4;
    private final static float TILES_OFFSET_Y = -2;
    
    @Override
    public void onDraw(Entity owner) {
        
        // draw cells
        HashMap<ZoneCoordinates, ZoneLevel> rooms = Managers.playStateManager.currentZone.rooms;
        
        for (int x = -CELLS_DRAWN; x < CELLS_DRAWN; x++) {
            for (int y = -CELLS_DRAWN; y < CELLS_DRAWN; y++) {
                String mapTileSprite = "";
                
                if (x == 0 && y == 0) {
                    mapTileSprite = "hud_map_tiles_0001";
                } else {
                    ZoneLevel level = rooms.get(new ZoneCoordinates(
                        Managers.playStateManager.playerZoneCoordinates.x + x,
                        Managers.playStateManager.playerZoneCoordinates.y + y
                    ));
                    
                    if (level == null) {
                        continue;
                    }
                    
                    mapTileSprite = level.type.minimapSprite;
                }
                
                
                Managers.drawingManager.drawSpriteStatic(
                    new DrawingCommand(
                        mapTileSprite,
                        owner.x + (x * 8),
                        owner.y + (y * 8) + TILES_OFFSET_Y
                    ),
                    DrawingLayer.GUI
                );
            }
        }
        
        
        
        
        // draw cover
        Managers.drawingManager.drawSpriteStatic(
            new DrawingCommand("hud_map_0001",
                owner.x,
                owner.y
            ),
            DrawingLayer.GUI
        );
    }
}
