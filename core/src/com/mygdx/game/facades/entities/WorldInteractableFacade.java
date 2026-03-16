package com.mygdx.game.facades.entities;

import com.mygdx.game.Managers;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.entities.ComponentName;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityIdentifier;
import com.mygdx.game.entities.EntityTeam;
import com.mygdx.game.entities.components.behaviour.ItemDrop;
import com.mygdx.game.entities.components.control.Interactable;
import com.mygdx.game.entities.components.visual.particles.FadeParticle;
import com.mygdx.game.entities.fields.FieldName;
import com.mygdx.game.entities.items.Quality;
import com.mygdx.game.facades.augmentBox.AugmentBoxFacade;
import com.mygdx.game.facades.inventory.InventoryFacade;
import com.mygdx.game.facades.world.WorldMovementFacade;
import com.mygdx.game.playState.inventory.InventoryItem;
import com.mygdx.game.playState.inventory.InventoryItemType;
import com.mygdx.game.playState.inventory.InventoryMenuType;
import com.mygdx.game.utils.types.NumberUtils;

public class WorldInteractableFacade {
    public static Entity createItemDrop(InventoryItem item, float x, float y) {
        return new Entity()
            .setSprite(item.sprite)
            .setX(x)
            .setY(y)
            .setDrawingLayer(DrawingLayer.ITEMS)
            .setNumericStat(FieldName.Speed, 1.5f)
            .setIdentifier(EntityIdentifier.ITEM)
            .setTeam(EntityTeam.NEUTRAL_OBJECT)
            .addComponent(new ItemDrop(item))
            .addComponent(new Interactable(
                (entity)-> {
                    ItemDrop dropComponent = (ItemDrop) entity.getComponentByName(ComponentName.INVENTORY_ITEM);
                    
                    if (Managers.playStateManager.inventory.canStoreItem(dropComponent.item)) {
                        Managers.playStateManager.inventory.addItem(dropComponent.item);
                        entity.commitSudoku();
                    }
                }
            ));
    }
    
    
    public static Entity createItemBox(float x, float y) {
        return new Entity()
            .setSprite("item_boxes_0001")
            .setX(x)
            .setY(y)
            .setTeam(EntityTeam.NEUTRAL_OBJECT)
            .setDrawingLayer(DrawingLayer.ITEMS)
            .addComponent(
                new Interactable((entity)->{

                    int numberOfContents = NumberUtils.randomInt(2, 4);

                    for (int i = 0; i < numberOfContents; i++) {
                        float rng = NumberUtils.randomFloat(0f, 1f);

                        if (rng < 0.44f) {
                            Managers.entityManager.addEntity(
                                    WorldInteractableFacade.createItemDrop(
                                            new InventoryItem(InventoryItemType.PLATE, Quality.POOR, NumberUtils.randomInt(1,2)),
                                            entity.x,
                                            entity.y
                                    )
                            );
                        }else if (rng < 0.88f) {
                            Managers.entityManager.addEntity(
                                    WorldInteractableFacade.createItemDrop(
                                            new InventoryItem(InventoryItemType.WIRING, Quality.POOR, NumberUtils.randomInt(1,2)),
                                            entity.x,
                                            entity.y
                                    )
                            );
                        }else {
                            Managers.entityManager.addEntity(
                                    WorldInteractableFacade.createItemDrop(
                                            new InventoryItem(InventoryItemType.PROCESSOR, Quality.POOR, 1),
                                            entity.x,
                                            entity.y
                                    )
                            );
                        }
                    }

                    entity.commitSudoku();
                    
                    // spawn giblet
                    Managers.entityManager.addEntity(
                        new Entity()
                            .setX(entity.x)
                            .setY(entity.y)
                            .setSprite("item_boxes_0002")
                            .setDrawingLayer(DrawingLayer.DOOR)
                            .addComponent(new FadeParticle(60, false, 0.5f))
                    );
                })
            );
    }
    
    public static Entity createRepairStation(float x, float y) {
        return new Entity()
            .setX(x)
            .setY(y)
            .setSprite("machines_0001")
            .setDrawingLayer(DrawingLayer.ITEMS)
            .setTeam(EntityTeam.NEUTRAL_OBJECT)
            .addComponent(new Interactable((entity) -> {
                InventoryFacade.openInventoryMenu(InventoryMenuType.REPAIR_STATION, entity);
            }));
    }

    public static Entity createForgingStation(float x, float y) {
        return new Entity()
                .setX(x)
                .setY(y)
                .setSprite("machines_0004")
                .setDrawingLayer(DrawingLayer.ITEMS)
                .setTeam(EntityTeam.NEUTRAL_OBJECT)
                .addComponent(new Interactable((entity) -> {
                    InventoryFacade.openInventoryMenu(InventoryMenuType.FORGING_STATION, entity);
                }));
    }

    public static Entity createProcessorAssembler(float x, float y) {
        return new Entity()
                .setX(x)
                .setY(y)
                .setSprite("machines_0002")
                .setDrawingLayer(DrawingLayer.ITEMS)
                .setTeam(EntityTeam.NEUTRAL_OBJECT)
                .addComponent(new Interactable((entity) -> {
                    InventoryFacade.openInventoryMenu(InventoryMenuType.PROCESSOR_MANUFACTURING, entity);
                }));
    }


    public static Entity createLevelExit(float x, float y) {
        return new Entity()
                .setX(x)
                .setY(y)
                .setSprite("level_exit")
                .setDrawingLayer(DrawingLayer.ITEMS)
                .setTeam(EntityTeam.NEUTRAL_OBJECT)
                .addComponent(new Interactable((entity) -> {
                    WorldMovementFacade.goToNextZone();
                }));
    }
    
    public static Entity createNewAugmentBox(float x, float y, Quality boxRarity) {
        return
            new Entity()
                .setX(x)
                .setY(y)
                .setTeam(EntityTeam.NEUTRAL_OBJECT)
                .setDrawingLayer(DrawingLayer.WALLS)
                .setSprite("item_boxes_0003")
                .addComponent(new Interactable((box)->{
                    AugmentBoxFacade.openNewBox(Managers.playStateManager.playerReference, boxRarity);
                    box.commitSudoku();
                    // spawn giblet
                    Managers.entityManager.addEntity(
                        new Entity()
                            .setX(box.x)
                            .setY(box.y)
                            .setSprite("item_boxes_0004")
                            .setDrawingLayer(DrawingLayer.DOOR)
                            .addComponent(new FadeParticle(60, false, 0.5f))
                    );
                }))
            ;
    }
    
}
