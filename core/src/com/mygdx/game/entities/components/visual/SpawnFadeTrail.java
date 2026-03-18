package com.mygdx.game.entities.components.visual;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Managers;
import com.mygdx.game.drawing.DrawingCommand;
import com.mygdx.game.entities.ComponentName;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;

import java.util.ArrayList;


public class SpawnFadeTrail extends EntityComponent {
    
    private final int afterImageCount;
    private final int imageDistance;
    private final int maxRecordCount;
    private ArrayList<Vector2> recordedPositions = new ArrayList<>();

    public SpawnFadeTrail(int afterImageCount, int imageDistance) {
        this.afterImageCount = afterImageCount;
        this.imageDistance = imageDistance;
        super.name = ComponentName.FADE_TRAIL;
        this.maxRecordCount = (afterImageCount + 1) * imageDistance ;
    }

    @Override
    public void onDraw(Entity owner) {
        for (int i = 0; i <= afterImageCount; i++) {
            if (recordedPositions.size() < ((i + 1) * imageDistance) + 1) {
                continue;
            }
            Vector2 imagePosition = recordedPositions.get((i + 1) * imageDistance);
            Managers.drawingManager.drawSprite(
                    new DrawingCommand(
                            owner.sprite,
                            imagePosition.x,
                            imagePosition.y
                    ).setR(owner.r).setG(owner.g).setB(owner.b).setA(
                            (float) i / afterImageCount * owner.a
                    ).setFlipHorizontally(owner.flipX)
                            .setFlipVertically(owner.flipY)
                            .setWidth(owner.scaleX)
                            .setHeight(owner.scaleY)
                            .setRotationRad(owner.spriteRotation)
                    ,
                    owner.drawingLayer
            );
        }

    }



    @Override
    public void onUpdate(Entity owner) {
        if (recordedPositions.size() < maxRecordCount) {
            recordedPositions.add(new Vector2(owner.x, owner.y));
        }else {
            for (int i = 0; i < maxRecordCount - 1; i++) {
                recordedPositions.set(i, recordedPositions.get(i+1));
            }
            recordedPositions.set(maxRecordCount-1, new Vector2(owner.x, owner.y));
        }
    }

    
    
    @Override
    public EntityComponent copy() {
        return new SpawnFadeTrail(afterImageCount, imageDistance);
    }
}
