package com.mygdx.game.entities.components.gui.hudElements;

import com.mygdx.game.Managers;
import com.mygdx.game.drawing.DrawingCommand;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.fields.FieldName;

public class BossHealthBar extends EntityComponent {


    private final Entity boss;
    private final float maxHealth;

    private static final int TOTAL_SEGMENT_COUNT = 16;
    private static final int SEGMENT_WIDTH = 12;
    private static final int X_OFFSET = (-TOTAL_SEGMENT_COUNT * SEGMENT_WIDTH / 2);
    private static final float SKULL_OFFSET_X = 21f / 2f + (SEGMENT_WIDTH / 2f);
    private static final float SKULL_OFFSET_Y = 2f;

    public BossHealthBar(Entity boss) {
        this.boss = boss;
        maxHealth = boss.getNumericStat(FieldName.MaxHealth);
    }

    @Override
    public void onDraw(Entity owner) {
        float healthPercentage = boss.getNumericStat(FieldName.Health) / maxHealth;

        Managers.drawingManager.drawSpriteStatic(
                new DrawingCommand("boss_healthbar_header",
                        owner.x + X_OFFSET - SKULL_OFFSET_X,
                        owner.y + SKULL_OFFSET_Y),
                DrawingLayer.GUI
        );

        for (int i = 0; i < TOTAL_SEGMENT_COUNT; i++) {
            float segmentPercentage = ((float) i / TOTAL_SEGMENT_COUNT);

            String sprite;

            if (segmentPercentage <= healthPercentage) {
                sprite = "boss_healthbar_segment_0001";
            }else {
                sprite = "boss_healthbar_segment_0002";
            }

            float currentCellOffset = X_OFFSET + (i * SEGMENT_WIDTH);

            Managers.drawingManager.drawSpriteStatic(
                    new DrawingCommand(sprite, owner.x + currentCellOffset, owner.y),
                    DrawingLayer.GUI
            );
        }

    }
}
