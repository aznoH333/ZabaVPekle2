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
import com.mygdx.game.input.InputManager;
import com.mygdx.game.utils.types.NumberUtils;

public class PlayerBehaviour extends EntityComponent {

    private Gun gun = null;
    private float dashDirection = 0f;


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
                if (Managers.inputManager.pressedOpenInventory()) {
                    ((Interactable)interactable.getComponentByName(ComponentName.INTERACTABLE)).interact();
                }
            }
        }
    }
    
    private void handlePlayerWorldActions() {

        boolean suspendMovement = owner.getField(FieldName.SuspendMovement);

        if (!suspendMovement) {
            Vector2 movementInput = Managers.inputManager.getMovementInput();
            dashDirection = NumberUtils.directionToward(0f, 0f, movementInput.x, movementInput.y);

            owner.walk(movementInput.x, movementInput.y);
        }


        
        if (gun != null) {

            Vector2 shootDirection = Managers.inputManager.getShootingDirection(owner.x, owner.y);

            if (shootDirection.len() > 0f) {
                gun.direction = NumberUtils.directionToward(
                        0f,
                        0f,
                        shootDirection.x,
                        shootDirection.y);
            }



            
            if (Managers.inputManager.isFireButtonPressed()) {
                gun.shoot(owner);
            }
            
            owner.flipX = shootDirection.x < 0;
        }

        Dash dash = (Dash) owner.getComponentByName(ComponentName.DASH);

        if (dash != null && Managers.inputManager.isDashButtonPressed()) {
            dash.dashInDirection(dashDirection);
        }
    }
    
    

    @Override
    public void onFirstAttached(Entity owner) {
        owner.canBeDamaged = true;
        owner.initializeField(FieldName.SuspendMovement, false);
    }


}
