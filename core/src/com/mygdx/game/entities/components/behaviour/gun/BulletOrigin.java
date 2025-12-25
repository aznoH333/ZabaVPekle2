package com.mygdx.game.entities.components.behaviour.gun;


/**
 * A data holding class indicating bullet origins.
 * Has no functionality of its own. Should be used by Gun instead.
 * @see Gun
 */
public class BulletOrigin {
    /** Number in radians indicating this bullet origins offset */
    public float aimOffset;

    /**
     * A flag indicating if this origin should fire immediately when the fire button is pressed (false = synchronized)
     * or after a delay (false = asynchronous).
     * */
    public boolean asynchronousFiring;

    /** Initial firing delay. Will be 0 for synchronized origins. Stored as a % of the owners firing speed */
    public float initialFireDelay;

    /** How long until the origin can fire again (value in frames)*/
    public int fireCooldown;
    public int scaleTimer;



    public BulletOrigin(float aimOffset, boolean asynchronousFiring) {
        this.aimOffset = aimOffset;
        this.asynchronousFiring = asynchronousFiring;
        this.initialFireDelay = 0;
        this.fireCooldown = 0;
        this.scaleTimer = 0;
    }
}