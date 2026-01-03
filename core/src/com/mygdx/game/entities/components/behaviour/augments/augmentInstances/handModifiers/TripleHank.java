package com.mygdx.game.entities.components.behaviour.augments.augmentInstances.handModifiers;

import com.mygdx.game.entities.ComponentName;
import com.mygdx.game.entities.components.behaviour.augments.SimpleOnApplyModifierAugmentInstance;
import com.mygdx.game.entities.components.behaviour.gun.BulletOrigin;
import com.mygdx.game.entities.components.behaviour.gun.Gun;
import com.mygdx.game.entities.fields.FieldName;
import com.mygdx.game.utils.NumberUtils;


public class TripleHank extends SimpleOnApplyModifierAugmentInstance {

    public TripleHank() {
        super("gives 2 extra hands", (owner) -> {
            Gun gun = (Gun) owner.getComponentByName(ComponentName.GUN);


            gun.addBulletOrigin(
                new BulletOrigin(0.30f, true)
            );
            gun.addBulletOrigin(
                new BulletOrigin(NumberUtils.TWO_PI - 0.30f, true)
            );
        });

        super.augmentMap.put(FieldName.FireRateMultiplier, 0.25f);
        super.augmentMap.put(FieldName.FireRate, 10f);

    }
}
