package com.mygdx.game.entities.facades;

import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.EntityTeam;
import com.mygdx.game.entities.components.behaviour.Bullet;
import com.mygdx.game.entities.stats.Stat;

import java.util.ArrayList;

public class ProjectileFactory {

    // TODO : this this should be rewritten to be a facade instead


    public static Entity buildBullet(
            float x,
            float y,
            String sprite,
            float damage,
            float speed,
            EntityTeam team,
            float direction,
            int lifeTime,
            ArrayList<EntityComponent> components
    ) {
        Entity bullet = new Entity()
                .setSprite(sprite)
                .setX(x)
                .setY(y)
                .overrideDefault(Stat.Damage, damage, 2f)
                .overrideDefault(Stat.Speed, speed, 2f)
                .setTriggerInvincibility(false)
                .setTeam(team)
                .setDrawingLayer(DrawingLayer.PROJECTILES)
                .addComponent(new Bullet(direction, lifeTime));

        if (components != null) {
            for (EntityComponent c : components) {
                bullet.addComponent(c.copy());
            }
        }

        return bullet;
    }
}
