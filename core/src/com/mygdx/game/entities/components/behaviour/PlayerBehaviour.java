package com.mygdx.game.entities.components.behaviour;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Managers;
import com.mygdx.game.drawing.DrawingCommand;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.drawing.TextDrawingCommand;
import com.mygdx.game.entities.ComponentName;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.components.behaviour.gun.Gun;
import com.mygdx.game.entities.components.control.Interactable;
import com.mygdx.game.entities.fields.FieldName;
import com.mygdx.game.facades.inventory.InventoryFacade;
import com.mygdx.game.utils.types.NumberUtils;

public class PlayerBehaviour extends EntityComponent {

    private Gun gun = null;


    public PlayerBehaviour() {
        super.name = ComponentName.PLAYER;
    }


    @Override
    public void onComponentAttached(Entity owner) {
        gun = (Gun) owner.getComponentByName(ComponentName.GUN);
    }

    @Override
    public void onDraw(Entity owner) {
        // set camera
        Managers.drawingManager.setCameraPosition(owner.x, owner.y);
    }
    
    @Override
    public void onUpdate(Entity owner) {
        boolean isInventoryOpen = InventoryFacade.isInventoryOpen();
    
        if (!isInventoryOpen) {
            handlePlayerWorldActions();
        }
        
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
            // inventory
            InventoryFacade.toggleInventory();
        }
        
        if (!isInventoryOpen) {
            handleInteractables(owner);
        }
    }
    
    private static void handleInteractables(Entity owner) {
        // interacting
        Entity interactable = Managers.entityManager.findClosestEntityWithComponent(owner, ComponentName.INTERACTABLE);
        
        if (interactable != null) {
            float distance = NumberUtils.distanceBetweenEntities(owner, interactable);
            
            if (distance < 32f) {
                // Managers.drawingManager.drawText(new TextDrawingCommand("E", interactable.x, interactable.y - 16f));
                
                if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
                    ((Interactable)interactable.getComponentByName(ComponentName.INTERACTABLE)).interact();
                }
            }
        }
    }
    
    private void handlePlayerWorldActions() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            owner.walk(-1f, 0f);
        }
        
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            owner.walk(1f, 0f);
        }
        
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            owner.walk(0f, 1f);
        }
        
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            owner.walk(0f, -1f);
        }
        
        if (gun != null) {
            Vector2 mousePos = Managers.drawingManager.getMousePosition();
            
            gun.direction = NumberUtils.directionToward(
                owner.x,
                owner.y,
                mousePos.x,
                mousePos.y);
            
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                gun.shoot(owner);
            }
            
            owner.flipX = mousePos.x < owner.x;
        }
    }
    
    

    @Override
    public void onFirstAttached(Entity owner) {
        owner.setNumericStat(FieldName.Speed, 2.5f);
        owner.setNumericStat(FieldName.MaxHealth, 6f);
        owner.setNumericStat(FieldName.Health, 6f);
        owner.canBeDamaged = true;
    }


}
