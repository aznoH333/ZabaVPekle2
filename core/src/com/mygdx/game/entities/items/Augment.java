package com.mygdx.game.entities.items;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;

import java.util.ArrayList;
import java.util.HashMap;

public class Augment extends EntityComponent {


    public final Quality quality;
    public final ArrayList<EntityComponent> includedComponents;
    public final ArrayList<String> displayText = new ArrayList<>();


    public Augment(Quality quality, ArrayList<EntityComponent> includedComponents) {
        this.quality = quality;
        this.includedComponents = includedComponents;



        // construct display text

        HashMap<String, Integer> effectPotencyMap;
        for (EntityComponent component : includedComponents) {
            if (component.effectDescription != null) {

            }
        }
    }


    public void attachToEntity(Entity attachTo) {

    }
}
