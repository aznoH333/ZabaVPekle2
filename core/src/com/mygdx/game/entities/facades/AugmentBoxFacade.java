package com.mygdx.game.entities.facades;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.EntityManager;
import com.mygdx.game.entities.components.behaviour.projectile.Boomerang;
import com.mygdx.game.entities.components.behaviour.projectile.Shrapnel;
import com.mygdx.game.entities.components.behaviour.projectile.SineTravel;
import com.mygdx.game.entities.components.behaviour.projectile.WallBounce;
import com.mygdx.game.entities.items.Augment;
import com.mygdx.game.entities.items.Quality;
import com.mygdx.game.utils.NumberUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class AugmentBoxFacade {

    private final static EntityManager entityManager = EntityManager.getInstance();

    public static void openNewBox(Entity player, Quality targetQuality) {

        Entity guiOwner = new Entity();


        for (int i = -1; i <= 1; i++) {

            int augmentQuality = targetQuality.numericValue;

            if (NumberUtils.randomChance(0.10f)) {
                augmentQuality++;
            }else if (NumberUtils.randomChance(0.25f)) {
                augmentQuality--;
            }

            Quality quality = Quality.getFromNumeric(augmentQuality);


            Augment augment = generateAugment(quality);


            GUIFacade.createAugmentGUI(augment, i * 200f, 0f, player, guiOwner);

        }

        entityManager.addEntity(guiOwner);

    }


    private final static HashMap<Quality, ArrayList<AugmentGenerationSpecifier>> componentDistributionMap = new HashMap<>();
    static {
        componentDistributionMap.put(Quality.POOR, new ArrayList<>());
        componentDistributionMap.put(Quality.COMMON, new ArrayList<>());
        componentDistributionMap.put(Quality.REFINED, new ArrayList<>());
        componentDistributionMap.put(Quality.ELITE, new ArrayList<>());
        componentDistributionMap.put(Quality.DIVINE, new ArrayList<>());



        componentDistributionMap.get(Quality.POOR).add(new AugmentGenerationSpecifier(0.5f, new SineTravel()));
        componentDistributionMap.get(Quality.POOR).add(new AugmentGenerationSpecifier(0.5f, new Shrapnel(4)));
        componentDistributionMap.get(Quality.POOR).add(new AugmentGenerationSpecifier(0.5f, new WallBounce()));


        componentDistributionMap.get(Quality.COMMON).add(new AugmentGenerationSpecifier(0.5f, new SineTravel()));
        componentDistributionMap.get(Quality.COMMON).add(new AugmentGenerationSpecifier(0.5f, new Shrapnel(8)));
        componentDistributionMap.get(Quality.COMMON).add(new AugmentGenerationSpecifier(0.5f, new Boomerang()));


        componentDistributionMap.get(Quality.REFINED).add(new AugmentGenerationSpecifier(0.5f, new SineTravel()));
        componentDistributionMap.get(Quality.ELITE).add(new AugmentGenerationSpecifier(0.5f, new SineTravel()));
        componentDistributionMap.get(Quality.DIVINE).add(new AugmentGenerationSpecifier(0.5f, new SineTravel()));

    }

    private static Augment generateAugment(Quality targetQuality) {
        ArrayList<EntityComponent> components = new ArrayList<EntityComponent>();


        int targetCount = targetQuality.averageAugmentCount;
        if (NumberUtils.randomChance(0.23f)) {
            targetCount++;
        }


        for (int i = 0; i < targetCount; i++) {
            // pick possible augment
            components.add(getAugmentComponent(targetQuality));
        }

        Augment augment = new Augment(
                targetQuality,
                new ArrayList<>(),
                components
        );

        return augment;
    }

    private static EntityComponent getAugmentComponent(Quality quality){
        ArrayList<AugmentGenerationSpecifier> possibleComponents = componentDistributionMap.get(quality);

        float raritySum = 0f;

        for (AugmentGenerationSpecifier specifier : possibleComponents) {
            raritySum += specifier.rarity;
        }

        float chosenAugmentValue = NumberUtils.randomFloat(0, raritySum);

        float rarityCounter = 0f;
        for (AugmentGenerationSpecifier specifier : possibleComponents) {
            if (specifier.rarity + rarityCounter >= chosenAugmentValue) {
                return specifier.component.copy();
            }

            rarityCounter += specifier.rarity;
        }

        return null;
    }
}
