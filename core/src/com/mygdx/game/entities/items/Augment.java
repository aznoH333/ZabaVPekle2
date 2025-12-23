package com.mygdx.game.entities.items;

import com.mygdx.game.Managers;
import com.mygdx.game.drawing.TextDrawingCommand;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.components.behaviour.Gun;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Augment extends EntityComponent {


    public final Quality quality;
    public final ArrayList<EntityComponent> includedComponents;
    public final ArrayList<EntityComponent> componentsForGun;
    public final ArrayList<String> displayText = new ArrayList<>();


    private final static float Y_TEXT_OFFSET = -60f;
    private final static float Y_LINE_OFFSET = -16f;


    public Augment(Quality quality, ArrayList<EntityComponent> componentsForEntity, ArrayList<EntityComponent> componentsForGun) {
        this.quality = quality;
        this.includedComponents = componentsForEntity;
        this.componentsForGun = componentsForGun;


        // construct display text
        HashMap<String, Integer> effectPotencyMap = new HashMap<>();
        for (EntityComponent component : componentsForEntity) {
            if (component.effectDescription != null) {
                if (!effectPotencyMap.containsKey(component.effectDescription)) {
                    effectPotencyMap.put(component.effectDescription, 0);
                }

                if (component.potency != EffectPotency.NOT_QUALIFIED) {
                    effectPotencyMap.put(component.effectDescription, effectPotencyMap.get(component.effectDescription) + component.potency.quantifier);
                }
            }
        }
        for (EntityComponent component : componentsForGun) {
            if (component.effectDescription != null) {
                if (!effectPotencyMap.containsKey(component.effectDescription)) {
                    effectPotencyMap.put(component.effectDescription, 0);
                }

                if (component.potency != EffectPotency.NOT_QUALIFIED) {
                    effectPotencyMap.put(component.effectDescription, effectPotencyMap.get(component.effectDescription) + component.potency.quantifier);
                }
            }
        }

        displayText.add(quality.textName + " augment");

        // convert to text
        for (Map.Entry<String, Integer> entry : effectPotencyMap.entrySet()) {
            EffectPotency potency = EffectPotency.getPotencyBasedOnValue(entry.getValue());
            String text = "grants";

            if (potency != EffectPotency.NOT_QUALIFIED) {
                text += " " + potency.textName;
            }

            text += " " + entry.getKey();

            displayText.add(text);
        }
    }

    @Override
    public void onUpdate(Entity owner) {
        float nextLineY = owner.y + Y_TEXT_OFFSET;
        for (String line : displayText) {
            Managers.drawingManager.drawText(new TextDrawingCommand(line, owner.x, nextLineY));

            nextLineY += Y_LINE_OFFSET;
        }
    }


    public void attachToEntity(Entity attachTo) {
        for (EntityComponent component : includedComponents) {
            attachTo.addComponent(component);
        }

        Gun gun = (Gun) attachTo.getComponentByName("shooter");
        for (EntityComponent gunComponent : componentsForGun) {
            gun.addBulletComponent(gunComponent);
        }
    }
}
