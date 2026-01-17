package com.mygdx.game.entities.components.visual;

import com.mygdx.game.Managers;
import com.mygdx.game.drawing.LightHandle;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;

public class AttachedLight extends EntityComponent {


    private LightHandle handle;
    public float intensity;

    public AttachedLight(float intensity) {
        this.intensity = intensity;
    }

    @Override
    public void onUpdate(Entity owner) {
        handle.x = owner.x;
        handle.y = owner.y;
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
        this.handle = Managers.drawingManager.getNewLight(owner.x, owner.y, intensity);
    }

    @Override
    public EntityComponent copy() {
        return new AttachedLight(handle.intensity);
    }
}
