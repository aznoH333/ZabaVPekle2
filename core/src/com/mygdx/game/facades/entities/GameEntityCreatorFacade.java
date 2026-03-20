package com.mygdx.game.facades.entities;


import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.drawing.DrawingManager;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.EntityIdentifier;
import com.mygdx.game.entities.EntityTeam;
import com.mygdx.game.entities.components.behaviour.Bullet;
import com.mygdx.game.entities.components.behaviour.Dash;
import com.mygdx.game.entities.components.behaviour.PlayerBehaviour;
import com.mygdx.game.entities.components.behaviour.gun.Gun;
import com.mygdx.game.entities.components.gui.hudElements.BossHealthBar;
import com.mygdx.game.entities.components.gui.hudElements.HealthBar;
import com.mygdx.game.entities.components.gui.hudElements.Minimap;
import com.mygdx.game.entities.components.visual.AnimatedLegsWithHat;
import com.mygdx.game.entities.components.visual.AttachedLight;
import com.mygdx.game.entities.components.visual.EyeCursor;
import com.mygdx.game.entities.components.visual.LegsWithHatType;
import com.mygdx.game.entities.fields.FieldName;

import java.util.ArrayList;

/**
 * Concerned with creating instances of entities
 */
public class GameEntityCreatorFacade {

    /** creates a new player blank entity*/
    public static Entity createNewPlayer(float x, float y) {
        return new Entity()
                .setTeam(EntityTeam.PLAYER)

                .addComponent(new PlayerBehaviour())
                .setDrawingLayer(DrawingLayer.PLAYER)
                .addComponent(new AnimatedLegsWithHat(LegsWithHatType.PLAYER, new Color(1f, 1f, 1f, 1f), new Color(1f, 0.8f, 0.8f, 1f), null))
                .addComponent(new Gun("guns_0001"))
                .addComponent(new EyeCursor(2f, 7f, 1.75f))
                .addComponent(new Dash(4f, 20, 65))

                .setX(x)
                .setY(y)
                .setIdentifier(EntityIdentifier.PLAYER)


                .setNumericStat(FieldName.Speed, 2.5f)
                .setNumericStat(FieldName.ProjectileSpeed, 0.75f)
                .setNumericStat(FieldName.FireRate, 25f)
                .setNumericStat(FieldName.ProjectileDamage, 2f)
                .setNumericStat(FieldName.Health, 10f)
                .setNumericStat(FieldName.MaxHealth, 10f)
                .setField(FieldName.ProjectileColor, new Color(0.33333f, 0.66666f, 1f, 1f))
                .setField(FieldName.ProjectileSprite, "bullets_0006")


                .addComponent(new AttachedLight(0.75f, 1.0f))
                // add hud
                .addChild(createHealthBarHudElement())
                .addChild(createMinimapHudElement())
                ;
    }

    private final static float HEALTH_BAR_OFFSET_Y = 26;
    private static Entity createHealthBarHudElement() {
        return new Entity()
                .setSprite("hud_health_0001")
                .setX(-DrawingManager.SCREEN_HEIGHT / 2)
                .setY(-DrawingManager.SCREEN_HEIGHT / 2 + HEALTH_BAR_OFFSET_Y)
                .setDrawingLayer(DrawingLayer.GUI)
                .addComponent(new HealthBar())
                .makeStatic();
    }

    private final static float BOSS_HEALTH_BAR_OFFSET_Y = 64;
    public static Entity createEnemyHealthBarHudElement(Entity enemy) {
        return new Entity()
                .setX(0)
                .setY(-DrawingManager.SCREEN_HEIGHT / 2 + BOSS_HEALTH_BAR_OFFSET_Y)
                .addComponent(new BossHealthBar(enemy))
                .makeStatic();
    }

    private final static float MINIMAP_OFFSET_Y = 37;
    private static Entity createMinimapHudElement() {
        return new Entity()
                .setSprite("hud_map_0002")
                .setX(DrawingManager.SCREEN_HEIGHT / 2)
                .setY(-DrawingManager.SCREEN_HEIGHT / 2 + MINIMAP_OFFSET_Y)
                .setDrawingLayer(DrawingLayer.GUI)
                .makeStatic()
                .addComponent(new Minimap());
    }

    public static Entity buildBullet(
            float x,
            float y,
            String sprite,
            float damage,
            float speed,
            EntityTeam team,
            float direction,
            int lifeTime,
            ArrayList<EntityComponent> components,
            float bounceCount,
            Color color
    ) {
        Entity bullet = new Entity()
                .setSprite(sprite)
                .setX(x)
                .setY(y)
                .setNumericStat(FieldName.Damage, damage)
                .setNumericStat(FieldName.Speed, speed)
                .setNumericStat(FieldName.RemainingProjectileLifeTime, lifeTime)
                .setNumericStat(FieldName.BounceCount, bounceCount)
                .setTriggerInvincibility(false)
                .setTeam(team)
                .setDrawingLayer(DrawingLayer.PROJECTILES)
                .setCanBeDamaged(false)
                .addComponent(new Bullet(direction, lifeTime))
                .setColor(color.r, color.g, color.b, color.a)
                .addComponent(new AttachedLight(0.25f, 0.5f));


        if (components != null) {
            for (EntityComponent c : components) {
                bullet.addComponent(c.copy());
            }
        }

        return bullet;
    }
}
