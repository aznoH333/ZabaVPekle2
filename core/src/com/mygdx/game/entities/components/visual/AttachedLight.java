package com.mygdx.game.entities.components.visual;

import com.mygdx.game.Managers;
import com.mygdx.game.drawing.LightHandle;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;

public class AttachedLight extends EntityComponent {


    private LightHandle handle;
    public float radius;
    public float brightness;

    public AttachedLight(float radius, float brightness) {
        this.radius = radius;
        this.brightness = brightness;
    }

    @Override
    public void onUpdate(Entity owner) {
        handle.x = owner.x;
        handle.y = owner.y;
        handle.radius = radius;
        handle.brightness = brightness;
    }

    @Override
    public void onSudoku(Entity owner) {
        handle.destroy();
    }


    @Override
    public void onCleanUp(Entity owner) {
        handle.destroy();
    }

    @Override
    public void onPlacedInWorld(Entity owner) {
        this.handle = Managers.drawingManager.getNewLight(owner.x, owner.y, radius, brightness);
    }

    @Override
    public EntityComponent copy() {
        return new AttachedLight(handle.radius, brightness);
    }
}
