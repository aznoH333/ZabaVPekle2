package com.mygdx.game.entities.components.behaviour;

import com.mygdx.game.entities.ComponentName;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.components.visual.SpawnFadeTrail;
import com.mygdx.game.entities.fields.FieldName;

public class Dash extends EntityComponent {


    private final float maxSpeed;
    private final int maxCooldown;
    private int cooldown = 0;
    private float direction = 0f;
    private final int maxDashDuration;
    private int duration = 0;

    public Dash(float speed, int duration, int cooldown) {
        this.maxSpeed = speed;
        this.maxCooldown = cooldown;
        this.maxDashDuration = duration;
        super.name = ComponentName.DASH;
    }


    public void dashInDirection(float direction) {
        if (cooldown != 0) {
            return;
        }
        addFadeTrail();
        cooldown = maxCooldown;
        this.direction = direction;
        duration = maxDashDuration;
    }

    @Override
    public void onUpdate(Entity owner) {
        if (duration > 0) {
            float dashPercentage = (float) duration / maxDashDuration;
            owner.goInDirection(direction, dashPercentage * maxSpeed);
            duration--;

            if (duration == 1) {
                removeFadeTrail();
            }
        }

        owner.setField(FieldName.SuspendMovement, duration > 0);

        if (cooldown > 0) {
            cooldown--;
        }
    }

    @Override
    public void onFirstAttached(Entity owner) {
        owner.initializeField(FieldName.SuspendMovement, false);
    }

    private void addFadeTrail() {
        SpawnFadeTrail trail = getTrail();

        if (trail != null) {
            return;
        }

        owner.addComponent(new SpawnFadeTrail(3, 10));
    }

    private void removeFadeTrail() {
        SpawnFadeTrail trail = getTrail();

        if (trail == null) {
            return;
        }

        owner.removeComponentByName(ComponentName.FADE_TRAIL);
    }
    private SpawnFadeTrail getTrail() {
        return (SpawnFadeTrail) owner.getComponentByName(ComponentName.FADE_TRAIL);
    }

}
