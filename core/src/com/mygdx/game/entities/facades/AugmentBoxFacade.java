package com.mygdx.game.entities.facades;

import com.mygdx.game.Managers;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.components.behaviour.AugmentBox;
import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.ScrapyMachineGun;
import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.ScrapyShotGun;
import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.StatBoost;
import com.mygdx.game.entities.components.behaviour.augments.projectileModifiers.Boomerang;
import com.mygdx.game.entities.components.behaviour.augments.projectileModifiers.Shrapnel;
import com.mygdx.game.entities.components.behaviour.augments.projectileModifiers.SineTravel;
import com.mygdx.game.entities.components.behaviour.augments.projectileModifiers.WallBounce;
import com.mygdx.game.entities.fields.FieldName;
import com.mygdx.game.entities.items.Augment;
import com.mygdx.game.entities.items.Quality;
import com.mygdx.game.utils.NumberUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class AugmentBoxFacade {


    public static void createNewBox(float x, float y, Quality boxRarity) {
        Managers.entityManager.addEntity(
                new Entity()
                        .addComponent(new AugmentBox(boxRarity))
                        .setX(x)
                        .setY(y)
                        .setDrawingLayer(DrawingLayer.WALLS)
                        .setSprite(boxRarity.boxSprite)
        );
    }

    public static void openNewBox(Entity player, Quality targetQuality) {

        Entity guiOwner = new Entity();


        for (int i = -1; i <= 1; i++) {

            int augmentQuality = targetQuality.numericValue;

            if (NumberUtils.randomChance(0.10f)) {
                augmentQuality++;
            } else if (NumberUtils.randomChance(0.25f)) {
                augmentQuality--;
            }

            Quality quality = Quality.getFromNumeric(augmentQuality);


            Augment augment = generateAugment(quality);


            GUIFacade.createAugmentGUI(augment, i * 200f, 0f, player, guiOwner);

        }

        Managers.entityManager.addEntity(guiOwner);
    }


    private final static HashMap<Quality, ArrayList<AugmentGenerationSpecifier>> componentDistributionMap = new HashMap<>();

    static {
        componentDistributionMap.put(Quality.POOR, new ArrayList<>());
        componentDistributionMap.put(Quality.COMMON, new ArrayList<>());
        componentDistributionMap.put(Quality.REFINED, new ArrayList<>());
        componentDistributionMap.put(Quality.ELITE, new ArrayList<>());
        componentDistributionMap.put(Quality.DIVINE, new ArrayList<>());

        // poor
        componentDistributionMap.get(Quality.POOR).add(new AugmentGenerationSpecifier(0.25f, new SineTravel(), true));
        componentDistributionMap.get(Quality.POOR).add(new AugmentGenerationSpecifier(0.25f, new Shrapnel(4), true));
        componentDistributionMap.get(Quality.POOR).add(new AugmentGenerationSpecifier(0.25f, new WallBounce(), true));
        componentDistributionMap.get(Quality.POOR).add(new AugmentGenerationSpecifier(0.25f, new ScrapyMachineGun(), false));
        componentDistributionMap.get(Quality.POOR).add(new AugmentGenerationSpecifier(0.25f, new ScrapyShotGun(), false));


        componentDistributionMap.get(Quality.POOR).add(new AugmentGenerationSpecifier(0.75f, new StatBoost(FieldName.ProjectileDamage, 2f), false));
        componentDistributionMap.get(Quality.POOR).add(new AugmentGenerationSpecifier(0.75f, new StatBoost(FieldName.FireRate, -10f), false));
        componentDistributionMap.get(Quality.POOR).add(new AugmentGenerationSpecifier(0.75f, new StatBoost(FieldName.ProjectileSpeed, 0.5f), false));
        componentDistributionMap.get(Quality.POOR).add(new AugmentGenerationSpecifier(0.75f, new StatBoost(FieldName.ProjectileLifeTime, 12f), false));

        componentDistributionMap.get(Quality.COMMON).add(new AugmentGenerationSpecifier(0.5f, new SineTravel(), true));
        componentDistributionMap.get(Quality.COMMON).add(new AugmentGenerationSpecifier(0.5f, new Shrapnel(8), true));
        componentDistributionMap.get(Quality.COMMON).add(new AugmentGenerationSpecifier(0.5f, new Boomerang(), true));


        componentDistributionMap.get(Quality.COMMON).add(new AugmentGenerationSpecifier(0.5f, new StatBoost(FieldName.Damage, 0.5f), false));
        componentDistributionMap.get(Quality.COMMON).add(new AugmentGenerationSpecifier(0.5f, new StatBoost(FieldName.FireRate, -1.5f), false));
        componentDistributionMap.get(Quality.COMMON).add(new AugmentGenerationSpecifier(0.5f, new StatBoost(FieldName.ProjectileSpeed, 0.1f), false));
        componentDistributionMap.get(Quality.COMMON).add(new AugmentGenerationSpecifier(0.5f, new StatBoost(FieldName.ProjectileLifeTime, 24f), false));



        componentDistributionMap.get(Quality.REFINED).add(new AugmentGenerationSpecifier(0.5f, new SineTravel(), false));
        componentDistributionMap.get(Quality.ELITE).add(new AugmentGenerationSpecifier(0.5f, new SineTravel(), false));
        componentDistributionMap.get(Quality.DIVINE).add(new AugmentGenerationSpecifier(0.5f, new SineTravel(), false));

    }

    private static Augment generateAugment(Quality targetQuality) {
        ArrayList<EntityComponent> playerComponents = new ArrayList<>();
        ArrayList<EntityComponent> gunComponents = new ArrayList<EntityComponent>();


        int targetCount = targetQuality.averageAugmentCount;
        if (NumberUtils.randomChance(0.23f)) {
            targetCount++;
        }


        for (int i = 0; i < targetCount; i++) {
            // pick possible augment
            AugmentGenerationSpecifier specifier = getAugmentComponent(targetQuality);

            if (specifier.intendedForProjectile) {
                gunComponents.add(specifier.component.copy());

            }else {
                playerComponents.add(specifier.component.copy());
            }

        }

        Augment augment = new Augment(
                targetQuality,
                playerComponents,
                gunComponents
        );

        return augment;
    }

    private static AugmentGenerationSpecifier getAugmentComponent(Quality quality) {
        ArrayList<AugmentGenerationSpecifier> possibleComponents = componentDistributionMap.get(quality);

        float raritySum = 0f;

        for (AugmentGenerationSpecifier specifier : possibleComponents) {
            raritySum += specifier.rarity;
        }

        float chosenAugmentValue = NumberUtils.randomFloat(0, raritySum);

        float rarityCounter = 0f;
        for (AugmentGenerationSpecifier specifier : possibleComponents) {
            if (specifier.rarity + rarityCounter >= chosenAugmentValue) {
                return specifier;
            }

            rarityCounter += specifier.rarity;
        }

        return null;
    }
}
