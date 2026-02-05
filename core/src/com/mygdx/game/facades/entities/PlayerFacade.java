package com.mygdx.game.facades.entities;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Managers;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.drawing.DrawingManager;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityTeam;
import com.mygdx.game.entities.components.behaviour.PlayerBehaviour;
import com.mygdx.game.entities.components.behaviour.gun.Gun;
import com.mygdx.game.entities.components.gui.hudElements.HealthBar;
import com.mygdx.game.entities.components.gui.hudElements.Minimap;
import com.mygdx.game.entities.components.visual.AnimatedLegsWithHat;
import com.mygdx.game.entities.components.visual.AttachedLight;
import com.mygdx.game.entities.components.visual.EyeCursor;
import com.mygdx.game.entities.components.visual.LegsWithHatType;
import com.mygdx.game.entities.fields.FieldName;

public class PlayerFacade {
    /** creates a new player blank entity*/
    public static Entity createNewPlayer(float x, float y) {
        return new Entity()
            .setTeam(EntityTeam.PLAYER)
            .addComponent(new PlayerBehaviour())
            .setDrawingLayer(DrawingLayer.PLAYER)
            .addComponent(new AnimatedLegsWithHat(LegsWithHatType.PLAYER, new Color(1f, 1f, 1f, 1f), new Color(1f, 0.8f, 0.8f, 1f), null))
            .addComponent(new Gun("guns_0001"))
            .addComponent(new EyeCursor(2f, 7f, 1.75f))
            
            
            .setNumericStat(FieldName.ProjectileSpeed, 0.75f)
            .setNumericStat(FieldName.FireRate, 25f)
            .setNumericStat(FieldName.ProjectileDamage, 2f)
            .setNumericStat(FieldName.Health, 6f)
            .setNumericStat(FieldName.MaxHealth, 6f)
            .setField(FieldName.ProjectileColor, new Color(0.33333f, 0.66666f, 1f, 1f))
            .setField(FieldName.ProjectileSprite, "bullets_0002")
            
            
            .addComponent(new AttachedLight(0.75f, 1.0f))
            // add hud
            .addChild(createHealthBar())
            .addChild(createMinimap())
            ;
    }
    
    private final static float HEALTH_BAR_OFFSET_Y = 26;
    public static Entity createHealthBar() {
        return new Entity()
            .setSprite("hud_health_0001")
            .setX(-DrawingManager.SCREEN_HEIGHT / 2)
            .setY(-DrawingManager.SCREEN_HEIGHT / 2 + HEALTH_BAR_OFFSET_Y)
            .setDrawingLayer(DrawingLayer.GUI)
            .addComponent(new HealthBar())
            .makeStatic();
    }
    
    private final static float MINIMAP_OFFSET_Y = 37;
    public static Entity createMinimap() {
        return new Entity()
            .setSprite("hud_map_0002")
            .setX(DrawingManager.SCREEN_HEIGHT / 2)
            .setY(-DrawingManager.SCREEN_HEIGHT / 2 + MINIMAP_OFFSET_Y)
            .setDrawingLayer(DrawingLayer.GUI)
            .makeStatic()
            .addComponent(new Minimap());
    }
}
