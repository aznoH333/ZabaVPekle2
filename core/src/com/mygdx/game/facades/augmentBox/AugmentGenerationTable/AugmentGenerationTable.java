package com.mygdx.game.facades.augmentBox.AugmentGenerationTable;

import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.StatBoostAugment;
import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.gunBehaviourModifiers.CannonAugment;
import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.gunBehaviourModifiers.MachineGunAugment;
import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.gunBehaviourModifiers.ShotGunAugment;
import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.handModifiers.*;
import com.mygdx.game.entities.components.behaviour.augments.augmentInstances.shotBehaviour.*;
import com.mygdx.game.entities.fields.FieldName;
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

        for (Quality quality : Quality.values()) {
            fillWithBasicAugments(componentDistributionMap.get(quality), quality);
        }


        // poor
        addAugment(Quality.POOR, 0.10f, new SpinShotAugment());

        // common
        addAugment(Quality.COMMON, 0.05f, new SpinShotAugment());
        addAugment(Quality.COMMON, 0.05f, new DoubleHank());
        addAugment(Quality.COMMON, 0.05f, new TripleHank());

        // refined
        addAugment(Quality.REFINED, 0.1f, new DoubleHank());
        addAugment(Quality.REFINED, 0.1f, new TripleHank());
        addAugment(Quality.REFINED, 0.1f, new HomingShotAugment());
        addAugment(Quality.REFINED, 0.1f, new WallBounceShotAugment());

        // eite
        addAugment(Quality.ELITE, 0.1f, new DoubleHank());
        addAugment(Quality.ELITE, 0.1f, new TripleHank());
        addAugment(Quality.ELITE, 0.1f, new PentaHank());
        addAugment(Quality.ELITE, 0.1f, new OctoHank());
        addAugment(Quality.ELITE, 0.1f, new HomingShotAugment());
        addAugment(Quality.ELITE, 0.1f, new WallBounceShotAugment());

        // divine
        addAugment(Quality.DIVINE, 0.1f, new DoubleHank());
        addAugment(Quality.DIVINE, 0.1f, new TripleHank());
        addAugment(Quality.DIVINE, 0.1f, new PentaHank());
        addAugment(Quality.DIVINE, 0.1f, new OctoHank());
        addAugment(Quality.DIVINE, 0.1f, new HomingShotAugment());
        addAugment(Quality.DIVINE, 0.1f, new WallBounceShotAugment());
    }


    public static ArrayList<AugmentGenerationSpecifier> get(Quality quality) {
        return componentDistributionMap.get(quality);
    }

    private static void fillWithBasicAugments(ArrayList<AugmentGenerationSpecifier> target, Quality quality) {
        target.add(new AugmentGenerationSpecifier(1f, new StatBoostAugment(FieldName.ProjectileDamage, 0.1f * quality.rarityAdjustedNumericValue)));
        target.add(new AugmentGenerationSpecifier(1f, new StatBoostAugment(FieldName.FireRate, -0.5f * quality.rarityAdjustedNumericValue)));
        target.add(new AugmentGenerationSpecifier(1f, new StatBoostAugment(FieldName.ProjectileSpread, -0.02f * quality.rarityAdjustedNumericValue)));


        // weapon types
        target.add(new AugmentGenerationSpecifier(0.10f, new MachineGunAugment(quality)));
        target.add(new AugmentGenerationSpecifier(0.10f, new ShotGunAugment(quality)));
        target.add(new AugmentGenerationSpecifier(0.1f, new CannonAugment(quality)));

        // universal modifiers
        target.add(new AugmentGenerationSpecifier(0.05f, new ShrapnelShotAugment(quality)));
        target.add(new AugmentGenerationSpecifier(0.07f, new SineTravelShotAugment()));
    }

    private static void addAugment(Quality targetQuality, float chance, EntityComponent component) {
        componentDistributionMap.get(targetQuality).add(new AugmentGenerationSpecifier(chance, component));
    }
}
