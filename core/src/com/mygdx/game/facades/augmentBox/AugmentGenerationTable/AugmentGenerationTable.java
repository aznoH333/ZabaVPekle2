package com.mygdx.game.facades.augmentBox.AugmentGenerationTable;

import com.mygdx.game.facades.augmentBox.AugmentGenerationSpecifier;
import com.mygdx.game.entities.items.Quality;

import java.util.ArrayList;
import java.util.HashMap;

public class AugmentGenerationTable {
    private final static HashMap<Quality, ArrayList<AugmentGenerationSpecifier>> componentDistributionMap = new HashMap<>();


    static {
        componentDistributionMap.put(Quality.POOR, new ArrayList<>());
        componentDistributionMap.put(Quality.COMMON, new ArrayList<>());
        componentDistributionMap.put(Quality.REFINED, new ArrayList<>());
        componentDistributionMap.put(Quality.ELITE, new ArrayList<>());
        componentDistributionMap.put(Quality.DIVINE, new ArrayList<>());

        // fill with temp garbage
        new PoorAugmentProvider().fillWithAugments(componentDistributionMap.get(Quality.POOR));
        new PoorAugmentProvider().fillWithAugments(componentDistributionMap.get(Quality.COMMON));
        new PoorAugmentProvider().fillWithAugments(componentDistributionMap.get(Quality.REFINED));
        new PoorAugmentProvider().fillWithAugments(componentDistributionMap.get(Quality.ELITE));
        new PoorAugmentProvider().fillWithAugments(componentDistributionMap.get(Quality.DIVINE));

    }


    public static ArrayList<AugmentGenerationSpecifier> get(Quality quality) {
        return componentDistributionMap.get(quality);
    }
}
