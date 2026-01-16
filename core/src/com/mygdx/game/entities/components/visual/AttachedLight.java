package com.mygdx.game.entities.components.visual;

import com.mygdx.game.Managers;
import com.mygdx.game.drawing.LightHandle;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;

public class AttachedLight extends EntityComponent {


    private final LightHandle handle;

    public AttachedLight(float intensity) {
        this.handle = Managers.drawingManager.getNewLight(0f, 0f, intensity);
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
    public void onFirstAttached(Entity owner) {
        handle.x = owner.x;
        handle.y = owner.y;
    }

    @Override
    public EntityComponent copy() {
        return new AttachedLight(handle.intensity);
    }
}
