package com.mygdx.game.entities.facades.AugmentBox;

import com.mygdx.game.Managers;
import com.mygdx.game.drawing.DrawingLayer;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.components.behaviour.AugmentBox;
import com.mygdx.game.entities.facades.AugmentBox.AugmentGenerationTable.AugmentGenerationTable;
import com.mygdx.game.entities.facades.GUIFacade;
import com.mygdx.game.entities.items.Augment;
import com.mygdx.game.entities.items.Quality;
import com.mygdx.game.utils.types.NumberUtils;

import java.util.ArrayList;

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


    private static Augment generateAugment(Quality targetQuality) {
        ArrayList<EntityComponent> playerComponents = new ArrayList<>();


        int targetCount = targetQuality.averageAugmentCount;
        if (NumberUtils.randomChance(0.23f)) {
            targetCount++;
        }


        for (int i = 0; i < targetCount; i++) {
            // pick possible augment
            AugmentGenerationSpecifier specifier = getAugmentComponent(targetQuality);

            assert specifier != null;
            playerComponents.add(specifier.component.copy());
        }

        Augment augment = new Augment(
            targetQuality,
            playerComponents
        );

        return augment;
    }

    private static AugmentGenerationSpecifier getAugmentComponent(Quality quality) {
        ArrayList<AugmentGenerationSpecifier> possibleComponents = AugmentGenerationTable.get(quality);

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
