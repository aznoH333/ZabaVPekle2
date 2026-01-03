package com.mygdx.game.entities.components.behaviour.augments.augmentInstances.handModifiers;

import com.mygdx.game.entities.ComponentName;
import com.mygdx.game.entities.components.behaviour.augments.SimpleOnApplyModifierAugmentInstance;
import com.mygdx.game.entities.components.behaviour.gun.BulletOrigin;
import com.mygdx.game.entities.components.behaviour.gun.Gun;
import com.mygdx.game.utils.types.NumberUtils;

import java.util.ArrayList;

public class SideHank extends SimpleOnApplyModifierAugmentInstance {
    public SideHank() {
        super(
            "2x the hands but sideways",
            (owner) -> {
                Gun gun = (Gun) owner.getComponentByName(ComponentName.GUN);


                ArrayList<Float> handsToAdd = new ArrayList<>();
                for (BulletOrigin origin : gun.bulletOrigins) {
                    origin.aimOffset += NumberUtils.HALF_PI;
                    handsToAdd.add(origin.aimOffset + NumberUtils.PI);
                }

                for (Float direction : handsToAdd) {
                    gun.addBulletOrigin(new BulletOrigin(direction, true));
                }

            });
    }
}
