package com.mygdx.game.entities.components.visual;

import com.mygdx.game.Managers;
import com.mygdx.game.entities.ComponentName;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.components.visual.particles.FadeParticle;

public class SpawnFadeTrail extends EntityComponent {
    
    private final int spawnInterval;
    private final int fadeImageLifetime;
    private int spawnTimer;
    
    public SpawnFadeTrail(int spawnInterval, int fadeImageLifetime) {
        this.spawnInterval = spawnInterval;
        this.fadeImageLifetime = fadeImageLifetime;
        this.spawnTimer = spawnInterval;
        super.name = ComponentName.FADE_TRAIL;
    }
    
    @Override
    public void onUpdate(Entity owner) {
        spawnTimer--;
        if (spawnTimer <= 0) {
            
            Managers.entityManager.addEntity(
                new Entity()
                    .setX(owner.x)
                    .setY(owner.y)
                    .setWidth(owner.width)
                    .setHeight(owner.height)
                    .setDrawingLayer(owner.drawingLayer)
                    .setSpriteRotation(owner.spriteRotation)
                    .setFlipX(owner.flipX)
                    .setFlipY(owner.flipY)
                    .setColor(owner.r, owner.g, owner.b, owner.a)
                    .setSprite(owner.sprite)
                    .setScaleX(owner.scaleX)
                    .setScaleY(owner.scaleY)

                    .addComponent(new FadeParticle(fadeImageLifetime, false, 1f))
            );
            
            
            spawnTimer = spawnInterval;
        }
    }
    
    
    @Override
    public EntityComponent copy() {
        return new SpawnFadeTrail(spawnInterval, fadeImageLifetime);
    }
}
