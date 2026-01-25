package com.mygdx.game.entities;

public enum EntityTeam {
    
    /** Intended for player and objects related to the player (bullets)*/
    PLAYER(false, true),
    
    /** Intended for enemy units and projectiles */
    ENEMY(true, true),
    
    /** Intended for objects that might spawn in levels (pickups, merchants, save stations)*/
    NEUTRAL_OBJECT(true, false),
    
    NONE(false, false);

    /** Indicates if entity should be saved after room completion */
    public final boolean savedAsRoomContent;
    public final boolean isCombatParticipant;
    
    
    EntityTeam(boolean savedAsRoomContent, boolean isCombatParticipant) {
        this.savedAsRoomContent = savedAsRoomContent;
        this.isCombatParticipant = isCombatParticipant;
    }
    
    public boolean isAggressiveAgainst(EntityTeam other) {
        return other.isCombatParticipant && this.isCombatParticipant && this != other;
    }
    
}
