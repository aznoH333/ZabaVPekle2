package com.mygdx.game.entities.components.behaviour.augments.augmentInstances.handModifiers;

import com.mygdx.game.entities.ComponentName;
import com.mygdx.game.entities.components.behaviour.augments.SimpleOnApplyModifierAugmentInstance;
import com.mygdx.game.entities.components.behaviour.gun.BulletOrigin;
import com.mygdx.game.entities.components.behaviour.gun.Gun;
import com.mygdx.game.entities.fields.FieldName;
import com.mygdx.game.utils.NumberUtils;

public class OctoHank extends SimpleOnApplyModifierAugmentInstance {
    public OctoHank() {
        super("too many hands", (owner) -> {
            Gun gun = (Gun) owner.getComponentByName(ComponentName.GUN);


            for (float i = NumberUtils.QUARTER_PI; i <= NumberUtils.TWO_PI - NumberUtils.QUARTER_PI; i += NumberUtils.QUARTER_PI) {
                gun.addBulletOrigin(
                    new BulletOrigin(i, true)
                );
            }


        });

        super.augmentMap.put(FieldName.FireRateMultiplier, 1f);
    }
}
